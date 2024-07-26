package example.application.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import example.application.model.UseData
import example.application.R
import example.application.model.source.Data
import example.application.databinding.FragmentCurrencyBinding
import example.application.view.adapters.TopScrollAdapter
import java.text.DecimalFormat


class CurrencyFragment : Fragment(), TopScrollAdapter.OnItemClickListener {

    private lateinit var currencyBinding: FragmentCurrencyBinding

    private lateinit var topScrollAdapter: TopScrollAdapter
    private var topScroll: ArrayList<UseData.TopScroll> = ArrayList()

    private val exchangeRates = mapOf(
        "USD" to 1.0,
        "EUR" to 0.85,
        "GBP" to 0.73,
        "JPY" to 110.34
    )


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        currencyBinding = FragmentCurrencyBinding.inflate(inflater, container, false)
        return currencyBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currencyBinding.etAmount.addTextChangedListener(amountTextWatcher)
        currencyBinding.etFromCurrency.addTextChangedListener(amountTextWatcher)
        currencyBinding.etToCurrency.addTextChangedListener(amountTextWatcher)

        val useData = Data()
        topScroll = useData.strings()

        setUpRecyclerView()

    }
    private val amountTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable?) {
            calculateTotal()
        }

    }
    private fun calculateTotal() {
        val amountStr = currencyBinding.etAmount.text.toString()
        val fromCurrency = currencyBinding.etFromCurrency.text.toString()
        val toCurrency = currencyBinding.etToCurrency.text.toString()

        if (amountStr.isNotEmpty() && exchangeRates.containsKey(fromCurrency) && exchangeRates.containsKey(
                toCurrency
            )
        ) {
            val amount = amountStr.toDouble()
            val exchangeRateFrom = exchangeRates[fromCurrency] ?: 1.0
            val exchangeRateTo = exchangeRates[toCurrency] ?: 1.0

            val total = amount / exchangeRateFrom * exchangeRateTo
            val decimalFormat = DecimalFormat("#.##")
            currencyBinding.etTotals.setText(decimalFormat.format(total))
        } else {
            currencyBinding.etTotals.text.clear()
        }
    }


    private fun setUpRecyclerView() {
        topScrollAdapter = TopScrollAdapter(topScroll, this)

        val topScrollRecyclerView = currencyBinding.topScrollRecycler
        topScrollRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        topScrollRecyclerView.adapter = topScrollAdapter
    }

    override fun onItemClick(position: Int) {
        val categoryName = topScroll[position].text
        when (categoryName) {
            "CABS" -> {
                findNavController().navigate(R.id.action_currencyFragment_to_landingFragment2)

            }
            "ACCOMMODATIONS" -> {
                findNavController().navigate(R.id.action_currencyFragment_to_accommodationFragment)
            }
            "SAFARIS" -> {
                findNavController().navigate(R.id.action_currencyFragment_to_safarisFragment)

            }
            "$#EXCHANGE" -> {

            }
            "FLIGHTS" -> {
                findNavController().navigate(R.id.action_currencyFragment_to_flightsFragment)

            }
        }
    }

}