package ht.pq.khanh.helper

import android.os.Environment
import java.io.File

/**
 * Created by khanhpq on 10/26/17.
 */
object ExternalStorageHelper {
    fun isExternalStorageWritable() : Boolean  = Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED

    fun openDirectory(dirName : String) : File?{
        var file : File? = null
        if (isExternalStorageWritable()){
            val storageDir = Environment.getExternalStorageDirectory()
            file = File(storageDir, dirName)
            if (file != null && !file.exists()){
                file.mkdirs()
            }
        }
        return file
    }
}