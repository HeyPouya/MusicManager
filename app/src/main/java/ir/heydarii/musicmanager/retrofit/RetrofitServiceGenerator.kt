package ir.heydarii.musicmanager.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class RetrofitServiceGenerator @Inject constructor(
        private val converter: GsonConverterFactory,
        private val httpClient: OkHttpClient.Builder,
        private val baseURL: String
) {

    /**
     * Creates a Retrofit Client object
     */
    fun getClient(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(converter)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient.build())
                .build()
    }
}