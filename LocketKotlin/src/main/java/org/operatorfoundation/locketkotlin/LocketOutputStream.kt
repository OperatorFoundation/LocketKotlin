package org.operatorfoundation.locketkotlin

import java.io.OutputStream

class LocketOutputStream(
    private val output: OutputStream,
    private val locket: LocketConnection
): OutputStream() {
    override fun close() {
        locket.log("INFO", "close(): output stream closed")
        output.close()
    }

    override fun flush() {
        locket.log("INFO", "flush(): output stream flushed")
        output.flush()
    }

    override fun write(b: Int) {
        output.write(b)
        locket.log("INFO", "write(b: $b) called")
    }

    override fun write(b: ByteArray)
    {
        output.write(b)
        if (b.isEmpty())
        {
            locket.log("ERROR", "write(b: ByteArray): no bytes to write")
        }
        else
        {
            val writeString = String(b, Charsets.UTF_8).substring(0, b.size)
            val hexString = b.toHexString().substring(0, b.size * 2)
            locket.log("INFO", "write(b: ByteArray): $writeString - ${b.size} - $hexString")
        }
    }

    override fun write(b: ByteArray, off: Int, len: Int)
    {
        output.write(b, off, len)
        if (b.isEmpty())
        {
            locket.log("ERROR", "write(b: ByteArray, off: $off, len: $len): no bytes to write")
        }
        else
        {
            val buffer = b.sliceArray(off until len)
            val writeString = String(buffer, Charsets.UTF_8).substring(0, buffer.size)
            val hexString = buffer.toHexString().substring(0, buffer.size * 2)
            locket.log("INFO", "write(b: ByteArray, off: $off, len: $len): $writeString - ${b.size} - $hexString")
        }
    }
}