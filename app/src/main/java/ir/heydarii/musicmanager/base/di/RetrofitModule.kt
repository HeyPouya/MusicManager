package ir.heydarii.musicmanager.base.di

import dagger.Module
import dagger.Provides
import ir.heydarii.musicmanager.retrofit.RetrofitMainInterface
import ir.heydarii.musicmanager.utils.Constants
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * A module to provide all needed dependencies for retrofit
 */
@Module
class RetrofitModule {

    @Singleton
    @Provides
    fun provideRetrofit(converter: GsonConverterFactory, httpClient: OkHttpClient.Builder, @Named("baseURL") baseURL: String): Retrofit {
        return Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(converter)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient.build())
                .build()
    }

    @Singleton
    @Provides
    fun provideOkHttp(interceptor: HttpLoggingInterceptor): OkHttpClient.Builder {
        val httpClient = OkHttpClient().newBuilder()
        httpClient.connectTimeout(15, TimeUnit.SECONDS)
        httpClient.readTimeout(15, TimeUnit.SECONDS)
        httpClient.callTimeout(15, TimeUnit.SECONDS)
        httpClient.addInterceptor(interceptor)
        return httpClient
    }

    @Singleton
    @Provides
    fun provedHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    @Singleton
    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Singleton
    @Provides
    @Named("baseURL")
    fun provideBaseURL(): String {
        return Constants.BASE_URL
    }

    @Singleton
    @Provides
    fun getMainInterface(retrofit: Retrofit): RetrofitMainInterface {
        return retrofit.create(RetrofitMainInterface::class.java)
    }
}
