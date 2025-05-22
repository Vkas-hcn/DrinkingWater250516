package com.leaning.against.mountains.drinkingwater.ui.history

import androidx.recyclerview.widget.LinearLayoutManager
import com.leaning.against.mountains.drinkingwater.R
import com.leaning.against.mountains.drinkingwater.base.BaseActivity
import com.leaning.against.mountains.drinkingwater.base.BaseViewModel
import com.leaning.against.mountains.drinkingwater.databinding.ActivityHistoryBinding
import com.leaning.against.mountains.drinkingwater.ui.main.MainUtils
import com.leaning.against.mountains.drinkingwater.utils.HistoryBean
import com.leaning.against.mountains.drinkingwater.utils.WaterDrinkBean

class HistoryActivity : BaseActivity<ActivityHistoryBinding, BaseViewModel>(), HistoryContract.View {
    private lateinit var adapter: HistoryAdapter
    private lateinit var presenter: HistoryPresenter

    override fun getLayoutId(): Int = R.layout.activity_history
    override fun getViewModelClass(): Class<BaseViewModel> = BaseViewModel::class.java

    override fun initUI() {
        binding.imageView.setOnClickListener { finish() }
        setupRecyclerView()

        presenter = HistoryPresenter(this)
        presenter.loadHistoryData()
    }

    private fun setupRecyclerView() {
        adapter = HistoryAdapter(this, mutableListOf())
        binding.recyclerHis.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@HistoryActivity.adapter
        }
    }

    // 实现 View 接口方法
    override fun showData(data: List<HistoryBean>) {
        adapter.updateData(data)
        binding.recyclerHis.adapter = adapter
        presenter.calculateAverages(MainUtils.getWaterList())
    }

    override fun showAverages(totalWater: Int, totalDays: Int) {
        binding.tvAllNum.text = "${totalWater}ml"
        binding.tvAllD.text = totalDays.toString()
    }
}
