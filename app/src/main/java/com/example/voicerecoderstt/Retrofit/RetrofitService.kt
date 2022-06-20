package com.example.voicerecoderstt.Retrofit

import com.example.voicerecoderstt.ObjectFactory.SecondDataObject
import com.example.voicerecoderstt.ObjectFactory.dataObject
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface RetrofitService {
    @Multipart
    @POST("api/audioFileUpload")
    fun audioFileUpload(
        @Part filePart:MultipartBody.Part
    ) : Call<dataObject>

    @Multipart
    @POST("api/audioFileUpload2")
    fun audioFileUpload2(
        @Part filePart:MultipartBody.Part
    ) : Call<SecondDataObject>
}