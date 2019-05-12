package com.yesayasoftware.learning.ui.base

import androidx.lifecycle.ViewModel
import com.yesayasoftware.learning.data.repository.YesayaSoftwareRepository

abstract class YesayaSoftwareViewModel(
    private val yesayaSoftwareRepository: YesayaSoftwareRepository
) : ViewModel()