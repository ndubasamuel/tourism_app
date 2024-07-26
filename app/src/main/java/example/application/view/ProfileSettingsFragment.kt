package example.application.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import example.application.R
import example.application.databinding.FragmentProfileSettingsBinding

class ProfileSettingsFragment : Fragment() {

    private lateinit var profileSettingsBinding: FragmentProfileSettingsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        profileSettingsBinding = FragmentProfileSettingsBinding.inflate(inflater, container, false)
        return profileSettingsBinding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }



}