package com.example.cashfree_poc.ui.api.di

import android.content.Context
import com.example.cashfree_poc.ui.api.domain.ApiConstant.BASE_URL
import com.example.cashfree_poc.ui.api.domain.NetworkUtil
import com.example.cashfree_poc.ui.api.domain.PaymentApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PaymentApiModule {

    @Provides
    @Singleton
    fun provideApi(builder: Retrofit.Builder): PaymentApi {
        return builder.build().create(PaymentApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
    }

    @Provides
    @Singleton
    fun provideNetworkConnectivity(@ApplicationContext context: Context): NetworkUtil {
        return NetworkUtil(context)
    }
}