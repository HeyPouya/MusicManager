package ir.heydarii.musicmanager.utils

import android.content.Context
import android.graphics.Bitmap
import java.io.File
import javax.inject.Inject

/**
 * Provides functions to save or delete images from the device
 */
class ImageStorageManager @Inject constructor() {

    /**
     * Saves Images in the device
     */
    fun saveToInternalStorage(context: Context, bitmapImage: Bitmap, imageFileName: String): String {
        val name = imageFileName.replace(" ", "") + ".png"
        context.openFileOutput(name, Context.MODE_PRIVATE).use { fos ->
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 25, fos)
        }
        return context.filesDir.absolutePath + "/" + name
    }

    /**
     * Removes the images from the device
     */
    fun deleteImageFromInternalStorage(path: String): Boolean {
        val file = File(path)
        return file.delete()
    }
}
