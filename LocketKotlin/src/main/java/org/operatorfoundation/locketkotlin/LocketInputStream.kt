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
            val resultString = String(b, Charsets.UTF_8)
            locket.log("INFO", "read(b: $b): \"$resultString\" - $bytesRead - ${b.toHexString()}")
        }
        else
        {
            locket.log("ERROR", "read(b: $b): null")
        }
        return bytesRead
    }

    override fun read(b: ByteArray, off: Int, len: Int): Int
    {
        val bytesRead = input.read(b, off, len)
        if (b.isNotEmpty())
        {
            val resultString = String(b, Charsets.UTF_8)
            locket.log("INFO", "read(b: $b, off: $off, len: $len): \"$resultString\" - $bytesRead - ${b.toHexString()}")
        }
        else
        {
            locket.log("ERROR", "read(b: $b, off: $off, len: $len): null")
        }
        return bytesRead
    }
}