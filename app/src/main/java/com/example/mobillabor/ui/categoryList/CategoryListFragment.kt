package com.example.mobillabor.ui.categoryList

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mobillabor.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryListFragment : Fragment() {

    private lateinit var viewModel: CategoryListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_category_list, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[CategoryListViewModel::class.java]
        // TODO: Use the ViewModel
    }

}