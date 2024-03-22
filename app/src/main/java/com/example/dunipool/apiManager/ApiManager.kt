package com.example.dunipool.apiManager

import ir.dunijet.dunipool.apiManager.model.CoinsData
import ir.dunijet.dunipool.apiManager.model.NewsData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val API_KEY="authorization: Apikey 4c43f68023c6f57b50038937b779b750e561ed69a73555baa7e90dc604c8384c"
const val BAsE_URL="https://min-api.cryptocompare.com/data/"
const val BASE_URL_IMAGE = "https://www.cryptocompare.com"

class ApiManager {
    private val Api_service:APIInterface
    init {

        val retrofit=Retrofit
            .Builder()
            .baseUrl(BAsE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        Api_service=retrofit.create(APIInterface::class.java)
    }

    fun getNews(apiCallback: ApiCallback<ArrayList<Pair<String, String>>>) {

       Api_service.getTopNews().enqueue(object :Callback<NewsData>{
           override fun onResponse(call: Call<NewsData>, response: Response<NewsData>) {
               val data = response.body()!!

               val dataToSend: ArrayList<Pair<String, String>> = arrayListOf()
               data.data.forEach {
                   dataToSend.add(Pair(it.title, it.url))
               }

               apiCallback.onSuccess(dataToSend)           }

           override fun onFailure(call: Call<NewsData>, t: Throwable) {
               apiCallback.onError( t.message!! )
           }

       })

    }

    fun getCoinsList( apiCallback: ApiCallback<List<CoinsData.Data>> ) {

        Api_service.getTopCoin().enqueue( object :Callback<CoinsData> {
            override fun onResponse(call: Call<CoinsData>, response: Response<CoinsData>) {
                val data = response.body()!!
                apiCallback.onSuccess( data.data )
            }

            override fun onFailure(call: Call<CoinsData>, t: Throwable) {
                apiCallback.onError(t.message!!)
            }

        } )

    }

    interface ApiCallback<T> {

        fun onSuccess(data: T)
        fun onError(errorMessage: String)

    }


}