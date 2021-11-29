package com.dasturchi.newketaylikstore.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dasturchi.newketaylikstore.databinding.ItemProductBinding
import com.dasturchi.newketaylikstore.model.AllProduct
import com.dasturchi.newketaylikstore.network.BASE_URL
import com.dasturchi.newketaylikstore.network.repository.MainRepository
import com.dasturchi.newketaylikstore.util.ApiException
import com.dasturchi.newketaylikstore.util.Coroutines
import com.dasturchi.newketaylikstore.util.PreferenceProvider

class ProductAdapter(
) : RecyclerView.Adapter<ProductAdapter.OrderHolder>() {
    private var orderList: List<AllProduct> = ArrayList()
    private var repository: MainRepository? = null
    private var pref: PreferenceProvider? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemProductBinding.inflate(inflater,parent,false)
        return OrderHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderHolder, position: Int) =
        holder.bind(orderList[position])

    override fun getItemCount(): Int = orderList.size

    fun setPro(orderList: List<AllProduct>, repository: MainRepository, pref: PreferenceProvider){
        this.orderList = orderList
        this.repository = repository
        this.pref = pref
        notifyDataSetChanged()
    }
    inner class OrderHolder(val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: AllProduct) {
            binding.item = item
            binding.executePendingBindings()

            binding.swProductStatus.isChecked = item.status == 1
            binding.swProductStatus.setOnClickListener {
                Coroutines.main {
                    try {
                        val response = repository?.setProductStatus(pref?.getToken()!!,item.id.toString())
                        if(response?.success!!){
                            val st = response.data?.status
                            binding.swProductStatus.isChecked = st == 1
                        }
                    }catch (e: ApiException){

                    }
                }
            }
            Glide.with(binding.root).load(
                "$BASE_URL/storage/product_images/${item.image}")
                .into(binding.imgProductPhoto)
        }
    }

}