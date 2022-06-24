package com.example.voicerecoderstt

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.voicerecoderstt.Adapter.ResultAdapter
import com.example.voicerecoderstt.Common.Common.TAG
import com.example.voicerecoderstt.ObjectFactory.SecondDataObject
import com.example.voicerecoderstt.Retrofit.RetrofitClient
import com.example.voicerecoderstt.databinding.ActivityResultViewBinding
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class ResultViewActivity : AppCompatActivity() {

    lateinit var binding : ActivityResultViewBinding
    lateinit var context : Context

    lateinit var progressDialog : ProgressDialog
    private var fileName:String? = null
    private var audioFile:File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultViewBinding.inflate(layoutInflater)
        context = this

        setContentView(binding.root)

        initView()
        bindView()
    }

    @SuppressLint("SetTextI18n")
    private fun initView() {
        progressDialog = ProgressDialog(context)

        val intentData:Intent? = intent

        intentData?.also {
            fileName = it.getStringExtra("fileName")
            val filePath = it.getStringExtra("filePath")
            val fileAbsolutePath = it.getStringExtra("fileAbsolutePath")

            if(filePath.isNullOrBlank()) {
                // 에러처리
                Log.e(TAG, "filePath is null or blank")
            }

            binding.RecordFileName.text = """
                경로: $filePath
                절대경로: $fileAbsolutePath
                파일명: $fileName
            """.trimIndent()

            audioFile = File(filePath)
        } ?: run {
            binding.RecordFileName.text = "인텐트 데이터 null"
        }
    }

    private fun bindView() {
        binding.ApiCallButton.setOnClickListener { audioFileUpload(audioFile!!) }
    }

    private fun audioFileUpload(file:File) {
        progressDialog.show()

        val requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file)
        val audioFilePart = MultipartBody.Part.createFormData("recordAudioFile", file.name, requestBody)

        val call = RetrofitClient.service.audioFileUpload2(audioFilePart)
        call.enqueue(object:Callback<SecondDataObject> {
            override fun onResponse(call: Call<SecondDataObject>, response: Response<SecondDataObject>) {
                progressDialog.dismiss()

                if(response.isSuccessful) {
                    setResultData(response.body()!!)
                } else {
                    Toast.makeText(context, "API Error Response", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<SecondDataObject>, t: Throwable) {
                progressDialog.dismiss()

                Toast.makeText(context, "API 호출 오류, ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun setResultData(resultObj:SecondDataObject) {
        binding.RecordFileName.visibility = View.GONE
        binding.ApiCallButton.visibility = View.GONE

        val resultAdapter = ResultAdapter(resultObj)
        binding.ApiResultList.apply {
            visibility = View.VISIBLE
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = resultAdapter
        }
    }
}