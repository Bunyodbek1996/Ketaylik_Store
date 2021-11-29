package com.dasturchi.newketaylikstore.ui.main.report

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dasturchi.newketaylikstore.R
import com.dasturchi.newketaylikstore.adapter.ReportAdapter
import com.dasturchi.newketaylikstore.databinding.FragmentReportBinding
import com.dasturchi.newketaylikstore.ui.main.MainListener
import com.dasturchi.newketaylikstore.util.PreferenceProvider
import com.dasturchi.newketaylikstore.util.hide
import com.dasturchi.newketaylikstore.util.show
import com.dasturchi.newketaylikstore.util.toast
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import java.util.*

class ReportFragment : Fragment(), KodeinAware, MainListener {
    override val kodein by kodein()
    private val factory: ReportViewModelFactory by instance()
    private val pref: PreferenceProvider by instance()

    private lateinit var binding: FragmentReportBinding
    private lateinit var viewModel: ReportViewModel
    private var mAdapter = ReportAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_report, container, false)
        viewModel = ViewModelProvider(this, factory).get(ReportViewModel::class.java)
        viewModel.mainListener = this
        binding.viewmodel = viewModel
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initDatePicker()

        binding.recyclerReport.adapter = mAdapter

        viewModel.orders.observe(viewLifecycleOwner, { it1 ->
            val list = it1.filter { it.status == 5 }
            var summa = 0
            for (order in list) {
                summa += order.total_price!!
            }
            binding.tvAllTrade.text = "Umumiy savdo: $summa"
            mAdapter.setOrderList(list)
        })

        viewModel.loadAllOrder()
    }

    private fun initDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)


        binding.tvDateFrom.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                this.requireContext(),
                DatePickerDialog.OnDateSetListener { _, yearl, monthOfYear, dayOfMonth ->
                    var date = yearl
                    val mon = if (monthOfYear + 1 < 10) "0${monthOfYear + 1}" else {
                        "${monthOfYear + 1}"
                    }
                    val da = if (dayOfMonth < 10) "0$dayOfMonth" else dayOfMonth
                    binding.tvDateFrom.text = "$date-$mon-$da"
                },
                year,
                month,
                day
            )
            datePickerDialog.show()
        }
        binding.tvDateTo.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                this.requireContext(),
                DatePickerDialog.OnDateSetListener { _, yearl, monthOfYear, dayOfMonth ->
                    var date = yearl
                    val mon = if (monthOfYear + 1 < 10) "0${monthOfYear + 1}" else {
                        "${monthOfYear + 1}"
                    }
                    val da = if (dayOfMonth < 10) "0$dayOfMonth" else dayOfMonth
                    binding.tvDateTo.text = "$date-$mon-$da"
                    val from = binding.tvDateFrom.text.toString()
                    val to = "${binding.tvDateTo.text.toString()} 23:59"
                    viewModel.loadBetween(from, to)
                },
                year,
                month,
                day
            )
            datePickerDialog.show()
        }

        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == R.id.radioAllTime) {
                binding.consDate.visibility = View.GONE
                viewModel.loadAllOrder()
            } else {
                binding.consDate.visibility = View.VISIBLE
            }
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
        this.requireContext().toast(message)

    }

}