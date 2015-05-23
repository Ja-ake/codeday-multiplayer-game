package com.jakespringer.codeday.jake.networking;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Connection {

    private Thread inHandle, outHandle;

    private Socket socket;
    private InputStream is;
    private OutputStream os;

    private Queue<byte[]> input, output;
    private boolean hasOutput = false;

    public Connection() {
        input = new ConcurrentLinkedQueue<>();
        output = new ConcurrentLinkedQueue<>();
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
                try {
                    Connection.this.runIn(sock);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        inHandle.setDaemon(true);
        inHandle.start();

        outHandle = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Connection.this.runOut(sock);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        outHandle.setDaemon(true);
        outHandle.start();
    }

    private void runIn(Socket socket) throws IOException {
        ByteBuffer len = ByteBuffer.allocate(4);

        try {
            while (true) {
                if (socket.isClosed()) {
                    return;
                }

                is.read(len.array());
                int size = len.getInt();

                byte[] data = new byte[size];
                is.read(data, 0, size);
                input.add(data);

                len.position(0);
            }
        } catch (SocketException e) {
            System.err.println("Lost connection with " + socket.getInetAddress().getHostName() + ".");
        }
    }

    private void runOut(Socket socket) throws IOException {
        ByteBuffer len = ByteBuffer.allocate(4);

        try {
            while (true) {
                if (socket.isClosed()) {
                	System.err.println("Socket closed.");
                    return;
                }

                while (!output.isEmpty()) {                	
                    byte[] data = output.remove();
                    len.putInt(data.length);

                    os.write(len.array());
                    os.write(data);

                    os.flush();
                    len.position(0);
                }
            }
        } catch (SocketException e) {
            System.err.println("Lost connection with " + socket.getInetAddress().getHostName() + ".");
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

    public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
        final Connection c = new Connection();
        c.start("192.168.1.199", 1225);

        final Scanner scan = new Scanner(System.in);

        (new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) c.send(scan.nextLine().getBytes());
            }
        })).start();

        byte[] next;
        while (true) {
            next = c.next();
            if (next != null) {
                System.out.println(new String(next));
            }
        }
    }
}
