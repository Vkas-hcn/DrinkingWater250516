package com.leaning.against.mountains.drinkingwater.base

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.leaning.against.mountains.drinkingwater.R

abstract class BaseActivity<DB : ViewDataBinding, VM : BaseViewModel> : AppCompatActivity() {

    protected lateinit var binding: DB
    protected lateinit var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // 初始化 DataBinding
        binding = DataBindingUtil.setContentView(this, getLayoutId())
        binding.lifecycleOwner = this
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // 初始化 ViewModel
        viewModel = ViewModelProvider(this).get(getViewModelClass())

        // 初始化 UI
        initUI()
    }

    // 获取布局资源 ID
    protected abstract fun getLayoutId(): Int

    // 获取 ViewModel 的 Class 类型
    protected abstract fun getViewModelClass(): Class<VM>

    // 初始化 UI
    protected open fun initUI() {}

    // 页面无参跳转
    protected fun <T : AppCompatActivity> navigateTo(activityClass: Class<T>) {
        val intent = Intent(this, activityClass)
        startActivity(intent)
    }

    // 页面带参跳转
    protected fun <T : AppCompatActivity> navigateWithExtra(activityClass: Class<T>, extras: Bundle) {
        val intent = Intent(this, activityClass).apply {
            putExtras(extras)
        }
        startActivity(intent)
    }

}
