package org.operatorfoundation.locketkotlin

import android.content.Context
import java.io.File
import java.io.FileWriter
import java.net.Socket

class LocketConnection(context: Context?, nonAppDirectory: String?, var socket: Socket, logFileName: String): Socket()
{
    private lateinit var locketDir: File
    private val logPath = File(locketDir, logFileName)
    private val writer = FileWriter(logPath, true)
    private val output = socket.getOutputStream()
    private val input = socket.getInputStream()

    init {
        if (context == null) {
            requireNotNull (nonAppDirectory)
            locketDir = File(nonAppDirectory)
        } else {
            locketDir = File(context.filesDir, "locket")
        }

        if (!locketDir.isDirectory) {
            locketDir.mkdir()
        }

        if (!logPath.isFile) {
            logPath.createNewFile()
        }
    }

    fun read(): Int
    {
        val bytesRead = input.read()
        this.log("read(): $bytesRead")
        return bytesRead
    }

    fun read(b: ByteArray): Int
    {
        val bytesRead = input.read(b)
        if (b.isNotEmpty())
        {
            val resultString = String(b, Charsets.UTF_8)
            this.log("read(b: $b): \"$resultString\" - $bytesRead - ${b.toHexString()}")
        }
        else
        {
            this.log("read(b: $b): null")
        }
        return bytesRead
    }

    fun read(b: ByteArray, off: Int, len: Int): Int
    {
        val bytesRead = input.read(b, off, len)
        if (b.isNotEmpty())
        {
            val resultString = String(b, Charsets.UTF_8)
            this.log("read(b: $b, off: $off, len: $len): \"$resultString\" - $bytesRead - ${b.toHexString()}")
        }
        else
        {
            this.log("read(b: $b, off: $off, len: $len): null")
        }
        return bytesRead
    }

    fun readBytes(): ByteArray?
    {
        val data = input.readBytes()
        return if (data.isNotEmpty())
        {
            val resultString = String(data, Charsets.UTF_8)
            this.log("readBytes(): \"$resultString\" - ${data.size} - ${data.toHexString()}")
            data
        }
        else
        {
            this.log("readBytes(): null")
            null
        }
    }

    // Reads up to a specific number of bytes in a byte array.
    fun readNBytes(numBytes: Int): ByteArray?
    {
        try
        {
            val buffer = ByteArray(numBytes)
            var offset = input.read(buffer)

            if (offset == -1)
            {
                this.log("readNBytes(numBytes: $numBytes): The end of the stream has been reached")
                return null
            }
            while (offset != numBytes)
            {
                val bytesRead = input.read(buffer, offset, numBytes - offset)
                if (bytesRead == -1)
                {
                    this.log("readNBytes(numBytes: $numBytes): Could not read the specified number of bytes ${numBytes - offset} because the end of the stream has been reached")
                    return null
                }
                offset += bytesRead
            }
            val resultString = String(buffer, Charsets.UTF_8)
            this.log("readNBytes(numBytes: $numBytes): \"$resultString\" - ${buffer.size} - ${buffer.toHexString()}")
            return buffer
        }
        catch(readError: Exception)
        {
            this.log("readNBytes(numBytes: $numBytes): Error: ${readError.message}")
            throw readError
        }
    }

    // TODO: do we want the write(Int)? (yes)

    fun write(b: ByteArray)
    {
        output.write(b)
        if (b.isEmpty())
        {
            this.log("write(b: null): no bytes to write")
        }
        else
        {
            val writeString = String(b, Charsets.UTF_8)
            // TODO: Nil check this
            this.log("write(b: $writeString): ${b.size} - ${b.toHexString()}")
        }
    }

    fun write(b: ByteArray, off: Int, len: Int)
    {
        output.write(b, off, len)
        if (b.isEmpty())
        {
            this.log("write(b: null, off: $off, len: $len): no bytes to write")
        }
        else
        {
            val writeString = String(b, Charsets.UTF_8)
            this.log("write(b: $writeString, off: $off, len: $len): ${b.size} - ${b.toHexString()}")
        }
    }

    override fun close()
    {
        // FIXME: log the close
        writer.close()
        socket.close()
    }

    fun log(string: String)
    {
        writer.write(string)
        writer.write("\n")
    }
}

fun ByteArray.toHexString() = asUByteArray().joinToString("")
{
    it.toString(16).padStart(2, '0')
}