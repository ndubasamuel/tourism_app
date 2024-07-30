package example.application.view

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import example.application.R
import example.application.databinding.FragmentAccommodationBinding
import example.application.model.UseData
import example.application.model.network.api.RetrofitInstance
import example.application.model.network.api.RetrofitInstance.Companion.api
import example.application.model.repository.Repository
import example.application.model.source.Data
import example.application.utils.Resource
import example.application.view.adapters.AccommodationAdapter
import example.application.view.adapters.TopScrollAdapter
import example.application.viewModel.ViewModelClass
import example.application.viewModel.ViewModelFactory
import example.eclestay.view.adapters.ServiceAdapter
import java.util.Calendar


class AccommodationFragment : Fragment(), TopScrollAdapter.OnItemClickListener, AccommodationAdapter.OnAccommodationClickListener {


    private val repository: Repository by lazy {
        Repository.getInstance(api)
    }
    private val viewModel: ViewModelClass by viewModels {
        ViewModelFactory(repository)
    }


    private lateinit var accommodationBinding: FragmentAccommodationBinding

    private lateinit var fromDateEditText: EditText
    private lateinit var toDateEdittext: EditText
    private lateinit var adultsSpinner: Spinner
    private lateinit var childrenSpinner: Spinner

    private lateinit var topScrollAdapter: TopScrollAdapter
    private lateinit var topScrollItems: ArrayList<UseData.TopScroll>

    private lateinit var accommodationAdapter: AccommodationAdapter
//    private lateinit var serviceAdapter: ServiceAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        accommodationBinding = FragmentAccommodationBinding.inflate(inflater, container, false)
        return accommodationBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.services.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    if (response.value.status == 200) {
                        val services = response.value.data.Services
                        if (services.isNotEmpty()) {
                            if (services.size > 3) {
                                Log.d("Accommodation Fragment", "Service Response: ${services}")
                                val serviceImages = services[3].images
                                accommodationAdapter.differ.submitList(serviceImages)

                                val editText: EditText = accommodationBinding.fromDate
                                val fromDate = services[0].start_date
                                val editableText = Editable.Factory.getInstance().newEditable(fromDate)
                                editText.text = editableText

                            } else {
                                Log.d("Accommodation Fragment", "Services list has less than 4 elements")
                            }
                        } else {
                            Log.d("Accommodation Fragment", "Accommodation Empty")
                        }
                    }
                    Toast.makeText(requireContext(), "Success: ${response.value.message}", Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
                is Resource.Failure -> {
                    Toast.makeText(requireContext(), "Error: ${response.errorMessage}", Toast.LENGTH_SHORT).show()
                    Log.e("Accommodation Fragment", response.errorMessage)
                }
            }
        }
        fromDateEditText = accommodationBinding.fromDate
        toDateEdittext = accommodationBinding.toDate
        adultsSpinner = accommodationBinding.adultsSpinner
        childrenSpinner = accommodationBinding.childrenSpinner

        fromDateEditText.setOnClickListener {
            showDatePickerDialog { date ->
                fromDateEditText.setText(date)
            }
        }
        toDateEdittext.setOnClickListener{
            showDatePickerDialog { date ->
                toDateEdittext.setText(date)

            }
        }
        setupSpinner(adultsSpinner, R.array.adults_array)
        setupSpinner(childrenSpinner, R.array.children_array)


    }

    private fun showDatePickerDialog(onDateSet: (String) -> Unit) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
            val date = "$selectedDay/${selectedMonth + 1}/$selectedYear"
            onDateSet(date)
        }, year, month, day)

        datePickerDialog.show()
    }

    private fun setupSpinner(spinner: Spinner, arrayResId: Int) {
        ArrayAdapter.createFromResource(requireContext(), arrayResId, R.layout.drop_down_item).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter

        }

        val topScroll = Data()
        topScrollItems = topScroll.strings()


        setUpRecyclerView()
        viewModel.getServices()
    }

    private fun setUpRecyclerView() {
        topScrollAdapter = TopScrollAdapter(topScrollItems, this)

        val topScrollRecyclerView = accommodationBinding.topScrollRecycler
        topScrollRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        topScrollRecyclerView.adapter = topScrollAdapter


       accommodationAdapter = AccommodationAdapter(this)
        val serviceRecyclerView = accommodationBinding.bedAndBreakfast
        serviceRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        serviceRecyclerView.adapter = accommodationAdapter

//        serviceAdapter = ServiceAdapter()
//        val serviceRecyclerView = accommodationBinding.bedAndBreakfast
//        serviceRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
//        serviceRecyclerView.adapter = accommodationAdapter
    }


    override fun onItemClick(position: Int) {
        when (topScrollItems[position].text) {
            "CABS" -> {
                findNavController().navigate(R.id.action_accommodationFragment_to_landingFragment2)

            }
            "ACCOMMODATIONS" -> {

            }
            "SAFARIS" -> {
                findNavController().navigate(R.id.action_accommodationFragment_to_safarisFragment)

            }
            "$#EXCHANGE" -> {
                findNavController().navigate(R.id.action_accommodationFragment_to_currencyFragment)

            }
            "FLIGHTS" -> {
                findNavController().navigate(R.id.action_accommodationFragment_to_flightsFragment)

            }
        }
    }

    override fun onAccommodationClick(position: Int, viewId: Int) {
        val image = accommodationAdapter.differ.currentList[position]
        when (image.service_id) {
             366 -> {
                 findNavController().navigate(R.id.serviceFragment)

            }


        }
    }
    private fun showSnackBar(message: String) {
        Snackbar.make(accommodationBinding.root, message, Snackbar.LENGTH_SHORT).show()
    }
    private fun showProgressBar() {
        val progressBar = accommodationBinding.progressBar
        progressBar.visibility = View.VISIBLE
    }
    private fun hideProgressBar() {
        val progressBar = accommodationBinding.progressBar
        progressBar.visibility = View.INVISIBLE
    }
}