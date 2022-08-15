package org.operatorfoundation.locketkotlin

import android.content.Context
import java.io.File

class Locket(context: Context) {
    val locketDir = File(context.filesDir, "locket")

    init {
        if (!locketDir.isDirectory) {
            locketDir.mkdir()
        }
    }

    fun getLogs(): Array<File> {
        return locketDir.listFiles()
    }

    fun getLog(fileName: String): String {
        val filePath = File(locketDir, fileName)
        if (filePath.isFile) {
            return filePath.readText(Charsets.UTF_8)
        } else {
            return ""
        }
    }

    fun clear() {
        if (locketDir.isDirectory) {
            locketDir.delete()
        }
    }
}