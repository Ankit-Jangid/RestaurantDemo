package com.example.restrauntdemo.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.restrauntdemo.R
import com.example.restrauntdemo.databinding.FragmentHomeBinding
import com.example.restrauntdemo.ui.RestaurantDishViewModel
import com.example.restrauntdemo.util.KeyboardExt


class HomeFragment : Fragment() {
    private val TAG = this::class.java.simpleName
    private lateinit var binding: FragmentHomeBinding

    private val restaurantDishViewModel: RestaurantDishViewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(RestaurantDishViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        initViews()
        return binding.root
    }

    private fun initViews() {
        binding.root.findViewById<View>(R.id.container).setOnClickListener { view ->
            Navigation.findNavController(view)
                .navigate(R.id.action_homeFragment_to_searchFragment)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadAndSetData()
    }

    private fun loadAndSetData() {
        val restaurantsAdapter = RestaurantsSuggestionsAdapter(requireContext())
        val suggestionsAdapter = SuggestionsAdapter(requireContext())
        binding.restaurantsList.adapter = restaurantsAdapter
        binding.suggestionList.adapter = suggestionsAdapter

        restaurantDishViewModel.loadDummyData(requireContext())
        restaurantDishViewModel.restaurantsListLiveData.observe(viewLifecycleOwner, { it ->
            if (!it.isNullOrEmpty()) {
                restaurantsAdapter.updateListData(it)
            }
        })
        restaurantDishViewModel.menuListLiveData.observe(viewLifecycleOwner, { it ->
            if (!it.isNullOrEmpty()) {
                suggestionsAdapter.updateListData(it)
            }
        })
    }

    override fun onStart() {
        super.onStart()
        KeyboardExt.hideKeyboard(binding.root, requireContext())
    }
}