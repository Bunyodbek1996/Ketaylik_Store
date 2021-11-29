package com.dasturchi.newketaylikstore.ui.main.order

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.dasturchi.newketaylikstore.R
import com.dasturchi.newketaylikstore.adapter.OrderDetailAdapter
import com.dasturchi.newketaylikstore.databinding.ActivityOrderDetailBinding
import com.dasturchi.newketaylikstore.ui.main.MainListener
import com.dasturchi.newketaylikstore.ui.main.MainViewModel
import com.dasturchi.newketaylikstore.ui.main.MainViewModelFactory
import com.dasturchi.newketaylikstore.util.hide
import com.dasturchi.newketaylikstore.util.show
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class OrderDetailActivity : AppCompatActivity(), KodeinAware, MainListener {

    override val kodein by kodein()
    private val factory : MainViewModelFactory by instance()
    private lateinit var binding : ActivityOrderDetailBinding
    private val mAdapter = OrderDetailAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_detail)
        val viewModel = ViewModelProvider(this,factory).get(MainViewModel::class.java)
        binding.viewmodel = viewModel
        viewModel.listener = this


        binding.recyclerSingleOrder.adapter = mAdapter

        val id = intent.extras?.getString("id")
        viewModel.loadSingleOrder(id!!)

        viewModel.singleOrder.observe(this, {
            val list = it.pockets
            mAdapter.setOrderList(list!!)
            binding.tvAllPrice.text = it.total_price.toString()
            if (it.comment != null){binding.tvComment.text = it.comment!!}
            when (it.status) {
                in -5..0 -> {
                    binding.tvStatus.text = "Bekor qilingan"
                    binding.btnAccept.isEnabled = false
                    binding.btnReady.isEnabled = false
                    binding.btnCancel.isEnabled = false
                }
                1 -> {
                    binding.tvStatus.text = "Aktiv"
                    binding.btnAccept.isEnabled = true
                    binding.btnReady.isEnabled = false
                    binding.btnCancel.isEnabled = true
                }
                2 -> {
                    binding.tvStatus.text = "Qabul qilingan"
                    binding.btnAccept.isEnabled = false
                    binding.btnReady.isEnabled = true
                    binding.btnCancel.isEnabled = true
                }
                22 -> {
                    binding.tvStatus.text = "Tayyor qilingan"
                    binding.btnAccept.isEnabled = false
                    binding.btnReady.isEnabled = false
                    binding.btnCancel.isEnabled = false
                }
                in 3..6 -> {
                    binding.tvStatus.text = "Yetkazilgan"
                    binding.btnAccept.isEnabled = false
                    binding.btnReady.isEnabled = false
                    binding.btnCancel.isEnabled = false
                }
            }
        })

        viewModel.isReceived.observe(this,{
            if (it){
                with(binding){
                    btnCancel.isEnabled = true
                    btnReady.isEnabled = true
                    btnAccept.isEnabled = true
                    tvStatus.text = "Qabul qilingan"
                }
            }
        })
        viewModel.isReady.observe(this,{
            if (it){
                with(binding){
                    btnCancel.isEnabled = false
                    btnReady.isEnabled = false
                    btnAccept.isEnabled = false
                    tvStatus.text = "Tayyor qilingan"
                }
            }
        })
        viewModel.isCancel.observe(this,{
            if (it){
                with(binding){
                    btnCancel.isEnabled = false
                    btnReady.isEnabled = false
                    btnAccept.isEnabled = false
                    tvStatus.text = "Bekor qilingan"
                }
            }
        })

        binding.btnCancel.setOnClickListener {
            viewModel.cancelOrder(id)
        }
        binding.btnReady.setOnClickListener {
            viewModel.readyOrder(id)
        }
        binding.btnAccept.setOnClickListener {
            viewModel.receiveOrder(id)
        }
    }

    override fun onStarted() {
        binding.progressCircular.show()
    }

    override fun onSuccess() {
        binding.progressCircular.hide()
    }

    override fun onFailure(message: String) {
        binding.progressCircular.hide()
    }
}