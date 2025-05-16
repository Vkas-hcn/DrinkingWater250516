package com.leaning.against.mountains.drinkingwater.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

open class BaseViewModel(application: Application) : AndroidViewModel(application) {
    // 可以在这里添加通用的 ViewModel 逻辑，例如 loading 状态、错误处理等
}
