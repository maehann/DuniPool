package com.example.dunipool.featurse.market

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dunipool.apiManager.ApiManager
import com.example.dunipool.apiManager.dataClass.CoinsData
import com.example.dunipool.databinding.ActivityMarketBinding
import com.example.dunipool.featurse.CoinActivity

const val SEND_COIN_DATA_TO_SHOW="send coin data to coin activity"
class MarketActivity : AppCompatActivity(), MarketAdapter.action {
    private lateinit var binding:ActivityMarketBinding
    lateinit var news_data:ArrayList<Pair<String, String>>
    var api_manager=ApiManager()
    lateinit var coin_adapter:MarketAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMarketBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.moduleToolbar.mainToolbar.title="MARKET"


        loudUi()

        binding.moduleWatchlist.buttonMoreWatchlist.setOnClickListener {
            val intent=Intent(Intent.ACTION_VIEW, Uri.parse("https://www.livecoinwatch.com/"))
            startActivity(intent)
        }

        binding.swiperMarket.setOnRefreshListener {
            loudUi()
            Handler(Looper.getMainLooper()).postDelayed({
                binding.swiperMarket.isRefreshing=false
            },1500)

        }


    }

    override fun onResume() {
        super.onResume()
        loudUi()
    }

    private fun loudUi() {
        loudNews()

        loudCoins()
    }

    private fun loudCoins() {
        api_manager.get_coins(object :ApiManager.ApiCallback<List<CoinsData.Data>>{
            override fun onSuccess(data: List<CoinsData.Data>) {
                coin_adapter= MarketAdapter(ArrayList(data)!!,this@MarketActivity)
                binding.moduleWatchlist.marketRecycler.adapter=coin_adapter
                binding.moduleWatchlist.marketRecycler.layoutManager=LinearLayoutManager(this@MarketActivity,RecyclerView.VERTICAL,false)

            }

            override fun onError(message: String) {
                Toast.makeText(this@MarketActivity, "error network", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun loudNews() {
        api_manager.get_news(object :ApiManager.ApiCallback<ArrayList<Pair<String,String>>>{
            override fun onSuccess(data: ArrayList<Pair<String, String>>) {
                news_data=data
                refreshNews()

            }

            override fun onError(message: String) {

                Toast.makeText(this@MarketActivity, "error network", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun refreshNews() {
        val rand=(0 until news_data.size).random()
        binding.moduleNews.textNews.text=news_data[rand].first
        binding.moduleNews.textNews.setOnClickListener {
            refreshNews()
        }
        binding.moduleNews.imageNews.setOnClickListener {
            val intent=Intent(Intent.ACTION_VIEW, Uri.parse(news_data[rand].second))
            startActivity(intent)
        }

    }

    override fun onClick(data: CoinsData.Data) {

        val intent=Intent(this, CoinActivity::class.java)
        intent.putExtra(SEND_COIN_DATA_TO_SHOW,data)
        startActivity(intent)
    }
}