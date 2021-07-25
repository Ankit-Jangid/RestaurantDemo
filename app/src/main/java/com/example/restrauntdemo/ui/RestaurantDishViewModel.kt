package com.example.restrauntdemo.ui

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.restrauntdemo.model.Menus
import com.example.restrauntdemo.model.Restaurants
import com.example.restrauntdemo.util.DataRepository
import kotlinx.coroutines.*

//For the sake of data fetching, common viewmodel can be used, for other extended business logics,
//it can be de-coupled
class RestaurantDishViewModel : ViewModel() {

    private val TAG = this::class.java.simpleName
    private lateinit var searchJob: Job

    enum class DataStatus { AVAILABLE, NOT_AVAILABLE, EMPTY_SEARCH }

    private var resultLiveDataStatus_ = MutableLiveData<DataStatus>()
    val resultLiveDataStatus: LiveData<DataStatus>
        get() = resultLiveDataStatus_


    private var searchQuery_ = MutableLiveData<String>()
    val searchQuery: LiveData<String>
        get() = searchQuery_


    var restaurantsListLiveData = MutableLiveData<ArrayList<Restaurants>>()
    var menuListLiveData = MutableLiveData<ArrayList<Menus>>()


    internal fun loadDummyData(context: Context) {
        val restaurantsList = DataRepository(context).getRestaurantsList()
        val menuList = DataRepository(context).getMenuList()
        restaurantsListLiveData.value = restaurantsList
        menuListLiveData.value = menuList
    }


    internal fun addSearchListener(editText: EditText) {
        searchJob = Job()
        editText.requestFocus()
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                Log.d(TAG, "beforeTextChanged: ${s.toString()}")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.d(TAG, "onTextChanged: ${s.toString()}")
            }

            override fun afterTextChanged(s: Editable?) {
                Log.d(TAG, "afterTextChanged: ${s.toString()}")
                val query = s.toString().trim()
                query.let { query_ ->
                    if (query_.isEmpty()) {
                        resultLiveDataStatus_.value = DataStatus.EMPTY_SEARCH
//                        toggleResults(SearchFragment.DataStatus.EMPTY_SEARCH)
                    } else {
                        searchJob.cancel()
                        searchJob = CoroutineScope(Dispatchers.Main).launch {
                            delay(300)
                            Log.d(TAG, "searching...:$query")
                            searchQuery_.value = query
                            resultLiveDataStatus_.value = DataStatus.AVAILABLE
//                            searchForResults(query_)
                        }
                    }
                }
            }
        })
    }


}