package com.yesayasoftware.learning

import android.app.Application
import com.facebook.stetho.Stetho
import com.jakewharton.threetenabp.AndroidThreeTen
import com.yesayasoftware.learning.data.db.YesayaSoftwareDatabase
import com.yesayasoftware.learning.data.network.RetrofitBuilder
import com.yesayasoftware.learning.data.network.connectivity.ConnectivityInterceptor
import com.yesayasoftware.learning.data.network.connectivity.ConnectivityInterceptorImpl
import com.yesayasoftware.learning.data.network.datasource.PostNetworkDataSource
import com.yesayasoftware.learning.data.network.datasource.PostNetworkDataSourceImpl
import com.yesayasoftware.learning.data.repository.YesayaSoftwareRepository
import com.yesayasoftware.learning.data.repository.YesayaSoftwareRepositoryImpl
import com.yesayasoftware.learning.ui.categories.CategoryActivity
import com.yesayasoftware.learning.ui.posts.list.PostListViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class YesayaSoftwareApplication : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@YesayaSoftwareApplication))

        bind() from singleton { YesayaSoftwareDatabase(instance()) }

        bind() from singleton { instance<YesayaSoftwareDatabase>().postDao() }

        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }

        bind() from singleton { RetrofitBuilder(instance()) }

        bind<PostNetworkDataSource>() with singleton { PostNetworkDataSourceImpl(instance(), instance()) }

        bind<YesayaSoftwareRepository>() with singleton { YesayaSoftwareRepositoryImpl(instance(), instance()) }

        bind() from provider { PostListViewModelFactory(instance()) }
    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)

        initStetho()
    }

    private fun initStetho() {
        if (BuildConfig.DEBUG)
            Stetho.initializeWithDefaults(this)
    }
}