package com.leaning.against.mountains.drinkingwater.ui.main

import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.leaning.against.mountains.drinkingwater.R
import com.leaning.against.mountains.drinkingwater.base.BaseActivity
import com.leaning.against.mountains.drinkingwater.base.BaseViewModel
import com.leaning.against.mountains.drinkingwater.databinding.ActivityMainBinding
import com.leaning.against.mountains.drinkingwater.ui.setting.SettingActivity
import com.leaning.against.mountains.drinkingwater.utils.WaterDrinkBean
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class MainActivity : BaseActivity<ActivityMainBinding, BaseViewModel>() , AddNumAdapter.OnItemClickListener{

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun getViewModelClass(): Class<BaseViewModel> = BaseViewModel::class.java
    private lateinit var adapterAddNum: AddNumAdapter
    private lateinit var adapterToady: TodayWaterAdapter

    override fun initUI() {
        clikFun()
        iniAdapter()
        initNumAdapter()
        loadData()
        loadAddNumData()
        getGoalNum()
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }
    private fun iniAdapter(){
        adapterToady = TodayWaterAdapter()
        binding.recyTodayDrink.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = adapterToady
        }
    }
    private fun initNumAdapter(){
        adapterAddNum = AddNumAdapter(this)
        binding.recyclerNum.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = adapterAddNum
        }
    }
    private fun loadData() {
        val todayList = MainUtils.getTodayWaterList()
        adapterToady.submitList(todayList)
        getGoalNum()
    }
    fun clikFun(){
        with(binding){
            binding.imgSet.setOnClickListener {
                navigateTo(SettingActivity::class.java)
            }
            binding.inWater.dialogCon.setOnClickListener {  }
            binding.inGoal.dialogCon.setOnClickListener {
            }
            binding.inWater.tvCancel.setOnClickListener {
                binding.inWater.dialogCon.isVisible = false
            }
            binding.inGoal.tvCancel.setOnClickListener {
                binding.inGoal.dialogCon.isVisible = false
            }
            binding.inGoal.tvConfirm.setOnClickListener {
                binding.inGoal.dialogCon.isVisible = false
                val goalNum = binding.inGoal.editNum.text.toString().trim().toIntOrNull()
                if (goalNum != null && goalNum > 0) {
                    MainUtils.updateTodayGoal(goalNum)
                    loadData()
                    inGoal.dialogCon.isVisible = false
                    MainUtils.cloneKey(this@MainActivity,binding.inGoal.dialogCon)
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        "Please enter the correct value",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            binding.inWater.tvConfirm.setOnClickListener {
                binding.inWater.editNum.text.toString().trim().toIntOrNull()?.let {
                    if (it <= 0) {
                        Toast.makeText(
                            this@MainActivity,
                            "Please enter the correct value",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@setOnClickListener
                    }
                    MainUtils.saveAddNumList(it)
                    loadAddNumData()
                    inWater.dialogCon.isVisible = false
                    MainUtils.cloneKey(this@MainActivity,binding.inWater.dialogCon)
                } ?: run {
                    Toast.makeText(
                        this@MainActivity,
                        "Please enter the correct value",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }
            imgAdd.setOnClickListener {
                binding.inWater.dialogCon.isVisible = true
            }
            tvGoal.setOnClickListener {
                binding.inGoal.dialogCon.isVisible = true
            }
        }
    }
    private fun loadAddNumData() {
        val numList = MainUtils.getAddNumList()
        adapterAddNum.submitList(numList)
    }

    override fun onItemClick(amount: Int) {
        addWaterRecord(amount)
    }
    private fun addWaterRecord(amount: Int) {
        binding.inGoal.editNum.text.toString().trim().toIntOrNull()?.let {
            if (it <= 0) {
                Toast.makeText(
                    this,
                    "Please enter the correct value",
                    Toast.LENGTH_SHORT
                ).show()
                return
            }
            val newRecord = WaterDrinkBean(
                id = System.currentTimeMillis().toString(),
                date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()),
                goalNum = it,
                drinkNum = amount,
                drinkTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
            )
            MainUtils.addWaterBean(newRecord)
            loadData()
        }
    }
    private fun getGoalNum() {
        val data = MainUtils.getTodayWaterList().lastOrNull()?.goalNum ?: 25000
        val proData = MainUtils.getTodayProgress()
        binding.tvGoal.text = "${data}ml"
        binding.inGoal.editNum.setText(data.toString())
        binding.tvDrinkMl.text = MainUtils.getTodayTotalDrink().toString() + "ml"
        binding.cupProgressView.setProgress(proData)
        binding.tvDrinkPro.text = if (proData >= 100) {
            binding.conShowNum.isVisible = false
            binding.conShowFinish.isVisible = true
            "100%"
        } else {
            binding.conShowNum.isVisible = true
            binding.conShowFinish.isVisible = false
            "${proData}%"
        }
    }
}