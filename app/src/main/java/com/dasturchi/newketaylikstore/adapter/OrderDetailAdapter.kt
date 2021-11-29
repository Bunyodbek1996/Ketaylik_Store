package com.dasturchi.newketaylikstore.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dasturchi.newketaylikstore.databinding.ItemSinlgeOrderBinding
import com.dasturchi.newketaylikstore.model.ProductWithQuantity

class OrderDetailAdapter : RecyclerView.Adapter<OrderDetailAdapter.OrderHolder>() {
    private var orderList: List<ProductWithQuantity> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemSinlgeOrderBinding.inflate(inflater,parent,false)
        return OrderHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderHolder, position: Int) =
        holder.bind(orderList[position])

    override fun getItemCount(): Int = orderList.size

    fun setOrderList(orderList: List<ProductWithQuantity>) {
        this.orderList = orderList
        notifyDataSetChanged()
    }

    inner class OrderHolder(val binding: ItemSinlgeOrderBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ProductWithQuantity) {
            binding.item = item
            binding.executePendingBindings()
        }
    }

}