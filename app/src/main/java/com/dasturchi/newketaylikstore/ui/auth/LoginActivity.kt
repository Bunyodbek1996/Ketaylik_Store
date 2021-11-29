package com.dasturchi.newketaylikstore.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.dasturchi.newketaylikstore.R
import com.dasturchi.newketaylikstore.databinding.ActivityLoginBinding
import com.dasturchi.newketaylikstore.ui.main.HomeActivity
import com.dasturchi.newketaylikstore.util.PreferenceProvider
import com.dasturchi.newketaylikstore.util.hide
import com.dasturchi.newketaylikstore.util.show
import com.dasturchi.newketaylikstore.util.toast
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class LoginActivity : AppCompatActivity() ,AuthListener ,KodeinAware {

    override val kodein by kodein()
    private val factory : AuthViewModelFactory by instance()
    private lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_login)
        val viewModel = ViewModelProvider(this,factory).get(AuthViewModel::class.java)
        binding.viewmodel = viewModel
        viewModel.authListener = this


        if (PreferenceProvider(applicationContext).getToken().isNullOrEmpty()){}
        else{
            startActivity(Intent(applicationContext,HomeActivity::class.java))
            finish()
        }
    }

    override fun onStarted() {
        binding.progressCircular.show()
    }

    override fun onSuccess() {
        binding.progressCircular.hide()
        startActivity(Intent(applicationContext,ConfirmActivity::class.java))
    }

    override fun onFailure(message: String) {
        binding.progressCircular.hide()
        toast(message)
    }

}