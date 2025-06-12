package ru.practicum.android.diploma.search.data.source.remote

import okhttp3.Interceptor
import okhttp3.Response
import ru.practicum.android.diploma.BuildConfig

class HHApiHeadersInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val headers = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer ${BuildConfig.HH_ACCESS_TOKEN}")
            .addHeader("HH-User-Agent", "Yahh diploma (dream@team.one)")
            .build()
        return chain.proceed(headers)
    }
}
