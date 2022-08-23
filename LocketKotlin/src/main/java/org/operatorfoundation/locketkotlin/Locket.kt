package org.operatorfoundation.locketkotlin

import android.content.Context
import java.io.File

class Locket(context: Context) {
    private val locketDir = File(context.filesDir, "locket")

    init {
        if (!locketDir.isDirectory) {
            locketDir.mkdir()
        }
    }

    fun getLogs(): Array<File> {
        return locketDir.listFiles()
    }

    fun getLog(): String {
        val filePath = File(locketDir, "locket.log")
        return filePath.readText(Charsets.UTF_8)
    }

    fun clear() {
        if (locketDir.isDirectory) {
            locketDir.delete()
        }
    }
}