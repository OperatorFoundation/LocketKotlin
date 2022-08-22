package org.operatorfoundation.locketkotlin

import android.content.Context
import java.net.Socket
import java.net.SocketAddress

class OKHttpLocketConnection(context: Context?, nonAppDirectory: String?, socket: Socket, logFileName: String) :
    LocketConnection(context, nonAppDirectory, socket, logFileName) {

    //@ExperimentalUnsignedTypes
    override fun connect(endpoint: SocketAddress?) {
        // ignore calls to connect
    }

    // @ExperimentalUnsignedTypes
    override fun connect(endpoint: SocketAddress?, timeout: Int) {
        // ignore calls to connect
    }

    // Converts this socket to a String.
    override fun toString(): String {
        return "OKHttpLocketConnection[logPath = $logPath]"
    }
}