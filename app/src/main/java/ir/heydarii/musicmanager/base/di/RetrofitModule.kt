package ir.heydarii.musicmanager.base.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.heydarii.musicmanager.retrofit.RetrofitMainInterface
import ir.heydarii.musicmanager.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Provides [Retrofit] interfaces
 */
@InstallIn(SingletonComponent::class)
@Module
class RetrofitModule {

    @Singleton
    @Provides
    fun provideRetrofit(moshi: MoshiConverterFactory, httpClient: OkHttpClient.Builder): Retrofit =
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(moshi)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(httpClient.build())
            .build()

    @Singleton
    @Provides
    fun provideOkHttp(interceptor: HttpLoggingInterceptor) = OkHttpClient().newBuilder().apply {
        connectTimeout(15, TimeUnit.SECONDS)
        readTimeout(15, TimeUnit.SECONDS)
        callTimeout(15, TimeUnit.SECONDS)
        addInterceptor(interceptor)
    }

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Singleton
    @Provides
    fun provideMoshiConverterFactory(): MoshiConverterFactory = MoshiConverterFactory.create()

    @Singleton
    @Provides
    fun getMainInterface(retrofit: Retrofit): RetrofitMainInterface =
        retrofit.create(RetrofitMainInterface::class.java)
}
