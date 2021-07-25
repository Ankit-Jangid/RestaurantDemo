package com.example.restrauntdemo.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.restrauntdemo.databinding.FragmentSearchBinding
import com.example.restrauntdemo.ui.RestaurantDishViewModel
import com.example.restrauntdemo.util.KeyboardExt
import kotlinx.coroutines.*


class SearchFragment : Fragment() {

    private val TAG = this::class.java.simpleName
    private lateinit var binding: FragmentSearchBinding
    private lateinit var searchJob: Job

    private lateinit var searchAdapter: SearchAdapter

    private val restaurantDishViewModel: RestaurantDishViewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(RestaurantDishViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewmodel = restaurantDishViewModel
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        KeyboardExt.showKeyboard(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchJob = Job()
        initDataAndViews()
    }

    private fun initDataAndViews() {
        toggleResults(RestaurantDishViewModel.DataStatus.EMPTY_SEARCH.name)
        restaurantDishViewModel.apply {
            addSearchListener(binding.edtSearch)
        }

        binding.btnBack.setOnClickListener { view ->
            Navigation.findNavController(view).popBackStack()
        }
        binding.btnClear.setOnClickListener { view ->
            binding.edtSearch.text.clear()
        }
        searchAdapter = SearchAdapter(requireContext(), object : SearchAdapter.CountListener {
            override fun onResultsSize(count: Int) {
                if (count > 0) {
                    toggleResults(RestaurantDishViewModel.DataStatus.AVAILABLE.name)
                } else {
                    toggleResults(RestaurantDishViewModel.DataStatus.NOT_AVAILABLE.name)
                }
            }
        })
        binding.productList.adapter = searchAdapter
        setDataAndObservers()
    }

    private fun setDataAndObservers() {
        restaurantDishViewModel.loadDummyData(requireContext())
        restaurantDishViewModel.restaurantsListLiveData.observe(viewLifecycleOwner, { it ->
            if (!it.isNullOrEmpty()) {
                searchAdapter.setRestaurantsList(it)
            }
        })
        restaurantDishViewModel.menuListLiveData.observe(viewLifecycleOwner, { it ->
            if (!it.isNullOrEmpty()) {
                searchAdapter.setMenuList(it)
            }
        })
        restaurantDishViewModel.searchQuery.observe(viewLifecycleOwner, {
            if (!it.isNullOrEmpty()) {
                searchAdapter.filter.filter(it)
            }
        })
        restaurantDishViewModel.resultLiveDataStatus.observe(viewLifecycleOwner, {
            toggleResults(it.name)
        })
    }

    private fun toggleResults(availability: String) {
        when (availability) {
            RestaurantDishViewModel.DataStatus.AVAILABLE.name -> {
                binding.btnClear.visibility = View.VISIBLE
                binding.productList.visibility = View.VISIBLE
                binding.tvNoResults.visibility = View.GONE
            }
            RestaurantDishViewModel.DataStatus.NOT_AVAILABLE.name -> {
                binding.tvNoResults.visibility = View.VISIBLE
                binding.btnClear.visibility = View.VISIBLE
                binding.productList.visibility = View.GONE
            }
            RestaurantDishViewModel.DataStatus.EMPTY_SEARCH.name -> {
                binding.btnClear.visibility = View.GONE
                binding.productList.visibility = View.GONE
                binding.tvNoResults.visibility = View.GONE
            }
        }
    }
}