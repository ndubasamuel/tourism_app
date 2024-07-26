package example.application.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import example.application.R
import example.application.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {

    private lateinit var profileBinding: FragmentProfileBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        profileBinding = FragmentProfileBinding.inflate(inflater, container, false)
        return profileBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profileBinding.navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> {

                    Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
                        .navigate(R.id.action_profileFragment_to_landingFragment2)
                }
                R.id.contact -> {
                    Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)

                }
                R.id.profile -> {
                }
                R.id.gallery -> {
                    Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)

                }
                R.id.about -> {
                    Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)

                }
                R.id.login -> {
                    Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)

                }
                R.id.share -> {
                }
                R.id.rate_us -> {
                }
            }
            profileBinding.drawerLayout.closeDrawers()
            true
        }
    }

}