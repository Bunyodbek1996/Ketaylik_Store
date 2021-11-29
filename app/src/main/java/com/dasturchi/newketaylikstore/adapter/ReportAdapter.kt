package com.dasturchi.newketaylikstore.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dasturchi.newketaylikstore.databinding.ItemReportBinding
import com.dasturchi.newketaylikstore.model.Order
import com.dasturchi.newketaylikstore.ui.main.order.OrderDetailActivity

class ReportAdapter : RecyclerView.Adapter<ReportAdapter.OrderHolder>() {
    private var orderList: List<Order> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemReportBinding.inflate(inflater,parent,false)
        return OrderHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderHolder, position: Int) =
        holder.bind(orderList[position])

    override fun getItemCount(): Int = orderList.size

    fun setOrderList(orderList: List<Order>) {
        this.orderList = orderList
        notifyDataSetChanged()
    }

    inner class OrderHolder(val binding: ItemReportBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Order) {
            binding.item = item
            binding.executePendingBindings()

            binding.tvOrderTime.text = item.created_at?.substring(0,16)

            binding.consOrder.setOnClickListener {
                val intent = Intent(binding.root.context,OrderDetailActivity::class.java)
                intent.putExtra("id",item.id.toString())
                binding.root.context.startActivity(intent)
            }
            binding.tvCustomerPhone.setOnClickListener {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:${item.phone}")
                binding.root.context.startActivity(intent)
            }
            when(item.status){
                1 -> binding.tvOrderStatus.text = "Aktiv"
                2 -> binding.tvOrderStatus.text = "Qabul qilingan"
                22 -> binding.tvOrderStatus.text = "Tayyor"
                in 3..7 -> binding.tvOrderStatus.text = "Jo'natilgan"
                else -> {
                    binding.tvOrderStatus.text = "Bekor qilingan"
                }
            }
        }
    }

}