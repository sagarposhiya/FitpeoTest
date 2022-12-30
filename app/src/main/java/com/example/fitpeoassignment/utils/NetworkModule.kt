package com.example.fitpeoassignment.utils

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.fitpeoassignment.api.DataAPI
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.Response

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Module
class NetworkModule @Inject constructor(var application: Application) {

    @Provides
    fun application(): Application {
        return application
    }


    val isConnected : Boolean    get(){
        val connectivityManager = application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork
            val capabilities = connectivityManager.getNetworkCapabilities(network)
            capabilities != null && capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
        } else {
            connectivityManager.activeNetworkInfo?.type == ConnectivityManager.TYPE_WIFI
        }
    }


    @Provides
    fun provideUserDataApi(retrofit: Retrofit): DataAPI =
        retrofit.create(DataAPI::class.java)

    @Provides
    @Singleton
    fun provideRetrofitInterface(): Retrofit {
        val cacheSize = (5 * 1024 * 1024).toLong()
        val myCache = Cache(application.cacheDir, cacheSize)


        // for caching

        val okHttpClient = OkHttpClient.Builder()
            .cache(myCache)
            // add off line interceptor
            .addInterceptor { chain ->
                var request = chain.request()
                request = if (isConnected)
                    request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build()
                else
                    request.newBuilder().header(
                        "Cache-Control",
                        "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7
                    ).build()
                chain.proceed(request)
            }
            // add on line interceptor
            .addNetworkInterceptor {

                val response: Response = it.proceed(it.request())
                val maxAge =
                    60 // read from cache for 60 seconds even if there is internet connection

                response.newBuilder()
                    .header("Cache-Control", "public, max-age=$maxAge")
                    .removeHeader("Pragma")
                    .build()
            }
            .build()


        // create retrofit
        return Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

}