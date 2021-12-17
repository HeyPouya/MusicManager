package ir.heydarii.musicmanager.base.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.heydarii.musicmanager.repository.network.RetrofitAlbumsInterface
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Provides [Retrofit] interfaces
 */

private const val BASE_URL = "https://ws.audioscrobbler.com/2.0/"

@InstallIn(SingletonComponent::class)
@Module
class RetrofitModule {

    @Singleton
    @Provides
    fun provideRetrofit(moshi: MoshiConverterFactory, httpClient: OkHttpClient.Builder): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(moshi)
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
    fun getMainInterface(retrofit: Retrofit): RetrofitAlbumsInterface =
        retrofit.create(RetrofitAlbumsInterface::class.java)
}
