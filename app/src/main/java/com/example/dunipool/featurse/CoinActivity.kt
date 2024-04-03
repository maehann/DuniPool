package com.example.dunipool.featurse

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.example.dunipool.apiManager.dataClass.CoinAboutData
import com.example.dunipool.apiManager.dataClass.CoinAboutItem
import com.example.dunipool.apiManager.dataClass.CoinsData
import com.example.dunipool.databinding.ActivityCoinBinding
import com.example.dunipool.featurse.market.SEND_COIN_DATA_TO_SHOW
import com.google.gson.Gson

@Suppress("DEPRECATION")
class CoinActivity : AppCompatActivity() {
    lateinit var binding: ActivityCoinBinding
    private lateinit var get_data: CoinsData.Data
    private lateinit var coin_about_map:MutableMap<String, CoinAboutItem>
    @SuppressLint("SuspiciousIndentation")
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        get_data = intent.getParcelableExtra(SEND_COIN_DATA_TO_SHOW)!!

        binding.moduleToolbar.mainToolbar.title=get_data.coinInfo?.fullName.toString()

            loudUi()

    }

    private fun loudUi() {
        loud_statics()
        loud_about()
    }

    @SuppressLint("SetTextI18n")
    private fun loud_about() {
        initial_set_data()
        val key= get_data.coinInfo?.name
        val data_item: CoinAboutItem =coin_about_map[key]!!


        binding.moduleAbout.githubAd.text=data_item.coinGithub
        binding.moduleAbout.redditAd.text=data_item.coinReddit
        binding.moduleAbout.twitterAd.text=data_item.coinTwitter
        binding.moduleAbout.webAd.text=data_item.coinWebsite

        binding.moduleAbout.twitterAd.text= "@" + data_item.coinTwitter

        binding.moduleAbout.txtAboutCoin.text=data_item.coinDesc


    }

    private fun initial_set_data() {
        val fileInString = applicationContext.assets
            .open("currencyinfo.json")
            .bufferedReader()
            .use { it.readText() }

        coin_about_map= mutableMapOf()

        val gson = Gson()
        val dataclass_about = gson.fromJson(fileInString, CoinAboutData::class.java)

        dataclass_about.forEach{
            coin_about_map[it.currencyName]= CoinAboutItem(
                coinWebsite = it.info.web,
                coinGithub = it.info.github,
                coinDesc = it.info.desc,
                coinReddit = it.info.reddit,
                coinTwitter = it.info.twt
            )
        }
    }


    @SuppressLint("SetTextI18n")
    private fun loud_statics() {

        binding.moduleStatistic.tvOpenAmount.text = "$" + ((get_data.rAW?.uSD?.oPEN24HOUR)?:0.00).toString()
        binding.moduleStatistic.tvTodaysHighAmount.text = "$" + ((get_data.rAW?.uSD?.hIGH24HOUR )?:0.00).toString()
        binding.moduleStatistic.tvTodaysLowAmount.text = "$" + ((get_data.rAW?.uSD?.lOW24HOUR )?:0.00).toString()
        binding.moduleStatistic.tvChangeTodaysAmount.text = "$" + ((get_data.rAW?.uSD?.cHANGE24HOUR)?:0.00 ).toString()
        binding.moduleStatistic.tvVolumeQuantity.text = "$" + ((get_data.dISPLAY?.uSD?.vOLUME24HOUR)?:0.00).toString()
        binding.moduleStatistic.tvTotalVolumeAmount.text = "$" + ((get_data.dISPLAY?.uSD?.tOTALVOLUME24H )?:0.00).toString()
        binding.moduleStatistic.tvAvgMarketCapAmount.text = "$" + ((get_data.dISPLAY?.uSD?.mKTCAPPENALTY )?:0.00).toString()
        binding.moduleStatistic.tvSupplyNumber.text = "$" + ((get_data.dISPLAY?.uSD?.sUPPLY )?:0.00).toString()
    }
}