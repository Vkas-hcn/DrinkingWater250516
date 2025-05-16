package com.leaning.against.mountains.drinkingwater.ui

import com.leaning.against.mountains.drinkingwater.R
import com.leaning.against.mountains.drinkingwater.base.BaseActivity
import com.leaning.against.mountains.drinkingwater.base.BaseViewModel
import com.leaning.against.mountains.drinkingwater.databinding.ActivityMainBinding


class MainActivity : BaseActivity<ActivityMainBinding, BaseViewModel>() {

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun getViewModelClass(): Class<BaseViewModel> = BaseViewModel::class.java

    override fun initUI() {
        binding.tvTttt.text = "tttt"
    }
}