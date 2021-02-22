package com.example.navigation2.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.navigation2.R
import com.example.navigation2.navigate

class NewFragment : Fragment() {
    private var count : Int = 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_new, container, false)
        view.findViewById<Button>(R.id.button).setOnClickListener {
            navigate(NewFragmentDirections.actionNewToNew(++count))
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        count = NewFragmentArgs.fromBundle(requireArguments()).count
        view.findViewById<TextView>(R.id.newText).text = count.toString()
    }
}

