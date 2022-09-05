package org.operatorfoundation.locketkotlin

import android.content.Context
import android.os.Build
import java.io.File
import java.io.FileWriter
import java.io.InputStream
import java.io.OutputStream
import java.net.InetAddress
import java.net.Socket
import java.net.SocketAddress
import java.nio.channels.SocketChannel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

open class LocketConnection(context: Context?, logDir: String?, var socket: Socket, var identifier: String): Socket()
{
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

    // Binds the socket to a local address.
    override fun bind(bindpoint: SocketAddress?) {
        socket.bind(bindpoint)
    }

    // Closes this socket.
    override fun close() {
        this.log("INFO", "close(): socket closed")
        writer.close()
        socket.close()
    }

    // Connects this socket to the server and initiates the handshake.
    override fun connect(endpoint: SocketAddress?) {
        socket.connect(endpoint)
    }

    // Connects this socket to the server with a specified timeout value and initiates the handshake.
    override fun connect(endpoint: SocketAddress?, timeout: Int) {
        socket.connect(endpoint, timeout)
    }

    // Returns the unique SocketChannel object associated with this socket, if any.
    override fun getChannel(): SocketChannel {
        return socket.channel
    }

    // Returns the address to which the socket is connected.
    override fun getInetAddress(): InetAddress {
        return socket.inetAddress
    }

    // Returns an input stream and the decryption cipher for this socket.
    override fun getInputStream(): InputStream {
        return LocketInputStream(socket.getInputStream(), this)
    }

    // Tests if SO_KEEPALIVE is enabled.
    override fun getKeepAlive(): Boolean {
        return socket.keepAlive
    }

    // Gets the local address to which the socket is bound.
    override fun getLocalAddress(): InetAddress {
        return socket.localAddress
    }

    // Returns the local port number to which this socket is bound.
    override fun getLocalPort(): Int {
        return socket.localPort
    }

    // Returns the address of the endpoint this socket is bound to.
    override fun getLocalSocketAddress(): SocketAddress {
        return socket.localSocketAddress
    }

    // Tests if SO_OOBINLINE is enabled.
    override fun getOOBInline(): Boolean {
        return socket.oobInline
    }

    // Returns an output stream and the encryption cipher for this socket.
    override fun getOutputStream(): OutputStream {
        return LocketOutputStream(socket.getOutputStream(), this)
    }

    // Returns the remote port number to which this socket is connected.
    override fun getPort(): Int {
        return socket.port
    }

    // Gets the value of the SO_RCVBUF option for this Socket, that is the buffer size used by the platform for input on this Socket.
    override fun getReceiveBufferSize(): Int {
        return socket.receiveBufferSize
    }

    // Returns the address of the endpoint this socket is connected to, or null if it is unconnected.
    override fun getRemoteSocketAddress(): SocketAddress {
        return socket.remoteSocketAddress
    }

    // Tests if SO_REUSEADDR is enabled.
    override fun getReuseAddress(): Boolean {
        return socket.reuseAddress
    }

    // Get value of the SO_SNDBUF option for this Socket, that is the buffer size used by the platform for output on this Socket.
    override fun getSendBufferSize(): Int {
        return socket.sendBufferSize
    }

    // Returns setting for SO_LINGER. -1 implies that the option is disabled.
    override fun getSoLinger(): Int {
        return socket.soLinger
    }

    // Returns setting for SO_TIMEOUT. 0 returns implies that the option is disabled (i.e., timeout of infinity).
    override fun getSoTimeout(): Int {
        return socket.soTimeout
    }

    // Tests if TCP_NODELAY is enabled.
    override fun getTcpNoDelay(): Boolean {
        return socket.tcpNoDelay
    }

    // Gets traffic class or type-of-service in the IP header for packets sent from this Socket.
    override fun getTrafficClass(): Int {
        return socket.trafficClass
    }

    // Returns the binding state of the socket.
    override fun isBound(): Boolean {
        return socket.isBound
    }

    // Returns the closed state of the socket.
    override fun isClosed(): Boolean {
        return socket.isClosed
    }

    // Returns the connection state of the socket.
    override fun isConnected(): Boolean {
        return socket.isConnected
    }

    // Returns whether the read-half of the socket connection is closed.
    override fun isInputShutdown(): Boolean {
        return socket.isInputShutdown
    }

    // Returns whether the write-half of the socket connection is closed.
    override fun isOutputShutdown(): Boolean {
        return socket.isOutputShutdown
    }

    // Send one byte of urgent data on the socket.
    override fun sendUrgentData(data: Int) {
        socket.sendUrgentData(data)
    }

    // Enable/disable SO_KEEPALIVE.
    override fun setKeepAlive(on: Boolean) {
        socket.keepAlive = on
    }

    // Enable/disable SO_OOBINLINE (receipt of TCP urgent data) By default, this option is disabled and TCP urgent data received on a socket is silently discarded.
    override fun setOOBInline(on: Boolean) {
        socket.oobInline = on
    }

    // Sets performance preferences for this socket.
    override fun setPerformancePreferences(connectionTime: Int, latency: Int, bandwidth: Int) {
    socket.setPerformancePreferences(connectionTime, latency, bandwidth)
    }

    // Sets the SO_RCVBUF option to the specified value for this Socket.
    override fun setReceiveBufferSize(size: Int) {
        socket.receiveBufferSize = size
    }

    // Enable/disable the SO_REUSEADDR socket option.
    override fun setReuseAddress(on: Boolean) {
        socket.reuseAddress = on
    }

    // Sets the SO_SNDBUF option to the specified value for this Socket.
    override fun setSendBufferSize(size: Int) {
        socket.sendBufferSize = size
    }

    // Enable/disable SO_LINGER with the specified linger time in seconds.
    override fun setSoLinger(on: Boolean, linger: Int) {
        socket.setSoLinger(on, linger)
    }

    // Enable/disable SO_TIMEOUT with the specified timeout, in milliseconds.
    override fun setSoTimeout(timeout: Int) {
        socket.soTimeout = timeout
    }

    // Enable/disable TCP_NODELAY (disable/enable Nagle's algorithm).
    override fun setTcpNoDelay(on: Boolean) {
        socket.tcpNoDelay = on
    }

    // Sets traffic class or type-of-service octet in the IP header for packets sent from this Socket.
    override fun setTrafficClass(tc: Int) {
        socket.trafficClass = tc
    }

    // Places the input stream for this socket at "end of stream".
    override fun shutdownInput() {
        socket.shutdownInput()
    }

    // Disables the output stream for this socket.
    override fun shutdownOutput() {
        socket.shutdownOutput()
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

@OptIn(ExperimentalUnsignedTypes::class)
public fun ByteArray.toHexString() = asUByteArray().joinToString("") {
    it.toString(16).padStart(2, '0')
}