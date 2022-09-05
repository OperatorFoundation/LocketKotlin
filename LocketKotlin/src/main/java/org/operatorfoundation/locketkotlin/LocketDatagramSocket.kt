package org.operatorfoundation.locketkotlin

import android.content.Context
import android.os.Build
import java.io.File
import java.io.FileWriter
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.net.SocketAddress
import java.nio.channels.DatagramChannel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class LocketDatagramSocket(context: Context?, logDir: String?, var datagramSocket: DatagramSocket, var identifier: String): DatagramSocket() {
    private var locketDir: File
    protected var logPath: File
    private var writer: FileWriter

    init {
        if (context == null) {
            requireNotNull (logDir)
            locketDir = File(logDir)
            if (!locketDir.isDirectory) {
                locketDir.mkdir()
            }

            logPath = File(locketDir, "locket.log")
            if (!logPath.isFile) {
                logPath.createNewFile()
            }

            writer = FileWriter(logPath, true)
        } else {
            locketDir = context.filesDir
            logPath = File(locketDir, "locket.log")

            if (!logPath.isFile) {
                logPath.createNewFile()
            }

            writer = FileWriter(logPath, true)
        }
    }

    override fun bind(addr: SocketAddress?) {
        datagramSocket.bind(addr)
    }

    override fun close() {
        this.log("INFO", "close(): socket closed")
        writer.close()
        datagramSocket.close()
    }

    override fun connect(addr: SocketAddress?) {
        datagramSocket.connect(addr)
    }

    override fun connect(address: InetAddress?, port: Int) {
        datagramSocket.connect(address, port)
    }

    override fun disconnect() {
        datagramSocket.disconnect()
    }
    
    override fun getBroadcast(): Boolean {
        return datagramSocket.broadcast
    }
    
    override fun getChannel(): DatagramChannel {
        return datagramSocket.channel
    }

    override fun getInetAddress(): InetAddress {
        return datagramSocket.inetAddress
    }

    override fun getLocalAddress(): InetAddress {
        return datagramSocket.localAddress
    }

    override fun getLocalPort(): Int {
        return datagramSocket.localPort
    }

    override fun getLocalSocketAddress(): SocketAddress {
        return datagramSocket.localSocketAddress
    }

    override fun getPort(): Int {
        return datagramSocket.port
    }

    override fun getReceiveBufferSize(): Int {
        return datagramSocket.receiveBufferSize
    }

    override fun getRemoteSocketAddress(): SocketAddress {
        return datagramSocket.remoteSocketAddress
    }

    override fun getReuseAddress(): Boolean {
        return datagramSocket.reuseAddress
    }

    override fun getSendBufferSize(): Int {
        return datagramSocket.sendBufferSize
    }

    override fun getSoTimeout(): Int {
        return datagramSocket.soTimeout
    }

    override fun getTrafficClass(): Int {
        return datagramSocket.trafficClass
    }

    override fun isBound(): Boolean {
        return datagramSocket.isBound
    }

    override fun isClosed(): Boolean {
        return datagramSocket.isClosed
    }

    override fun isConnected(): Boolean {
        return datagramSocket.isConnected
    }

    override fun receive(p: DatagramPacket?) {
        datagramSocket.receive(p)
        if (p != null) {
            val data = p.data
            val resultString = String(data, Charsets.UTF_8)
            val hexString = data.toHexString()
            val bytesRead = p.length
            this.log("INFO", "receive(p: DatagramPacket?): \"$resultString\" - $bytesRead - $hexString")
        } else {
            this.log("ERROR", "receive(p: DatagramPacket?): did not receive any bytes")
        }

    }

    override fun send(p: DatagramPacket?) {
        datagramSocket.send(p)
        if (p != null) {
            val data = p.data
            val resultString = String(data, Charsets.UTF_8)
            val hexString = data.toHexString()
            val bytesRead = p.length
            this.log("INFO", "send(p: DatagramPacket?): \"$resultString\" - $bytesRead - $hexString")
        } else {
            this.log("ERROR", "send(p: DatagramPacket?): did not send any bytes")
        }
    }

    override fun setBroadcast(on: Boolean) {
        datagramSocket.broadcast = on
    }

    override fun setReceiveBufferSize(size: Int) {
        datagramSocket.receiveBufferSize = size
    }

    override fun setReuseAddress(on: Boolean) {
        datagramSocket.reuseAddress = on
    }

    override fun setSendBufferSize(size: Int) {
        datagramSocket.sendBufferSize = size
    }

    override fun setSoTimeout(timeout: Int) {
        datagramSocket.soTimeout = timeout
    }

    override fun setTrafficClass(tc: Int) {
        datagramSocket.trafficClass = tc
    }

    fun log(level: String, message: String) {
        val identifier = this.identifier

        val logHeader: String = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")
            val formatted = current.format(formatter)
            "[$level] $formatted $identifier"
        } else {
            "[$level] $identifier"
        }

        writer.write("$logHeader : $message\n")
    }
}