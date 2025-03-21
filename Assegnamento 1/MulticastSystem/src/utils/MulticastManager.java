// Cosa fa questa classe?
// Inizializza una connessione multicast con un indirizzo e una porta.
// Invia e riceve messaggi usando DatagramPacket e MulticastSocket.
// Gestisce la serializzazione per inviare oggetti Message.
// Si unisce e lascia il gruppo multicast per garantire la corretta comunicazione.

package utils;

import java.io.*;
import java.net.*;

public class MulticastManager {
    private final int port;
    private final MulticastSocket socket;
    private final InetAddress group;
    private final NetworkInterface networkInterface;

    public MulticastManager(String multicastAddress, int port) throws IOException {
        this.port = port;
        this.socket = new MulticastSocket(port);
        this.group = InetAddress.getByName(multicastAddress);
        this.networkInterface = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());

        SocketAddress groupAddress = new InetSocketAddress(group, port);
        socket.joinGroup(groupAddress, networkInterface);
    }

    public void sendMessage(Message message) throws IOException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        ObjectOutputStream objStream = new ObjectOutputStream(byteStream);
        objStream.writeObject(message);
        objStream.flush();

        byte[] buffer = byteStream.toByteArray();
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group, port);
        socket.send(packet);
    }

    public Message receiveMessage() throws IOException, ClassNotFoundException {
        byte[] buffer = new byte[1024];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        socket.receive(packet);

        ByteArrayInputStream byteStream = new ByteArrayInputStream(packet.getData());
        ObjectInputStream objStream = new ObjectInputStream(byteStream);
        return (Message) objStream.readObject();
    }

    public void close() throws IOException {
        SocketAddress groupAddress = new InetSocketAddress(group, port);
        socket.leaveGroup(groupAddress, networkInterface);
        socket.close();
    }
}
