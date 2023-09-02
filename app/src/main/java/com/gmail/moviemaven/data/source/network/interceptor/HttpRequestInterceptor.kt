package com.gmail.moviemaven.data.source.network.interceptor

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

internal class HttpRequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val originalRequest = chain.request()
        val request = originalRequest.newBuilder().url(originalRequest.url)
            .header("accept", "application/json")
            // TODO secure the bearer code
            .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxMmM3YzdiNzhiYTAyZDAzODc2Y2JlZGM5OGY1NDNjZiIsInN1YiI6IjY0OGFiOTYwNTU5ZDIyMDEzOWJiYzA0NiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.lvPL7XCR0k4Os1-QAs9WOAebmavC0q6-BTy1DKQDfnU")
            .build()

        Log.d("HttpRequestInterceptor", request.toString())

        return chain.proceed(request)
    }
}