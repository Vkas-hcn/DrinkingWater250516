package com.leaning.against.mountains.drinkingwater.ui.record

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.leaning.against.mountains.drinkingwater.R
import com.leaning.against.mountains.drinkingwater.base.BaseActivity
import com.leaning.against.mountains.drinkingwater.base.BaseViewModel
import com.leaning.against.mountains.drinkingwater.databinding.ActivityRecordBinding
import com.leaning.against.mountains.drinkingwater.ui.main.MainUtils
import com.leaning.against.mountains.drinkingwater.utils.WaterDrinkBean

class RecordActivity : BaseActivity<ActivityRecordBinding, BaseViewModel>(), RecordContract.View {
    private lateinit var adapter: RecordAdapter
    private lateinit var lineChart: LineChart
    private lateinit var presenter: RecordPresenter
    private var selectedDate: String? = null

    override fun getLayoutId(): Int = R.layout.activity_record

    override fun getViewModelClass(): Class<BaseViewModel> = BaseViewModel::class.java

    override fun initUI() {
        binding.imageView.setOnClickListener { finish() }

        selectedDate = intent.getStringExtra("SELECTED_DATE")
        initRecyclerView()
        initLineChart()

        presenter = RecordPresenter(this)
        presenter.loadRecordData(selectedDate)

    }

    private fun initRecyclerView() {
        adapter = RecordAdapter()
        binding.recyclerRecord.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@RecordActivity.adapter
        }
    }

    private fun initLineChart() {
        lineChart = binding.lineChart
        lineChart.description.isEnabled = false
        lineChart.setTouchEnabled(true)
        lineChart.setDrawGridBackground(false)
        lineChart.setPinchZoom(true)
        lineChart.setScaleEnabled(true)
        lineChart.legend.isEnabled = false

        val xAxis = lineChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)
        xAxis.granularity = 1f
        xAxis.valueFormatter = IndexAxisValueFormatter(ChartUtils.getXAxisValues())

        val yAxis = lineChart.axisLeft
        yAxis.setDrawGridLines(false)
        yAxis.axisMinimum = 0f
        yAxis.axisMaximum = 100f

        lineChart.axisRight.isEnabled = false
    }

    // 实现 View 接口方法
    override fun showRecords(data: List<WaterDrinkBean>) {
        adapter.submitList(data)
    }

    override fun updateChart(entriesWithData: List<Pair<Entry, WaterDrinkBean>>) {
        val entries = entriesWithData.map { it.first }

        val dataSet = LineDataSet(entries, "").apply {
            color = resources.getColor(R.color.chart_1)
            valueTextColor = resources.getColor(R.color.white)
            setCircleColor(resources.getColor(R.color.chart_1))
            lineWidth = 2f
            circleRadius = 4f
            setDrawValues(true)
        }

        lineChart.data = LineData(dataSet)

        val marker = CustomMarker(this, R.layout.custom_marker_layout, entriesWithData)
        lineChart.marker = marker

        lineChart.invalidate()
    }
}

