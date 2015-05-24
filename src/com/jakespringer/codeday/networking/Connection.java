package com.jakespringer.codeday.networking;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Connection {

    private Thread inHandle, outHandle;

    private Socket socket;
    private InputStream is;
    private OutputStream os;

    private Queue<byte[]> input, output;
    private List<Object> toInturrupt;

    public Connection() {
        input = new ConcurrentLinkedQueue<>();
        output = new ConcurrentLinkedQueue<>();
        toInturrupt = Collections.synchronizedList(new LinkedList<Object>());
    }

    public void start(String hostname, int port) throws UnknownHostException, IOException {
        start(new Socket(InetAddress.getByName(hostname), port));
    }

    public void start(final Socket sock) throws UnknownHostException, IOException {
        socket = sock;
        is = socket.getInputStream();
        os = socket.getOutputStream();

        inHandle = new Thread(new Runnable() {
            @Override
            public void run() {
                Connection.this.runIn(sock);
            }
        });

        inHandle.setDaemon(true);
        inHandle.start();

        outHandle = new Thread(new Runnable() {
            @Override
            public void run() {
                Connection.this.runOut(sock);
            }
        });

        outHandle.setDaemon(true);
        outHandle.start();
    }

    private void nrunIn(Socket socket) throws IOException {
        try {
            byte[] data = new byte[1024];
            byte[] length = new byte[4];

            while (true) {
                length[0] = 0;
                length[1] = 0;
                length[2] = 0;
                length[3] = 0;

                if (socket.isClosed()) {
                    return;
                }

                is.read(length);
                ByteBuffer len = ByteBuffer.wrap(length);
                len.position(0);
                int pref = len.getShort();
                int size = len.getShort();
                System.out.println("size: " + size);

                if (size <= 0 || pref != 122) {
                    continue;
                }

                System.out.println("message receieved: " + size);

                is.read(data, 0, Math.min(size, 1023));
                input.add(data);

                for (int i = 0; i < data.length; i++) {
                    System.out.print(data[i] + " ");
                }
                System.out.println();

                synchronized (toInturrupt) {
                    if (!toInturrupt.isEmpty()) {
                        Iterator<Object> iter = toInturrupt.iterator();
                        while (iter.hasNext()) {
                            Object t = iter.next();
                            synchronized (t) {
                                if (t != null) {
                                    t.notify();
                                } else {
                                    iter.remove();
                                }
                            }
                        }
                    }
                }

                len.position(0);
            }
        } catch (SocketException e) {
            System.err.println("Lost connection with " + socket.getInetAddress().getHostName() + ".");
            return;
        }
    }

    private void nrunOut(Socket socket) throws IOException {
        try {
            while (true) {
                if (socket.isClosed()) {
                    System.err.println("Socket closed.");
                    return;
                }

                while (!output.isEmpty()) {
                    System.out.println("asdf");
                    byte[] data = output.remove();
                    System.out.println(data.length);

                    ByteBuffer sb = ByteBuffer.allocate(2 * 2);
                    sb.putShort((short) 10024);
                    sb.putShort((short) data.length);

                    os.write(sb.array());
                    os.write(data);

                    os.flush();
                }
            }
        } catch (SocketException e) {
            System.err.println("Lost connection with " + socket.getInetAddress().getHostName() + ".");
            return;
        }
    }

    private void runOut(Socket socket) {
        try {
            while (true) {
                if (socket.isClosed()) {
                    System.err.println("Socket closed.");
                    return;
                }

                while (!output.isEmpty()) {
                    byte[] data = output.poll();
                    byte[] size = new byte[2];
                    ByteBuffer bb = ByteBuffer.wrap(size);

                    bb.putShort((short) data.length);

                    os.write(size, 0, size.length);
                    os.write(data, 0, data.length);

                    os.flush();
                }
            }
        } catch (IOException e) {
            System.err.println("Lost connection with " + socket.getInetAddress().getHostName() + ".");
            return;
        }
    }

    private void runIn(Socket socket) {
        try {
            while (true) {
                if (socket.isClosed()) {
                    System.err.println("Socket closed.");
                    return;
                }

                byte[] size = new byte[2];
                is.read(size, 0, 2);
                ByteBuffer bb = ByteBuffer.wrap(size);
                short len = bb.getShort();
                byte[] msg = new byte[len];
                is.read(msg, 0, msg.length);
                input.add(msg);

                synchronized (toInturrupt) {
                    if (!toInturrupt.isEmpty()) {
                        Iterator<Object> iter = toInturrupt.iterator();
                        while (iter.hasNext()) {
                            Object t = iter.next();
                            synchronized (t) {
                                if (t != null) {
                                    t.notify();
                                } else {
                                    iter.remove();
                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Lost connection with " + socket.getInetAddress().getHostName() + ".");
            return;
        }
    }

    public boolean isRunning() {
        if (!inHandle.isAlive() || !outHandle.isAlive()) {
            inHandle.stop();
            outHandle.stop();

            return false;
        }
        return true;
    }

    public void disconnect() {
        if (inHandle.isAlive()) {
            inHandle.stop();
        }
        if (outHandle.isAlive()) {
            outHandle.stop();
        }
    }

    public void send(byte[] msg) {
        output.add(msg);
    }

    public boolean hasNext() {
        return !input.isEmpty();
    }

    public byte[] next() {
        return input.poll();
    }

    public void notifyOnReceive(Object t) {
        synchronized (toInturrupt) {
            toInturrupt.add(t);
        }
    }

//    public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
//        final Connection c = new Connection();
//        c.start("192.168.1.199", 1225);
//
//        final Scanner scan = new Scanner(System.in);
//
//        (new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (true) {
//                    c.send(scan.nextLine().getBytes());
//                }
//            }
//        })).start();
//
//        byte[] next;
//        while (true) {
//            next = c.next();
//            if (next != null) {
//                System.out.println(new String(next));
//            }
//        }
//    }
}
