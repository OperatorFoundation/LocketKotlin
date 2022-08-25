package org.operatorfoundation.locketkotlin

import java.io.InputStream

class LocketInputStream(
    private val input: InputStream,
    private val locket: LocketConnection
): InputStream() {
    override fun close() {
        locket.log("INFO", "close(): input stream closed")
        input.close()
    }

    override fun read(): Int
    {
        val bytesRead = input.read()
        locket.log("INFO", "read(): read $bytesRead bytes")
        return bytesRead
    }

    override fun read(b: ByteArray): Int
    {
        val bytesRead = input.read(b)
        if (b.isNotEmpty())
        {
            val slice = b.sliceArray(0 until bytesRead)
            val resultString = String(slice, Charsets.UTF_8)
            val hexString = slice.toHexString()
            locket.log("INFO", "read(b: ByteArray): \"$resultString\" - $bytesRead - $hexString")
        }
        else
        {
            locket.log("ERROR", "read(b: ByteArray): read zero bytes")
        }
        return bytesRead
    }

    override fun read(b: ByteArray, off: Int, len: Int): Int
    {
        val bytesRead = input.read(b, off, len)
        if (b.isNotEmpty())
        {
            val slice = b.sliceArray(0 until bytesRead)
            val resultString = String(slice, Charsets.UTF_8)
            val hexString = slice.toHexString()
            locket.log("INFO", "read(b: ByteArray, off: $off, len: $len): \"$resultString\" - $bytesRead - $hexString")
        }
        else
        {
            locket.log("ERROR", "read(b: ByteArray, off: $off, len: $len): read zero bytes")
        }
        return bytesRead
    }
}