package com.dasturchi.newketaylikstore.ui.main.order

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dasturchi.newketaylikstore.R
import com.dasturchi.newketaylikstore.adapter.OrderAdapter
import com.dasturchi.newketaylikstore.databinding.FragmentOrderBinding
import com.dasturchi.newketaylikstore.ui.auth.LoginActivity
import com.dasturchi.newketaylikstore.ui.main.MainListener
import com.dasturchi.newketaylikstore.util.PreferenceProvider
import com.dasturchi.newketaylikstore.util.hide
import com.dasturchi.newketaylikstore.util.show
import com.dasturchi.newketaylikstore.util.toast
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class OrderFragment : Fragment(), KodeinAware, MainListener {
    override val kodein by kodein()
    private val factory: OrderViewModelFactory by instance()
    private val pref: PreferenceProvider by instance()

    private lateinit var binding: FragmentOrderBinding
    private lateinit var orderViewModel: OrderViewModel
    private var mAdapter = OrderAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_order, container, false)
        orderViewModel = ViewModelProvider(this, factory).get(OrderViewModel::class.java)
        orderViewModel.mainListener = this
        binding.viewmodel = orderViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerNewOrder.adapter = mAdapter

        orderViewModel.orders.observe(viewLifecycleOwner, { it1 ->
            val recyclerViewState = binding.recyclerNewOrder.layoutManager?.onSaveInstanceState();
            it1.filter { it.status!! > 0 }
            mAdapter.setOrderList(it1)
            binding.recyclerNewOrder.layoutManager?.onRestoreInstanceState(recyclerViewState);
        })

        val handler = Handler(Looper.getMainLooper())
        val r: Runnable = object : Runnable {
            override fun run() {
                orderViewModel.loadNewOrder()
                handler.postDelayed(this, 5000)
            }
        }
        handler.postDelayed(r, 5000)

    }

    override fun onStarted() {
        if (binding.recyclerNewOrder.adapter?.itemCount == 0) {
            binding.progressCircular.show()
        }
    }

    override fun onSuccess() {
        binding.progressCircular.hide()
    }

    override fun onFailure(message: String) {
        binding.progressCircular.hide()
        if (message == "9") {
            startActivity(Intent(this.requireContext(), LoginActivity::class.java))
            pref.clear()
            this.requireActivity().finishAffinity()
        } else {
            this.requireContext().toast(message)
        }
    }
}