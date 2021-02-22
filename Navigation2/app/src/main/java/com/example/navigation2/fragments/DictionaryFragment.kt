package com.example.navigation2.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.navigation2.R
import com.example.navigation2.navigate

class DictionaryFragment : Fragment() {
    override fun onCreateView(
        inflater:LayoutInflater,
        container:ViewGroup?,
        savedInstanceState:Bundle?
    ): View
    {
        return inflater.inflate(R.layout.fragment_dictionary, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.button).setOnClickListener {
            navigate(DictionaryFragmentDirections.actionDictionaryToNew(1))
        }
    }
}
