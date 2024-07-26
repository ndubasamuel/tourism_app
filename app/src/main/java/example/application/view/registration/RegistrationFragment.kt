package example.application.view.registration

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import example.application.R
import example.application.databinding.FragmentRegistrationBinding
import example.application.model.network.api.RetrofitInstance
import example.application.model.repository.Repository
import example.application.utils.Resource
import example.application.viewModel.ViewModelClass
import example.application.viewModel.ViewModelFactory


class RegistrationFragment : Fragment() {


    private lateinit var registrationBinding: FragmentRegistrationBinding

    private val repository: Repository by lazy {
        Repository.getInstance(RetrofitInstance.api)
    }
    private val viewModel: ViewModelClass by viewModels {
        ViewModelFactory(repository)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        registrationBinding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return registrationBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        registrationBinding.apply {
            signUpButton.setOnClickListener{
                validateUser()
            }
        }

        viewModel.registerData.observe(viewLifecycleOwner, Observer{ authResponse ->
            when (authResponse){
                is Resource.Success -> {
                    if (authResponse.value.status == 200) {
                        hideProgressBar()
                        findNavController().navigate(R.id.action_registrationFragment_to_landingFragment2)
                    }
                    Toast.makeText(requireContext(), "${authResponse.value.Message} ):", Toast.LENGTH_SHORT).show()

                }
                is Resource.Loading -> {
                    showProgressBar()
                }

                is Resource.Failure -> {
                    val errorMessage = authResponse.errorMessage
                    Toast.makeText(requireContext(), ":( $errorMessage ", Toast.LENGTH_SHORT).show()


                }
                else -> {
                    showSnackBar("Unexpected error :(")
                }
            }
        })


    }

    fun validateUser(){
        val firstName = registrationBinding.firstName.text.toString().trim()
        val secondName = registrationBinding.secondName.text.toString().trim()
        val email = registrationBinding.email.text.toString().trim()
        val phone = registrationBinding.phoneNumber.text.toString().trim()
        val password = registrationBinding.etPassword.text.toString().trim()


        val confirmPassword = registrationBinding.etConfirmPassword.text.toString().trim()




        if (firstName.isEmpty()){
            Toast.makeText(requireContext(), " :( First Name Required", Toast.LENGTH_SHORT).show()
            return
        }
        if (secondName.isEmpty()){
            Toast.makeText(requireContext(), " :( Second Name Required", Toast.LENGTH_SHORT).show()
            return
        }
        if (email.isEmpty() || !email.matches(Regex("^[a-zA-Z0-9_.±]+@[a-zA-Z0-9-]+.[a-zA-Z0-9-.]+$"))) {
            Toast.makeText(requireContext(), " :( User Email Required", Toast.LENGTH_SHORT).show()
            return
        }
        if (phone.isNullOrEmpty() || !phone.matches(Regex("^(254|0)([7][0-9]|[1][0-1]){1}[0-9]{1}[0-9]{6}\$"))){
            Toast.makeText(requireContext(), " :( Phone Number Required", Toast.LENGTH_SHORT).show()
            return

        }
        if (password.isEmpty()){
            Toast.makeText(requireContext(), " :( Password Required", Toast.LENGTH_SHORT).show()
            return
        }
        if (!password.matches(Regex("^(?=.*\\d)(?=.*[a-zA-Z])(?=.*[A-Z])(?=.*[-\\#\\\$\\.\\%\\&\\*])(?=.*[a-zA-Z]).{8,16}\$"))){
            Toast.makeText(requireContext(), "Use a More Secure Password ", Toast.LENGTH_SHORT).show()
        }
        if (confirmPassword.isEmpty() || !password.matches(Regex("^(?=.*\\d)(?=.*[a-zA-Z])(?=.*[A-Z])(?=.*[-\\#\\\$\\.\\%\\&\\*])(?=.*[a-zA-Z]).{8,16}\$"))) {
            Toast.makeText(requireContext(), " :( Confirm Password", Toast.LENGTH_SHORT).show()
            return

        }
        if (password.length != confirmPassword.length){
            Toast.makeText(requireContext(), " :( Password Has to match", Toast.LENGTH_SHORT).show()
        }

        viewModel.registerUser(firstName, secondName, email, phone, password)
        return

    }

    private fun showSnackBar(message: String) {
        Snackbar.make(registrationBinding.root, message, Snackbar.LENGTH_SHORT).show()
    }
    private fun showProgressBar() {
        val progressBar = registrationBinding.progressBar
        progressBar.visibility = View.VISIBLE
    }
    private fun hideProgressBar() {
        val progressBar = registrationBinding.progressBar
        progressBar.visibility = View.INVISIBLE
    }
}