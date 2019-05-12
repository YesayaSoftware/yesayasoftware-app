package com.yesayasoftware.learning.internal

import com.yesayasoftware.learning.data.network.RetrofitBuilder
import okhttp3.ResponseBody
import retrofit2.Converter

object Utils {
    fun convertErrors(response: ResponseBody): ApiError? {
        val converter : Converter<ResponseBody, ApiError> =
            RetrofitBuilder.getRetrofits().responseBodyConverter(ApiError::class.java, arrayOfNulls<Annotation>(0))

        var apiError: ApiError? = null

        try {
            apiError = converter.convert(response)
        } catch (e: ApiErrorException) {
            e.printStackTrace()
        }

        return apiError
    }
}