package example.application.view.registration

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.navigation.Navigation
import example.application.R

class RegistrationDialog(): DialogFragment() {


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity.let {
            val builder = AlertDialog.Builder(it)
                .setMessage("Consider Login or Create an Account")
                .setPositiveButton("LOGIN") { dialog, which ->

                    Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(R.id.loginFragment)
                    dismiss()
                }
                .setNegativeButton("CREATE ACCOUNT") { Dialog, which ->

                    Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(R.id.registrationFragment)
                    dismiss()
                }
            builder.create()

        }?: throw java.lang.IllegalStateException("Activity Cannot be Null")
    }
}