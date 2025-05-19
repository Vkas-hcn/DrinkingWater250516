package com.leaning.against.mountains.drinkingwater.ui.first

import android.content.Intent
import androidx.activity.addCallback
import androidx.lifecycle.lifecycleScope
import com.leaning.against.mountains.drinkingwater.R
import com.leaning.against.mountains.drinkingwater.base.BaseActivity
import com.leaning.against.mountains.drinkingwater.base.BaseViewModel
import com.leaning.against.mountains.drinkingwater.databinding.ActivityFirstBinding
import com.leaning.against.mountains.drinkingwater.ui.main.MainActivity
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FirstActivity : BaseActivity<ActivityFirstBinding, BaseViewModel>() {

    override fun getLayoutId(): Int = R.layout.activity_first

    override fun getViewModelClass(): Class<BaseViewModel> = BaseViewModel::class.java
    private var job: Job? = null

    override fun initUI() {
        onBackPressedDispatcher.addCallback {
        }
        var num = 0
        job = lifecycleScope.launch {
            while (true) {
                binding.loiGuide.progress = num
                num++
                if (num >= 100) {
                    job?.cancel()
                    binding.loiGuide.progress = 100
                    startActivity(Intent(this@FirstActivity, MainActivity::class.java))
                    finish()
                }
                delay(20)
            }

        }
    }
}