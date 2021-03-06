/*
 *
 *  Teamspeak Query Plugin Framework
 *
 *  Copyright (C) 2019 - 2020 VortexdataNET
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
 */

package net.vortexdata.tsqpf.remoteShell;

import net.vortexdata.tsqpf.console.Logger;
import net.vortexdata.tsqpf.framework.*;
import org.json.simple.JSONObject;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <p>ConnectionListener class.</p>
 *
 * @author TAXSET
 * @version $Id: $Id
 */
public class ConnectionListener implements Runnable {

    /** Constant <code>CHARSET</code> */
    public static final Charset CHARSET = StandardCharsets.UTF_8;
    public ExecutorService commandExecutor;
    public static final byte[] END_OF_MESSAGE = "<EOM>".getBytes(CHARSET);
    private Logger logger;
    private boolean running = false;
    private Thread thread;
    private ArrayList<Session> sessions = new ArrayList<>();
    private int port;
    private FrameworkContainer frameworkContainer;

    /**
     * <p>Constructor for ConnectionListener.</p>
     *
     * @param frameworkContainer a {@link net.vortexdata.tsqpf.framework.FrameworkContainer} object.
     * @param logger a {@link net.vortexdata.tsqpf.console.Logger} object.
     * @param port a int.
     */
    public ConnectionListener(FrameworkContainer frameworkContainer, Logger logger, int port) {
        this.logger = logger;
        this.port = port;
        this.frameworkContainer = frameworkContainer;
    }

    /**
     * <p>start.</p>
     */
    public void start() {
        if (running) return;
        if (thread == null) thread = new Thread(this, this.getClass().getName());
        thread.start();


    }

    /**
     * <p>stop.</p>
     */
    public void stop() {
        thread.interrupt();
    }

    /**
     * <p>connectionDropped.</p>
     *
     * @param session a {@link net.vortexdata.tsqpf.remoteShell.Session} object.
     */
    public void connectionDropped(Session session) {
        sessions.remove(session);
    }


    private void init() {
        commandExecutor = Executors.newFixedThreadPool(1);
    }


    /** {@inheritDoc} */
    @Override
    public void run() {
        try {
            init();

            ServerSocket listener = new ServerSocket(port);
            running = true;
            while (running) {
                if (thread.isInterrupted()) {
                    listener.close();
                    running = false;
                    break;
                }
                Socket socket = listener.accept();

                InputStream inputStream = socket.getInputStream();
                OutputStream outputStream = socket.getOutputStream();
                JSONObject handshake = new JSONObject();
                SecureRandom random = new SecureRandom();
                byte[] bytes = new byte[32];
                random.nextBytes(bytes);
                String id = Base64.getEncoder().encodeToString(bytes);
                handshake.put("type", "handshake");
                handshake.put("sessionId", id);


                outputStream.write(handshake.toJSONString().getBytes(CHARSET));
                outputStream.write(END_OF_MESSAGE);
                outputStream.flush();
                sessions.add(new Session(frameworkContainer, id, socket, inputStream, outputStream, this, logger));


            }
        } catch (Exception e) {

            logger.printError(e.getMessage());
        }
    }
}
