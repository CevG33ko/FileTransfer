package Receiver;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UDPReceive implements Runnable {

    private static final int SIZE = 1410;
    public final int PORT = 6661;
    public byte[] buffer;

    // TODO UDP Chanel öffnen
    //TODO Paket Empfangen
    //TODO an Main/ReceiverMain weitergeben und rcvPkt auslösen

    @Override
    public void run() {
        InetAddress clientAdress;
        int clientPort;
        buffer = new byte[SIZE];
        try {
            DatagramSocket socket = new DatagramSocket(PORT);
            DatagramPacket dgmPTemp = new DatagramPacket(buffer, buffer.length);
            awaitFirstPacket(socket, dgmPTemp);


        } catch (SocketException socketE) {
            System.out.println("Problem Opening Socket");
        } catch (IOException ioE) {
            System.out.println("Problem Reading Package");
        }

        //TODO Antwort
        //TODO Pakete nach erstem Paket entgegennehmen bis Timeout (2 minuten wie bei TCP)

    }

    private void awaitFirstPacket(DatagramSocket socket, DatagramPacket dgmPTemp) throws IOException {
        int clientPort;
        socket.receive(dgmPTemp);
        ReceiverMain.fsm(dgmPTemp.getData());
        //TODO Paket überprüfen und an DatenStrom weitergeben

        //TODO Antwortadresse für alle eingeheden Verbindungen(funktioniert gerade nur für erste Verbindung)
        //TODO Moegliche Lösung wäre Verbindungspacka und neuer Thread für die einzelne Verbindung
        clientAdress = dgmPTemp.getAddress();
        clientPort = dgmPTemp.getPort();
    }

    public void
}
