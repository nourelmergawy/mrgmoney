package com.mrg.mrgmoney.fragments

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement
import com.mrg.mrgmoney.DataBase.Coin
import com.mrg.mrgmoney.ViewModel.CoinViewModel
import com.mrg.mrgmoney.databinding.FragmentGraphBinding
import kotlin.collections.ArrayList

class GraphFragment : Fragment() {
  private lateinit var binding: FragmentGraphBinding
  private lateinit var coinViewModel: CoinViewModel
//  private val data = ArrayList<ILineDataSet>()
  private val chartGain =ArrayList<AASeriesElement >()
  private val chartSpend =ArrayList<AASeriesElement >()
  private lateinit var aaChartModel : AAChartModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentGraphBinding.inflate(layoutInflater,container,false)

        val aaChartView = binding.aaChartView

        coinViewModel = ViewModelProvider(activity?.viewModelStore!!,
            ViewModelProvider.AndroidViewModelFactory
                .getInstance(activity?.application!!))
            .get(CoinViewModel::class.java)

        coinViewModel.allCoins.observe(viewLifecycleOwner , Observer {
            setData(it)
            aaChartModel = AAChartModel()
                .chartType(AAChartType.Funnel)
                .title("Graph")
                .subtitle("subtitle")
                .backgroundColor("#4b2b7f")
                .dataLabelsEnabled(true)
                .series(
                    arrayOf()
                )
            //The chart view object calls the instance object of AAChartModel and draws the final graphic
            aaChartView.aa_drawChartWithChartModel(aaChartModel)
        })

        //Only refresh the chart series data
//        aaChartView.aa_onlyRefreshTheChartDataWithChartModelSeries(chartModelSeriesArray)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setData(list: List<Coin>){
        for (item in list){
            if (item.type == "gain"){
                chartGain.add(
//                        LocalDate.parse(item.date).dayOfMonth.toFloat()?: 0F,
                    AASeriesElement()
                        .color("#232323")
                        .data(arrayOf(item.amount?.toInt()!!))
                )
            }else if(item.type == "spend"){
                chartSpend.add(
                    AASeriesElement()
                        .color("#222333")
                        .data(arrayOf(item.amount?.toInt()!!))
                )
            }
        }
    }
}