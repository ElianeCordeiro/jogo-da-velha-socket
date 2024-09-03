package jogoDaVelhaSocket.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class Receiver extends Thread {
	
    private final DatagramSocket socket;
    private final Queue<Pacote> queue;

    public Receiver(DatagramSocket socket) {
        this.socket = socket;
        this.queue = new ArrayBlockingQueue<>(10);

        this.start();
    }

    public synchronized Pacote readMessage() {
        return this.queue.poll();
    }

    @Override
    public void run() {
        try {
            while (true) {
                byte[] buffer = new byte[1024];

                DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
                this.socket.receive(receivePacket);

                ByteArrayInputStream byteInStream = new ByteArrayInputStream(buffer);
                ObjectInputStream objectInStream = new ObjectInputStream(byteInStream);
                Messagem msg = (Messagem) objectInStream.readObject();

                this.queue.offer(new Pacote(receivePacket.getAddress(), receivePacket.getPort(), msg));
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}