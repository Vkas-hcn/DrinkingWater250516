package com.leaning.against.mountains.drinkingwater.ui.setting

import android.content.Intent
import androidx.activity.addCallback
import com.leaning.against.mountains.drinkingwater.R
import com.leaning.against.mountains.drinkingwater.base.BaseActivity
import com.leaning.against.mountains.drinkingwater.base.BaseViewModel
import com.leaning.against.mountains.drinkingwater.databinding.ActivitySettingBinding

class SettingActivity : BaseActivity<ActivitySettingBinding, BaseViewModel>() {

    override fun getLayoutId(): Int = R.layout.activity_setting

    override fun getViewModelClass(): Class<BaseViewModel> = BaseViewModel::class.java
    override fun initUI() {
        onBackPressedDispatcher.addCallback(this) {
            finish()
        }
        with(binding) {
            imageView.setOnClickListener {
                finish()
            }
            tvShare.setOnClickListener {
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/plain"
                intent.putExtra(
                    Intent.EXTRA_TEXT,
                    "https://play.google.com/store/apps/details?id=${this@SettingActivity.packageName}"
                )
                try {
                    startActivity(Intent.createChooser(intent, "Share on:"))
                } catch (ex: android.content.ActivityNotFoundException) {
                    // Handle error
                }
            }
            tvPp.setOnClickListener {
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    android.net.Uri.parse("https://www.google.com/")
                )//TODO add privacy policy
                try {
                    startActivity(intent)
                } catch (ex: android.content.ActivityNotFoundException) {
                    // Handle error
                }
            }
        }
    }
}
