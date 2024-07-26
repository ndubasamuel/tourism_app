package example.application.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.snackbar.Snackbar
import example.application.databinding.FragmentServiceBinding
import example.application.model.network.api.RetrofitInstance
import example.application.model.network.response.services.Service
import example.application.model.repository.Repository
import example.application.utils.Resource
import example.application.viewModel.ViewModelClass
import example.application.viewModel.ViewModelFactory
import example.eclestay.view.adapters.ServiceAdapter

class ServiceFragment : Fragment() {

    private val repository: Repository by lazy {
        Repository.getInstance(RetrofitInstance.api)
    }
    private val viewModel: ViewModelClass by viewModels {
        ViewModelFactory(repository)
    }

    private lateinit var serviceBinding: FragmentServiceBinding

    private lateinit var mSearchView : SearchView
    private lateinit var mToolbar: AppBarLayout

    private lateinit var serviceAdapter: ServiceAdapter
    private lateinit var allServices: List<Service>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        serviceBinding = FragmentServiceBinding.inflate(inflater, container, false)
        return serviceBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.services.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    if (response.value.status == 200) {
                        serviceAdapter.submitList(response.value.data.Services)
                    }
                    Toast.makeText(requireContext(), response.value.message, Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
                is Resource.Failure -> {
                    Toast.makeText(requireContext(), response.errorMessage, Toast.LENGTH_SHORT).show()
                    hideProgressBar()
                }
            }
        })

        mToolbar = serviceBinding.toolBar
        setHasOptionsMenu(true)

        setUpSearchView()
        setUpRecyclerView()

        viewModel.getServices()
    }

    private fun setUpRecyclerView() {
        serviceAdapter = ServiceAdapter()
        serviceBinding.serviceRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = serviceAdapter
        }
    }

    private fun setUpSearchView() {
        mSearchView = serviceBinding.searchView

        mSearchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterServices(newText)
                return true
            }
        })
    }

    private fun filterServices(query: String?) {
        val filteredList = allServices.filter { service ->
            service.service_name.contains(query ?: "", ignoreCase = true)
        }
        serviceAdapter.submitList(filteredList)

    }



    private fun showSnackBar(message: String) {
        Snackbar.make(serviceBinding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    private fun showProgressBar() {
        serviceBinding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        serviceBinding.progressBar.visibility = View.INVISIBLE
    }
}