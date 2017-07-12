package com.webhopers.medlog.models

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Environment
import android.widget.Toast
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import java.io.File
import java.io.FileOutputStream

class SaveImageTarget(val context: Context): Target {
    override fun onPrepareLoad(placeHolderDrawable: Drawable?) {


    }

    override fun onBitmapFailed(errorDrawable: Drawable?) {
    }

    override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {


        val folder = File("${Environment
                .getExternalStorageDirectory()
                .absolutePath}/medlog/")
        if (!folder.exists())
            folder.mkdirs()

        val filename = System.currentTimeMillis().toString() + ".jpg"
        val file = File(folder, filename)

        try {
            val outputStream = FileOutputStream(file)
            bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            outputStream.flush()
            outputStream.close()
        } catch (e: Exception) {}

        Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show()


    }
}