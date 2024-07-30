package example.application.view

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import example.application.model.UseData
import example.application.R
import example.application.databinding.FragmentSafarisBinding
import example.application.model.network.api.RetrofitInstance
import example.application.model.repository.Repository
import example.application.model.source.Data
import example.application.view.adapters.TopScrollAdapter
import example.application.view.adapters.TourGuideAdapter
import example.application.viewModel.ViewModelClass
import example.application.viewModel.ViewModelFactory
import java.util.Calendar


class SafarisFragment : Fragment(), TopScrollAdapter.OnItemClickListener {


    private val repository: Repository by lazy {
        Repository.getInstance(RetrofitInstance.api)
    }
    private val viewModel: ViewModelClass by viewModels {
        ViewModelFactory(repository)
    }

    private lateinit var safarisBinding: FragmentSafarisBinding
    private lateinit var datePicker: ImageButton
    private lateinit var showDate: EditText

    private lateinit var adultsSpinner: Spinner
    private lateinit var childrenSpinner: Spinner

    private lateinit var tourGuideAdapter: TourGuideAdapter

    private lateinit var topScrollAdapter: TopScrollAdapter
    private var topScrollItems: ArrayList<UseData.TopScroll> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        safarisBinding = FragmentSafarisBinding.inflate(inflater, container, false)
        return safarisBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        datePicker = safarisBinding.showDatePicker
        showDate = safarisBinding.date

        adultsSpinner = safarisBinding.adultSpinner
        childrenSpinner = safarisBinding.childrenSpinner

        datePicker.setOnClickListener {
            showDatePickerDialog { date ->
                showDate.setText(date)

            }
        }

//        viewModel.tourGuides.observe(viewLifecycleOwner)

        val useData = Data()
        topScrollItems = useData.strings()
        setUpRecyclerView()


        setupSpinner(adultsSpinner, R.array.adults_array)
        setupSpinner(childrenSpinner, R.array.children_array)
    }


    private fun setupSpinner(spinner: Spinner, arrayResId: Int) {
        ArrayAdapter.createFromResource(requireContext(), arrayResId, R.layout.drop_down_item).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter

        }
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

    private fun setUpRecyclerView() {
        topScrollAdapter = TopScrollAdapter(topScrollItems, this)

        val topScrollRecyclerView = safarisBinding.topScrollRecycler
        topScrollRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        topScrollRecyclerView.adapter = topScrollAdapter


        tourGuideAdapter = TourGuideAdapter()

        val tourRecyclerView = safarisBinding.guideRecycler
        tourRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        tourRecyclerView.adapter = tourGuideAdapter
    }

    override fun onItemClick(position: Int) {
        val categoryName = topScrollItems[position].text
        when (categoryName) {
            "CABS" -> {
                findNavController().navigate(R.id.action_safarisFragment_to_landingFragment2)

            }
            "ACCOMMODATIONS" -> {
                findNavController().navigate(R.id.action_safarisFragment_to_accommodationFragment)
            }

            "SAFARIS" -> {

            }

            "$#EXCHANGE" -> {
                findNavController().navigate(R.id.action_safarisFragment_to_currencyFragment)

            }

            "FLIGHTS" -> {
                findNavController().navigate(R.id.action_safarisFragment_to_flightsFragment)

            }
        }
    }

}