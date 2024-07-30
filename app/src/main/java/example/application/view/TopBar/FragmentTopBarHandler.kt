//package example.application.view.TopBar
//
//import android.os.Bundle
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.Fragment
//import androidx.navigation.fragment.findNavController
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import example.application.R
//import example.application.model.UseData
//import example.application.model.source.Data
//import example.application.view.adapters.TopScrollAdapter
//
//class FragmentTopBarHandler: Fragment(R.layout.top_fragments_item), TopScrollAdapter.OnItemClickListener{
//
//
//    private lateinit var topScrollAdapter: TopScrollAdapter
//    private lateinit var topScrollItems: ArrayList<UseData.TopScroll>
//
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//
//        val topScroll = Data()
//        topScrollItems = topScroll.strings()
//        setUpRecyclerView(view)
//
//    }
//    private fun setUpRecyclerView(view: View) {
//        val topScrollRecyclerView = view.findViewById<RecyclerView>(R.id.topScrollRecycler)
//        Log.d("FragmentTopBarHandler", "RecyclerView found: $topScrollRecyclerView")
//
//        topScrollAdapter = TopScrollAdapter(topScrollItems, this)
//
//        Log.d("FragmentTopBarHandler", "TopScrollItems count: ${topScrollItems.size}")
//
//        topScrollRecyclerView.layoutManager =
//            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
//        topScrollRecyclerView.adapter = topScrollAdapter
//    }
//
//
//    override fun onItemClick(position: Int) {
//        when (topScrollItems[position].text) {
//            "CABS" -> findNavController().navigate(R.id.landingFragment2)
//
//            "ACCOMMODATIONS" -> findNavController().navigate(R.id.accommodationFragment)
//
//            "SAFARIS" -> findNavController().navigate(R.id.safarisFragment)
//
//            "$#EXCHANGE" -> findNavController().navigate(R.id.currencyFragment)
//
//            "FLIGHTS" -> findNavController().navigate(R.id.flightsFragment)
//        }
//    }
//
//}