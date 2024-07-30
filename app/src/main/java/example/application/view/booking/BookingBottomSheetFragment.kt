package example.application.view.booking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import example.application.R
import example.application.model.network.api.RetrofitInstance
import example.application.model.repository.Repository
import example.application.utils.Resource
import example.application.viewModel.ViewModelClass
import example.application.viewModel.ViewModelFactory

class BookingBottomSheetFragment: BottomSheetDialogFragment() {


    private val repository: Repository by lazy {
        Repository.getInstance(RetrofitInstance.api)
    }
    private val viewModel: ViewModelClass by viewModels {
        ViewModelFactory(repository)
    }

    private lateinit var adultsSpinner: Spinner
    private lateinit var childrenSpinner: Spinner
    private lateinit var paymentButton: Button
    private lateinit var servicePrice: TextView
    private lateinit var showPrice: EditText


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.booking_bottom_sheet, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adultsSpinner = view.findViewById(R.id.adultSpinnerBooking)
        childrenSpinner = view.findViewById(R.id.childrenSpinnerBooking)
        servicePrice = view.findViewById(R.id.servicePrice)
        paymentButton = view.findViewById(R.id.payButton)
        showPrice = view.findViewById(R.id.totalAmount)



        setupSpinner(adultsSpinner, R.array.adults_array)
        setupSpinner(childrenSpinner, R.array.children_array)


        paymentButton.setOnClickListener {
            calculate()
        }

        viewModel.services.observe(viewLifecycleOwner, Observer{
            when (it){
                is Resource.Success -> {
                    val cost = it.value.data.Services[0].price
                    servicePrice.text = cost.toString()
                }
                is Resource.Loading ->{}
                is Resource.Failure -> {}
            }
        })

        viewModel.makePayments.observe(viewLifecycleOwner, Observer {payResponse ->
            when (payResponse) {
                is Resource.Success -> {
                    Toast.makeText(requireContext(), payResponse.value.message, Toast.LENGTH_SHORT).show()

                }
                is Resource.Loading -> {

                }
                is Resource.Failure -> {
                    Toast.makeText(requireContext(), payResponse.errorMessage, Toast.LENGTH_SHORT).show()
                }

            }

        })


        viewModel.getServices()
    }


    private fun setupSpinner(spinner: Spinner, arrayResId: Int) {
        ArrayAdapter.createFromResource(requireContext(), arrayResId, R.layout.drop_down_item).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter

        }
    }


    private fun calculate() {
        val servicePriceValue = servicePrice.text.toString().toDoubleOrNull()

        if (servicePriceValue == null) {
            Toast.makeText(requireContext(), "Price Error", Toast.LENGTH_SHORT).show()
            return
        }


        val adultCount = adultsSpinner.selectedItem.toString().toInt()
        val childCount = childrenSpinner.selectedItem.toString().toInt()

        val totalCost = calculateTotalAmount(adultCount, childCount, servicePriceValue)

        Toast.makeText(requireContext(), "Total Cost: $%.2f".format(totalCost), Toast.LENGTH_SHORT).show()

        showPrice.setText( "Total Cost: $%.2f".format(totalCost))

        viewModel.makePayments()
    }

    private fun calculateTotalAmount(adultCount: Int, childCount: Int, servicePrice: Double): Double {
        val adultPrice = servicePrice
        val childPrice = servicePrice / 2
        return (adultCount * adultPrice) + (childCount * childPrice)
    }
}