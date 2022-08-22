package org.operatorfoundation.locketkotlin

import android.content.Context
import java.net.InetAddress
import java.net.Socket
import javax.net.SocketFactory

class LocketFactory(val context: Context?, val nonAppDirectory: String?, val factory: SocketFactory, val logFileName: String): SocketFactory() {
    override fun createSocket(p0: String?, p1: Int): Socket {
        val socket = factory.createSocket(p0, p1)
        return LocketConnection(context, nonAppDirectory, socket, logFileName)
    }

    override fun createSocket(p0: String?, p1: Int, p2: InetAddress?, p3: Int): Socket {
        val socket = factory.createSocket(p0, p1, p2, p3)
        return LocketConnection(context, nonAppDirectory, socket, logFileName)
    }

    override fun createSocket(p0: InetAddress?, p1: Int): Socket {
        val socket = factory.createSocket(p0, p1)
        return LocketConnection(context, nonAppDirectory, socket, logFileName)
    }

    override fun createSocket(p0: InetAddress?, p1: Int, p2: InetAddress?, p3: Int): Socket {
        val socket = factory.createSocket(p0, p1, p2, p3)
        return LocketConnection(context, nonAppDirectory, socket, logFileName)
    }

    override fun createSocket(): Socket {
        val socket = factory.createSocket()
        return LocketConnection(context, nonAppDirectory, socket, logFileName)
    }
}