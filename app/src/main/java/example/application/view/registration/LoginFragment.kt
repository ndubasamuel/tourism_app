package example.application.view.registration

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import androidx.navigation.fragment.findNavController
import example.application.R
import example.application.databinding.FragmentLoginBinding
import example.application.model.network.api.RetrofitInstance
import example.application.model.repository.Repository
import example.application.utils.Resource
import example.application.viewModel.ViewModelClass
import example.application.viewModel.ViewModelFactory


class LoginFragment : Fragment() {


    private lateinit var loginFragmentBinding: FragmentLoginBinding

    private val repository: Repository by lazy {
        Repository.getInstance(RetrofitInstance.api)
    }
    private val viewModel: ViewModelClass by viewModels {
        ViewModelFactory(repository)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        loginFragmentBinding = FragmentLoginBinding.inflate(inflater, container, false)
        return loginFragmentBinding.root

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginFragmentBinding.apply {
            buttonLogin.setOnClickListener {
                validate()
            }
            createAccount.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
            }
            forgotPassword.setOnClickListener{
            }
        }
//        Observe User data
        viewModel.loginData.observe(viewLifecycleOwner, Observer { loginResponse ->
            when (loginResponse) {
                is Resource.Success -> {
                    hideProgressBar()
                    loginResponse.value.data.token.let{

                        if (loginResponse.value.status == 200){
                            hideProgressBar()
                            findNavController().navigate(R.id.action_loginFragment_to_profileFragment)
                        }

                        Toast.makeText(requireContext(), "${loginResponse.value.Message} ):", Toast.LENGTH_SHORT).show()
                    }

                }
                is Resource.Failure -> {
                    val errorMessage = loginResponse.errorMessage

                    Toast.makeText(requireContext(), "$errorMessage :(", Toast.LENGTH_SHORT).show()

                    hideProgressBar()
                }

                is Resource.Loading -> {
                    showProgressBar()
                    loginFragmentBinding.progressBar.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun validate(){
        val email = loginFragmentBinding.etEmail.text.toString().trim()
        val password = loginFragmentBinding.etPassword.text.toString().trim()

        if (email.isEmpty()) {
            Toast.makeText(requireContext(), "Email Required", Toast.LENGTH_SHORT).show()
            return
        }
        if (password.isEmpty()) {
            Toast.makeText(requireContext(), "Password Required", Toast.LENGTH_SHORT).show()
            return
        }


        viewModel.loginUser(email, password)

    }

    private fun showSnackBar(message: String) {
        Snackbar.make(loginFragmentBinding.root, message, Snackbar.LENGTH_SHORT).show()
    }
    private fun showProgressBar() {
        val progressBar = loginFragmentBinding.progressBar
        progressBar.visibility = View.INVISIBLE
    }
    private fun hideProgressBar() {
        val progressBar = loginFragmentBinding.progressBar
        progressBar.visibility = View.VISIBLE
    }

}