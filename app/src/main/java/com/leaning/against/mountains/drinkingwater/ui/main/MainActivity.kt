package com.leaning.against.mountains.drinkingwater.ui.main

import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.leaning.against.mountains.drinkingwater.R
import com.leaning.against.mountains.drinkingwater.base.BaseActivity
import com.leaning.against.mountains.drinkingwater.base.BaseViewModel
import com.leaning.against.mountains.drinkingwater.databinding.ActivityMainBinding
import com.leaning.against.mountains.drinkingwater.ui.history.HistoryActivity
import com.leaning.against.mountains.drinkingwater.ui.setting.SettingActivity
import com.leaning.against.mountains.drinkingwater.utils.WaterDrinkBean
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class MainActivity : BaseActivity<ActivityMainBinding, BaseViewModel>(), MainContract.View, AddNumAdapter.OnItemClickListener {

    private lateinit var presenter: MainPresenter
    private lateinit var todayAdapter: TodayWaterAdapter
    private lateinit var addNumAdapter: AddNumAdapter

    override fun getLayoutId(): Int = R.layout.activity_main
    override fun getViewModelClass(): Class<BaseViewModel> = BaseViewModel::class.java

    override fun initUI() {
        presenter = MainPresenter(this)
        setupAdapters()
        setupListeners()
        loadData()
    }

    private fun setupAdapters() {
        todayAdapter = TodayWaterAdapter()
        binding.recyTodayDrink.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = todayAdapter
        }

        addNumAdapter = AddNumAdapter(this)
        binding.recyclerNum.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = addNumAdapter
        }
    }

    private fun setupListeners() {
        with(binding) {
            imgHis.setOnClickListener { navigateTo(HistoryActivity::class.java) }
            imgSet.setOnClickListener { navigateTo(SettingActivity::class.java) }
            atvToday.setOnClickListener { navigateTo(HistoryActivity::class.java) }
            imgAdd.setOnClickListener { inWater.dialogCon.isVisible = true }
            tvGoal.setOnClickListener { inGoal.dialogCon.isVisible = true }

            inGoal.tvConfirm.setOnClickListener {
                inGoal.editNum.text.toString().trim().toIntOrNull()?.let {
                    presenter.updateGoal(it)
                    loadData()
                    inGoal.dialogCon.isVisible = false
                } ?: run {
                    Toast.makeText(this@MainActivity, "Please enter the correct value", Toast.LENGTH_SHORT).show()
                }
            }

            inGoal.tvCancel.setOnClickListener { inGoal.dialogCon.isVisible = false }

            inWater.tvConfirm.setOnClickListener {
                inWater.editNum.text.toString().trim().toIntOrNull()?.let {
                    if (it <= 0) {
                        Toast.makeText(this@MainActivity, "Please enter the correct value", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                    MainUtils.saveAddNumList(it)
                    loadData()
                    inWater.dialogCon.isVisible = false
                } ?: run {
                    Toast.makeText(this@MainActivity, "Please enter the correct value", Toast.LENGTH_SHORT).show()
                }
            }

            inWater.tvCancel.setOnClickListener { inWater.dialogCon.isVisible = false }
        }
    }

    private fun loadData() {
        presenter.loadTodayData()
        presenter.loadAddNumData()
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    // 实现 View 接口方法
    override fun showTodayList(data: List<WaterDrinkBean>) {
        todayAdapter.submitList(data)
        binding.tvDrinkMl.text = "${MainUtils.getTodayTotalDrink()}ml"
        val data = MainUtils.getTodayWaterList().lastOrNull()?.goalNum ?: 2500
        binding.tvGoal.text = "${data}ml"

    }

    override fun showAddNumList(data: List<Int>) {
        addNumAdapter.submitList(data)
    }

    override fun updateGoal(goal: Int) {
        binding.tvGoal.text = "${goal}ml"
        binding.inGoal.editNum.setText(goal.toString())
    }

    override fun updateProgress(progress: Int, totalDrink: Int) {
        binding.cupProgressView.setProgress(progress)
        binding.tvDrinkPro.text = "$progress%"
    }

    override fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun showFinishState() {
        binding.conShowNum.isVisible = false
        binding.conShowFinish.isVisible = true
        binding.tvDrinkPro.text = "100%"
    }

    override fun hideFinishState() {
        binding.conShowNum.isVisible = true
        binding.conShowFinish.isVisible = false
    }

    override fun onItemClick(amount: Int) {
        if(binding.conShowFinish.isVisible){
            return
        }
        presenter.addWaterRecord(amount)
    }
}
