package com.dasturchi.newketaylikstore

import android.app.Application
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.dasturchi.newketaylikstore.network.MyApi
import com.dasturchi.newketaylikstore.network.NetworkInterceptor
import com.dasturchi.newketaylikstore.network.repository.AuthRepository
import com.dasturchi.newketaylikstore.network.repository.MainRepository
import com.dasturchi.newketaylikstore.ui.auth.AuthViewModelFactory
import com.dasturchi.newketaylikstore.ui.main.MainViewModelFactory
import com.dasturchi.newketaylikstore.ui.main.order.OrderViewModelFactory
import com.dasturchi.newketaylikstore.ui.main.product.ProductViewModelFactory
import com.dasturchi.newketaylikstore.ui.main.report.ReportViewModelFactory
import com.dasturchi.newketaylikstore.util.PreferenceProvider
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class KStoreApplication() : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@KStoreApplication))


        bind() from singleton {
            ChuckerInterceptor.Builder(instance())
                .collector(ChuckerCollector(instance()))
                .maxContentLength(250000L)
                .redactHeaders(emptySet())
                .alwaysReadResponseBody(false)
                .build()
        }

        bind() from singleton { NetworkInterceptor(instance()) }
        bind() from singleton { MyApi(instance(),instance()) }
        bind() from singleton { PreferenceProvider(instance()) }
        bind() from singleton { AuthRepository(instance()) }
        bind() from singleton { MainRepository(instance()) }
        bind() from provider { AuthViewModelFactory(instance(),instance()) }
        bind() from provider { MainViewModelFactory(instance(),instance()) }
        bind() from provider { OrderViewModelFactory(instance(),instance()) }
        bind() from provider { ProductViewModelFactory(instance(),instance()) }
        bind() from provider { ReportViewModelFactory(instance(),instance()) }

    }
}