package com.example.osmtest.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.osmtest.database.PointDatabase
import com.example.osmtest.database.PointRepository
import com.example.osmtest.entity.PointEntity
import kotlinx.coroutines.launch

class MapViewModel(application: Application) : AndroidViewModel(application) {

    private val pointRepository: PointRepository

    private val _pointsLiveData = MutableLiveData<List<PointEntity>>()
    val pointsLiveData: LiveData<List<PointEntity>> = _pointsLiveData

    init {
        val pointDao = PointDatabase.getDatabase(application).pointDao()
        pointRepository = PointRepository(pointDao)
        loadPoints()
    }

    fun loadPoints() {
        viewModelScope.launch {
            val points = pointRepository.getAllPoints()
            _pointsLiveData.value = points
        }
    }

    fun addOrUpdatePoint(point: PointEntity) {
        viewModelScope.launch {
            pointRepository.addOrUpdatePoint(point)
            loadPoints()
        }
    }
}