package com.xing.configuration

import com.fasterxml.jackson.databind.ObjectMapper
import com.xing.services.externals.ArtistInfoExternalService
import com.xing.services.externals.MovieInfoExternalService
import com.xing.services.externals.MovieSearchExternalService
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import retrofit2.Retrofit
import retrofit2.adapter.java8.Java8CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit


/**
 * RetrofitConfiguration
 * <p>
 *     Retorfit2 provides a simple way to execute an external call, basically implementing interfaces.
 *     Also, Retrofit2 provides a diverse way to manage asyncs response and request
 *
 *     Why do I use retrofit2 instead of RestTemplate or others?
 *
 *     Retrofit builds upon OkHttp and that means that I can customize them all,
 *     request and response, the simpleness of the implementation made me try this library in a lot of projects.
 *
 *     You can see more documentation at https://square.github.io/retrofit
 * <p>
 *
 */
@Configuration
class RetrofitConfiguration(
    @Value("\${services.paths.movieinfo}") val movieInfoUrl: String,
    @Value("\${services.paths.moviesearch}") val movieSearchUrl: String,
    @Value("\${services.paths.artistinfo}") val artistInfoUrl: String,
    @Value("\${services.paths.microservice-connection-timeout}") val connectionTimeout: Long,
    @Value("\${services.paths.microservice-timeout}") val timeout: Long,
) {

    @Bean
    fun okHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .connectionPool(ConnectionPool(20, 5, TimeUnit.MINUTES))
        .connectTimeout(connectionTimeout, TimeUnit.MILLISECONDS)
        .callTimeout(timeout, TimeUnit.MILLISECONDS)
        .build()

    @Bean
    fun retrofitMovieInfoServer(okHttpClient: OkHttpClient, objectMapper: ObjectMapper): Retrofit {
        return Retrofit.Builder()
            .baseUrl(movieInfoUrl)
            .addConverterFactory(JacksonConverterFactory.create(objectMapper))
            .addCallAdapterFactory(Java8CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Bean
    fun retrofitMovieSearchServer(okHttpClient: OkHttpClient, objectMapper: ObjectMapper): Retrofit {
        return Retrofit.Builder()
            .baseUrl(movieSearchUrl)
            .addConverterFactory(JacksonConverterFactory.create(objectMapper))
            .client(okHttpClient)
            .build()
    }

    @Bean
    fun retrofitArtistInfoServer(okHttpClient: OkHttpClient, objectMapper: ObjectMapper): Retrofit {
        return Retrofit.Builder()
            .baseUrl(artistInfoUrl)
            .addConverterFactory(JacksonConverterFactory.create(objectMapper))
            .client(okHttpClient)
            .build()
    }

    @Bean
    fun movieSearchService(@Qualifier("retrofitMovieSearchServer") retrofit: Retrofit): MovieSearchExternalService =
        retrofit.create(MovieSearchExternalService::class.java)

    @Bean
    fun movieInfoService(@Qualifier("retrofitMovieInfoServer") retrofit: Retrofit): MovieInfoExternalService =
        retrofit.create(MovieInfoExternalService::class.java)

    @Bean
    fun artistInfoService(
        @Qualifier("retrofitArtistInfoServer")
        retrofit: Retrofit
    ): ArtistInfoExternalService {
        return retrofit.create(ArtistInfoExternalService::class.java)
    }

}