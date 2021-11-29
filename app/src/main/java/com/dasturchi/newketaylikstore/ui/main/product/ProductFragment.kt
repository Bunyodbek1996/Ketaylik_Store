package com.dasturchi.newketaylikstore.ui.main.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dasturchi.newketaylikstore.R
import com.dasturchi.newketaylikstore.adapter.ProductAdapter
import com.dasturchi.newketaylikstore.databinding.FragmentProductBinding
import com.dasturchi.newketaylikstore.network.repository.MainRepository
import com.dasturchi.newketaylikstore.ui.main.MainListener
import com.dasturchi.newketaylikstore.util.PreferenceProvider
import com.dasturchi.newketaylikstore.util.hide
import com.dasturchi.newketaylikstore.util.show
import com.dasturchi.newketaylikstore.util.toast
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class ProductFragment : Fragment(), KodeinAware, MainListener {
    override val kodein by kodein()
    private val factory: ProductViewModelFactory by instance()
    private val repository: MainRepository by instance()
    private val pref: PreferenceProvider by instance()
    private lateinit var productViewModel: ProductViewModel
    private lateinit var binding: FragmentProductBinding
    private val mAdapter = ProductAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_product, container, false)
        productViewModel = ViewModelProvider(this, factory).get(ProductViewModel::class.java)
        binding.viewmodel = productViewModel
        productViewModel.mainListener = this
        binding.recyclerProduct.adapter = mAdapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productViewModel.products.observe(viewLifecycleOwner, {
            mAdapter.setPro(it,repository,pref)
        })

        productViewModel.loadProducts()
    }

    override fun onStarted() {
        binding.progressCircular.show()
    }

    override fun onSuccess() {
        binding.progressCircular.hide()
    }

    override fun onFailure(message: String) {
        binding.progressCircular.hide()
        this.requireContext().toast(message)
    }
}