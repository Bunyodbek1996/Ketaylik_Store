package com.dasturchi.newketaylikstore.ui.auth

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.dasturchi.newketaylikstore.R
import com.dasturchi.newketaylikstore.databinding.ActivityConfirmBinding
import com.dasturchi.newketaylikstore.ui.main.HomeActivity
import com.dasturchi.newketaylikstore.util.hide
import com.dasturchi.newketaylikstore.util.show
import com.dasturchi.newketaylikstore.util.toast
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class ConfirmActivity : AppCompatActivity(), AuthListener,KodeinAware {
    override val kodein by kodein()
    private val factory : AuthViewModelFactory by instance()
    private lateinit var binding: ActivityConfirmBinding
    private lateinit var timer : CountDownTimer



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_confirm)
        val viewModel = ViewModelProvider(this, factory).get(AuthViewModel::class.java)
        binding.viewmodel = viewModel
        viewModel.authListener = this

        initCountDown(120)

        binding.edtPhone.addTextChangedListener {
            val code = it.toString()
            if (code.length == 4) viewModel.onLoginClick(applicationContext)
        }
    }

    override fun onStarted() {
        binding.progressCircular.show()
    }

    override fun onSuccess() {
        timer.cancel()
        startActivity(Intent(applicationContext,HomeActivity::class.java))
        finishAffinity()
    }

    override fun onFailure(message: String) {
        binding.progressCircular.hide()
        toast(message)
    }


    private fun initCountDown(time: Int) {
        timer = object : CountDownTimer(time * 1000.toLong(), 1000) {
            override fun onTick(tick: Long) {
                var min = ((tick / 1000) / 60).toString();
                var sec = ((tick / 1000) % 60).toString();
                if (min.length == 1) min = "0" + min
                if (sec.length == 1) sec = "0" + sec
                var text = "$min:$sec"
                binding.tvCountDown.text = text
            }

            override fun onFinish() {
                finish()
            }
        }
        timer.start()
    }

    override fun onBackPressed() {}

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
    }

}