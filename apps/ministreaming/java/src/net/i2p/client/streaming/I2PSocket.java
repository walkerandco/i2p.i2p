package net.i2p.client.streaming;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import net.i2p.data.Destination;

/**
 * Minimalistic adapter between the socket api and I2PTunnel's way.
 * Note that this interface is a "subinterface" of the interface
 * defined in the "official" streaming api.
 */
public interface I2PSocket {
    /**
     * Return the Destination of this side of the socket.
     */
    public Destination getThisDestination();

    /**
     * Return the destination of the peer.
     */
    public Destination getPeerDestination();

    /**
     * Return an InputStream to read from the socket.
     */
    public InputStream getInputStream() throws IOException;

    /**
     * Return an OutputStream to write into the socket.
     */
    public OutputStream getOutputStream() throws IOException;

    /** 
     * Retrieve this socket's configuration
     */
    public I2PSocketOptions getOptions();
    /** 
     * Configure the socket
     */
    public void setOptions(I2PSocketOptions options);
    
    /**
     * How long we will wait blocked on a read() operation.  This is simply a
     * helper to query the I2PSocketOptions
     *
     * @return milliseconds to wait, or -1 if we will wait indefinitely
     */
    public long getReadTimeout();

    /**
     * Define how long we will wait blocked on a read() operation (-1 will make
     * the socket wait forever).  This is simply a helper to adjust the 
     * I2PSocketOptions
     *
     */
    public void setReadTimeout(long ms);

    /**
     * Closes the socket if not closed yet
     */
    public void close() throws IOException;
    
    public void setSocketErrorListener(SocketErrorListener lsnr);
    /**
     * Allow notification of underlying errors communicating across I2P without
     * waiting for any sort of cleanup process.  For example, if some data could
     * not be sent, this listener is notified immediately, and while the input/output
     * streams are notified through IOExceptions, they are told only after the 
     * TCP-like stream is closed (which may be a minute later, if the close message
     * times out as well).  This is not fired on normal close() activity.
     *
     */
    public interface SocketErrorListener {
        /**
         * An error occurred communicating with the peer.
         */
        void errorOccurred();
    }
}
