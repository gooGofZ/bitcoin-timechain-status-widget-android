/*
 * Copyright (C) 2021 The Android Open Source Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.marsphoto.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.marsphoto.network.MempoolApi
import kotlinx.coroutines.launch

/**
 * The [ViewModel] that is attached to the [OverviewFragment].
 */
class OverviewViewModel : ViewModel() {

    // The internal MutableLiveData that stores the status of the most recent request
    private val _blockHeight = MutableLiveData<String>()
    private val _fastestFee = MutableLiveData<String>()
    private val _halfHourFee = MutableLiveData<String>()
    private val _hourFee = MutableLiveData<String>()
    private val _economyFee = MutableLiveData<String>()
    private val _minimumFee = MutableLiveData<String>()
    private val _recommendedFees = MutableLiveData<String>()

    // The external immutable LiveData for the request status
    val blockHeight : LiveData<String> = _blockHeight
    val recommendedFees: LiveData<String> = _recommendedFees
    val fastestFee: LiveData<String> = _fastestFee
    val halfHourFee: LiveData<String> = _halfHourFee
    val hourFee: LiveData<String> = _hourFee
    val economyFee: LiveData<String> = _economyFee
    val minimumFee: LiveData<String> = _minimumFee

    /**
     * Call getMarsPhotos() on init so we can display status immediately.
     */
    init {
//        getMarsPhotos()
        getBlockTipHeight()
    }

//    {
//        "fastestFee": 27,
//        "halfHourFee": 1,
//        "hourFee": 1,
//        "economyFee": 1,
//        "minimumFee": 1
//    }

    private fun getBlockTipHeight() {
        viewModelScope.launch {
            val blockHeight = MempoolApi.retrofitService.getBlockTipHeight()
            _blockHeight.value = blockHeight
        }

        viewModelScope.launch {
            val recommendedFees = MempoolApi.retrofitService.getRecommendedFee()
            _fastestFee.value = recommendedFees.fastestFee
            _halfHourFee.value = recommendedFees.halfHourFee
            _hourFee.value =  recommendedFees.hourFee
            _economyFee.value =  recommendedFees.economyFee
            _minimumFee.value = recommendedFees.minimumFee
        }
    }

}
