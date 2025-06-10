package ru.practicum.android.diploma.search.data.source.remote

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import okio.Buffer
import timber.log.Timber

class HHApiLoggingInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()

        val requestBody = request.body
        val buffer = Buffer()
        requestBody?.writeTo(buffer)
        val requestBodyString = buffer.readUtf8()
        Timber.d("Request URL: ${request.url}")
        Timber.d("Request Headers: ${request.headers}")
        Timber.d("Request Body: $requestBodyString")

        val response = chain.proceed(request)

        val responseBody = response.body
        val contentType = responseBody?.contentType()
        val content = responseBody?.string() ?: ""

        Timber.d("Response Code: ${response.code}")
        Timber.d("Response Headers: ${response.headers}")
        Timber.d("Response Body: $content")

        return response.newBuilder()
            .body(content.toResponseBody(contentType))
            .build()
    }

}
