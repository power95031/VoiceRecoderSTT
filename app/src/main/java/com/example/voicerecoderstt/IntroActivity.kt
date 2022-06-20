package com.example.voicerecoderstt

import android.Manifest
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.example.voicerecoderstt.databinding.ActivityIntroBinding
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import java.io.File

class IntroActivity : AppCompatActivity() {

    lateinit var binding : ActivityIntroBinding
    lateinit var context : Context

    var handler : Handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroBinding.inflate(layoutInflater)
        context = this
        setContentView(binding.root)

        handler.postDelayed({
            checkPermissions()
        }, 1500)
    }

    override fun onDestroy() {
        handler.removeCallbacksAndMessages(null)
        super.onDestroy()
    }

    private fun checkPermissions() {
        val permissionListener = object : PermissionListener {
            override fun onPermissionGranted() {
                val dir = applicationContext.getExternalFilesDir(null)

                val intent = Intent(context, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
            }
            override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                Toast.makeText(applicationContext, getString(R.string.permissionDeniedMessage), Toast.LENGTH_SHORT).show()
            }
        }
        TedPermission.create().setPermissionListener(permissionListener)
            .setDeniedMessage(getString(R.string.permissionDeniedMessage))
            .setPermissions(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO
            )
            .check()
    }

}