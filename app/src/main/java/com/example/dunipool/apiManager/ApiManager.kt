package com.example.dunipool.apiManager

import com.example.dunipool.apiManager.dataClass.CoinsData
import com.example.dunipool.apiManager.dataClass.NewsData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val API_KEY="authorization: Apikey 4c43f68023c6f57b50038937b779b750e561ed69a73555baa7e90dc604c8384c"
const val BASE_URL="https://min-api.cryptocompare.com/data/"
const val BASE_URL_IMAGE = "https://www.cryptocompare.com"

class ApiManager {
    val api_servis:APIInterface
   init{
       val retrofit=Retrofit
           .Builder()
           .baseUrl(BASE_URL)
           .addConverterFactory(GsonConverterFactory.create())
           .build()

       api_servis=retrofit.create(APIInterface::class.java)
   }

    fun get_news(apiCallback:ApiCallback<ArrayList<Pair<String,String>>>){
         api_servis.getTopNews().enqueue(object : Callback<NewsData>{
             override fun onResponse(call: Call<NewsData>, response: Response<NewsData>) {
                 val news=response.body()!!
                 val news_pair:ArrayList<Pair<String,String>> = arrayListOf()
                 news.data.forEach {
                     news_pair.add(Pair(it.title,it.url))

                     apiCallback.onSuccess(news_pair)
                 }
             }

             override fun onFailure(call: Call<NewsData>, t: Throwable) {

                 apiCallback.onError(t.message!!)
             }

         })




    }

    fun get_coins(apiCallback:ApiCallback<List<CoinsData.Data>>){
        api_servis.getTopCoin().enqueue(object :Callback<CoinsData>{
            override fun onResponse(call: Call<CoinsData>, response: Response<CoinsData>) {
                val data=response.body()!!
                apiCallback.onSuccess(data.data!!)
            }

            override fun onFailure(call: Call<CoinsData>, t: Throwable) {
                apiCallback.onError(t.message!!)
            }

        })
    }

    interface ApiCallback<T>{
        fun onSuccess(data:T)
        fun onError(message:String)
    }

}