package com.example.services_test_kotlin

import android.app.Activity
import io.flutter.embedding.android.FlutterActivity
import android.content.Intent
import android.os.Bundle
import androidx.annotation.NonNull
import io.flutter.Log
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel
import net.gotev.uploadservice.protocols.multipart.MultipartUploadRequest


class MainActivity : FlutterActivity() {
    companion object {
        const val pickFileRequestCode = 42
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun configureFlutterEngine(@NonNull flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        MethodChannel(
            flutterEngine.dartExecutor.binaryMessenger,
            "com.smart.aeglepro_client.upload"
        ).setMethodCallHandler { call, result ->
            when (call.method == "pickFile") {
//                Log.e("TAG", "CALLED");
                pickFile()
            }

        }
    }

    fun pickFile() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "*/*"
            flags =
                (Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
        }
        startActivityForResult(intent, pickFileRequestCode)
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        if (requestCode == pickFileRequestCode && resultCode == Activity.RESULT_OK) {
            data?.let {
                onFilePicked(it.data.toString())
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    fun onFilePicked(filePath: String) {
        MultipartUploadRequest(
            this,
            serverUrl = "https://api.aeglepro.com/api/files/exercises"
        )
            .setMethod("POST")
            .addFileToUpload(
                filePath = filePath,
                parameterName = "myFile"
            ).startUpload()
    }
}