package com.example.voicerecoderstt

import android.app.ProgressDialog
import android.content.Context
import android.media.MediaRecorder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.voicerecoderstt.databinding.ActivityMainBinding
import java.io.File

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding
    lateinit var context : Context

    /**
    * 녹음 상태 제어 변수로써
    * False 상태는 녹음중이 아닐때 / True 녹음 활성화 상태
    */
    private var recordState = false
    private var mediaRecorder : MediaRecorder? = null
    private var outputFile : File? = null

    private var progressDialog : ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        context = this

        setContentView(binding.root)

        initView()
        bindView()
    }

    private fun initView() {
        binding.RecordButton.setBackgroundResource(R.drawable.record_button_background)

        progressDialog = ProgressDialog(context).apply {
            setCancelable(false)
        }
    }

    private fun bindView() {
        binding.RecordButton.setOnClickListener {
            if(recordState) {
                // 녹음 활성화 상태이므로 녹음을 종료 시키는 로직 실행
                stopRecording()
            }
            else {
                // 녹음 비활성화 상태이므로 녹음을 시작 시키는 로직 실행
                startRecording()
            }
        }

        binding.SoundWaveFormView.onRequestCurrentAmplitude = {
            mediaRecorder?.maxAmplitude ?: 0
        }
    }

    private fun startRecording() {
        val fileName = "${System.currentTimeMillis()}.mp3"
        outputFile = File(context.cacheDir, fileName)

        mediaRecorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setOutputFile(outputFile)
            prepare()
        }
        mediaRecorder?.start()

        recordState = true

        binding.RecordButton.setImageResource(R.drawable.ic_record_stop)
        binding.SoundWaveFormView.startVisualizing(false)
    }

    private fun stopRecording() {
        mediaRecorder?.run {
            stop()
            release()
        }

        recordState = false

        binding.RecordButton.setImageResource(R.drawable.ic_record)
        binding.SoundWaveFormView.stopVisualizing()

        if(!recordState && outputFile!=null) {
            if(outputFile!!.exists()) {
            }
        }
    }

}