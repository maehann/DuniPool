package com.example.dunipool.featurse.market

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dunipool.R
import com.example.dunipool.apiManager.BASE_URL_IMAGE
import com.example.dunipool.apiManager.dataClass.CoinsData
import com.example.dunipool.databinding.MarketItemBinding


class MarketAdapter(private val data:ArrayList<CoinsData.Data>, private val act: action): RecyclerView.Adapter<MarketAdapter.ViewHolder>() {

    lateinit var binding:MarketItemBinding

    inner class ViewHolder( item: View , private val context:Context):RecyclerView.ViewHolder(item){

        @SuppressLint("SetTextI18n")
        fun setView(data:CoinsData.Data){
            binding.coinName.text= data.coinInfo!!.fullName

            val price = data.rAW?.uSD?.pRICE?.toString() ?: "N/A"
            if (price.length>(price.indexOf(".")+3)){
                binding.coinPrice.text = "$" + price.substring(0..(price.indexOf(".")+3))
            } else{

                binding.coinPrice.text = "$" + price
            }


            val marketCap = data.rAW?.uSD?.mKTCAP?.div(1000000000) ?: 0.0
            val index = marketCap.toString().indexOf(".") + 3
            if (marketCap.toString().length>marketCap.toString().indexOf(".") + 3){
                binding.marketCap.text = "$" + marketCap.toString().substring(0..index) + "B"
            }else{

                binding.marketCap.text = "$" + marketCap.toString()+ "B"
            }

            val taghir = data.rAW?.uSD?.cHANGE24HOUR ?: 0.0
            val taghirStr = taghir.toString()
            if (taghir > 0) {
                binding.taghir.text = "+" + taghirStr.substring(0..(taghirStr.indexOf(".") + 3)) + " %"
                binding.taghir.setTextColor(ContextCompat.getColor(binding.root.context, R.color.colorGain))
            } else if (taghir < 0) {
                binding.taghir.text = taghirStr.substring(0..(taghirStr.indexOf(".") + 3)) + " %"
                binding.taghir.setTextColor(ContextCompat.getColor(binding.root.context, R.color.colorLoss))
            } else if (taghir == 0.toDouble()) {
                binding.taghir.text = "0.00 %"
            }

            Glide
                .with(context)
                .load(BASE_URL_IMAGE+data.coinInfo.imageUrl)
                .into(binding.marketItemImg)


            itemView.setOnClickListener {
                act.onClick(data!!)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        binding=MarketItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return ViewHolder(binding.root,parent.context)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setView(data[position])
    }

    interface action{
        fun onClick(data:CoinsData.Data)
    }
}


