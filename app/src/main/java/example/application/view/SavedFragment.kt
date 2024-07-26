package example.application.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import example.application.R
import example.application.databinding.FragmentSavedBinding


class SavedFragment : Fragment() {

    private lateinit var savedFragmentBinding: FragmentSavedBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

       savedFragmentBinding = FragmentSavedBinding.inflate(inflater, container, false)
        return savedFragmentBinding.root
    }

}