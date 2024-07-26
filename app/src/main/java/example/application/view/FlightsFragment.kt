package example.application.view

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import example.application.model.UseData
import example.application.R
import example.application.model.source.Data
import example.application.databinding.FragmentFlightsBinding
import example.application.view.adapters.TopScrollAdapter
import java.util.Calendar


class FlightsFragment : Fragment(), TopScrollAdapter.OnItemClickListener {


    private lateinit var flightsBinding: FragmentFlightsBinding
    private lateinit var topScrollAdapter: TopScrollAdapter
    private var topScrollItems: ArrayList<UseData.TopScroll> = ArrayList()
    private lateinit var departureDateEditText: EditText
    private lateinit var returnDateEditText : EditText



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        flightsBinding = FragmentFlightsBinding.inflate(inflater, container, false)
        return flightsBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val useData = Data()
        topScrollItems = useData.strings()
        setUpRecyclerView()


        departureDateEditText = flightsBinding.etDeparture
        returnDateEditText = flightsBinding.etReturn

        departureDateEditText.setOnClickListener {
            showDatePickerDialog { date ->
                departureDateEditText.setText(date)

            }
        }
        returnDateEditText.setOnClickListener {
            showDatePickerDialog { date ->
                returnDateEditText.setText(date)

            }
        }

        val autoCompleteTextView: AutoCompleteTextView = flightsBinding.autoCompleteText
        val tripOptions = arrayOf("Round Trip", "One Way", "Multi-City")
        val adapter = ArrayAdapter(requireContext(), R.layout.drop_down_item, tripOptions)
        autoCompleteTextView.setAdapter(adapter)

        autoCompleteTextView.setOnItemClickListener{parent, view, position, id ->
            val selectedItem = parent.getItemAtPosition(position) as String
            autoCompleteTextView.setText(selectedItem, false)

        }

        autoCompleteTextView.setOnClickListener {
            autoCompleteTextView.showDropDown()
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

        val topScrollRecyclerVIew = flightsBinding.topScrollRecycler
        topScrollRecyclerVIew.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        topScrollRecyclerVIew.adapter = topScrollAdapter
    }

    override fun onItemClick(position: Int) {
        val categoryName = topScrollItems[position].text
        when (categoryName) {
            "CABS" -> {
                findNavController().navigate(R.id.action_flightsFragment_to_landingFragment2)
            }
            "ACCOMMODATIONS" -> {
                findNavController().navigate(R.id.action_flightsFragment_to_accommodationFragment)
            }
            "SAFARIS" -> {
                findNavController().navigate(R.id.action_flightsFragment_to_safarisFragment)

            }
            "$#EXCHANGE" -> {
                findNavController().navigate(R.id.action_flightsFragment_to_currencyFragment)

            }
            "FLIGHTS" -> {
            }
        }
    }


}