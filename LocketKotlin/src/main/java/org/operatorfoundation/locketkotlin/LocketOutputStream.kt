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
            locket.log("ERROR", "write(b: null): no bytes to write")
        }
        else
        {
            val writeString = String(b, Charsets.UTF_8)
            locket.log("INFO", "write(b: $writeString): ${b.size} - ${b.toHexString()}")
        }
    }

    override fun write(b: ByteArray, off: Int, len: Int)
    {
        output.write(b, off, len)
        if (b.isEmpty())
        {
            locket.log("ERROR", "write(b: null, off: $off, len: $len): no bytes to write")
        }
        else
        {
            val writeString = String(b, Charsets.UTF_8)
            locket.log("INFO", "write(b: $writeString, off: $off, len: $len): ${b.size} - ${b.toHexString()}")
        }
    }
}