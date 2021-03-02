package com.matteopasotti.whatmovie.view.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.matteopasotti.whatmovie.R

class TestFragment : Fragment() {

    private lateinit var title: TextView

    companion object {

        private const val TEST_TITLE = "test_title"

        fun newInstance(title: String): TestFragment{
            val args = Bundle()
            args.putString(TEST_TITLE, title)
            val fragment = TestFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return  inflater.inflate(R.layout.fragment_test, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        title = view.findViewById(R.id.textTest)
        title.text = arguments?.getString(TEST_TITLE)
    }
}