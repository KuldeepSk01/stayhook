package com.stayhook.util

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.util.*

class MultiPartHelper {

    companion object{
        fun getRequestBody(value: String): RequestBody {
            return if (value.isNotEmpty()) {
                RequestBody.create("text/plain".toMediaTypeOrNull(), value)
            } else {
                RequestBody.create("text/plain".toMediaTypeOrNull(), "")
            }
        }

        fun getRequestFile(jsonFile: File, key: String): MultipartBody.Part? {
            var body: MultipartBody.Part? = null
            if (jsonFile.exists()) {
                val requestFile = jsonFile.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                // MultipartBody.Part is used to send also the actual file name
                body = MultipartBody.Part.createFormData(
                    key,
                    getTimeinMillis().toString() + "_media.jpg",
                    requestFile
                )
            }
            return body
        }

        fun convertImagesToMultiPart(filePaths: ArrayList<String?>?, key: String):
                Array<MultipartBody.Part?>? {
            var size = 0
            size = if (filePaths != null && filePaths.size > 0) {
                filePaths.size
            } else {
                0
            }
            val surveyImagesParts = arrayOfNulls<MultipartBody.Part>(size)
            for (i in filePaths!!.indices) {
                val file = File(filePaths[i])
                MultipartBody.Part.createFormData(
                    "file", file.name, RequestBody.create(
                        "image/*".toMediaTypeOrNull(), file
                    )
                )
                // multiPartList.add(filePart);
                val surveyBody = RequestBody.create("image/*".toMediaTypeOrNull(), file)
                surveyImagesParts[i] = MultipartBody.Part.createFormData(
                    "$key[$i]", file.name, surveyBody
                )
            }
            return surveyImagesParts
        }

        fun getMultipartData(
            imageFile: File,
            key: String
        ): MultipartBody.Part? {
            var body: MultipartBody.Part? = null
            if (imageFile.exists()) {
                val requestFile = imageFile.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                // MultipartBody.Part is used to send also the actual file name
                body = MultipartBody.Part.createFormData(
                    key,
                    getTimeinMillis().toString() + "_media.jpg",
                    requestFile
                )
            }
            return body
        }

        fun getMultipartDataVideo(
            imageFile: File,
            key: String
        ): MultipartBody.Part? {
            var body: MultipartBody.Part? = null
            if (imageFile.exists()) {
                val requestFile = imageFile.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                // MultipartBody.Part is used to send also the actual file name
                body = MultipartBody.Part.createFormData(
                    key,
                    getTimeinMillis().toString() + ".mp4",
                    requestFile
                )
            }
            return body
        }

        fun getRequestBody1(imageFile: File, key: String): RequestBody? {
            var body: RequestBody? = null
            if (imageFile.exists()) {
                body = imageFile.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                // MultipartBody.Part is used to send also the actual file name
            }
            return body
        }

        fun getTimeinMillis(): Long {
            val cal = Calendar.getInstance()
            cal.set(
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH),
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                cal.get(Calendar.SECOND)
            )
            return cal.getTimeInMillis()
        }
    }



}