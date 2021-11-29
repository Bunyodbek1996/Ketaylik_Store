package com.dasturchi.newketaylikstore.ui.main

import android.content.DialogInterface
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.dasturchi.newketaylikstore.R
import com.dasturchi.newketaylikstore.databinding.ActivityHomeBinding
import com.dasturchi.newketaylikstore.util.PreferenceProvider
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.messaging.FirebaseMessaging
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class HomeActivity : AppCompatActivity(),KodeinAware{
    override val kodein by kodein()
    private val factory : MainViewModelFactory by instance()
    private val pref : PreferenceProvider by instance()

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_home)
        setSupportActionBar(binding.toolbar)
        viewModel = ViewModelProvider(this,factory).get(MainViewModel::class.java)



        initBottomNavigationMenu()
        initStoreStatus()
        if (pref.getFCM().isNullOrBlank()){
            initFCM()
        }
    }

    private fun initStoreStatus() {
        viewModel.status.observe(this, Observer {
            binding.tvStoreStatus.text = if (it == 0){
                getString(R.string.yopiq)
            }else{
                getString(R.string.ochiq)
            }
        })
        viewModel.name.observe(this, Observer {
            binding.tvTitle.text = it
        })
        viewModel.getStoreStatus()
    }

    private fun initFCM() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@OnCompleteListener
            }
            val token = task.result
            viewModel.senFCM(token)
        })
    }

    private fun initBottomNavigationMenu() {
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.log_out -> {
                val alertDialog = AlertDialog.Builder(applicationContext)
                alertDialog.setTitle("Profildan chiqasizmi")
                alertDialog.setPositiveButton("Ha", DialogInterface.OnClickListener { dialog, which ->
                    PreferenceProvider(applicationContext).saveToken("")
                    finish()
                })
                alertDialog.create()
                alertDialog.show()
            }
            R.id.close_or_open -> {
                viewModel.setStoreStatus()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}