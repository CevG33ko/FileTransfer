package Receiver;

import javax.sound.sampled.Port;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UDPReceive implements Runnable {

    private static final int SIZE = 1410;
    public final int PORT = 6661;
    public byte[] buffer;
    static DatagramSocket socket;
    static DatagramPacket dgmPTemp;
    static boolean firstPaket = true;
    InetAddress clientAdress;
    int clientPort;


    //TODO an Main/ReceiverFSM weitergeben und rcvPkt auslösen
    // TODO rcvPaket als MEthode aufrufen Exceptions in main abfangen return=dgmP.getDAata
    //ales soll von dieser Methode ausgehen


    @Override
    public void run() {

        buffer = new byte[SIZE];
        try {
            socket = new DatagramSocket(PORT);
            dgmPTemp = new DatagramPacket(buffer, buffer.length);
            //awaitFirstPacket();


        } catch (SocketException socketE) {
            System.out.println("Problem Opening Socket");
        }

        //TODO Antwort
        //TODO Pakete nach erstem Paket entgegennehmen bis Timeout (2 minuten wie bei TCP)

    }

    //Wird von Oberer Statemachine aufgerufen
    // war mal receiveFirstPacket
    public boolean receiveP(byte[] buffer) {

        try {
            //TODO was wenn neue Adresse?
            socket.receive(dgmPTemp);
            clientAdress = dgmPTemp.getAddress();
            clientPort = dgmPTemp.getPort();
            buffer = dgmPTemp.getData();
            return true;
        } catch (IOException e) {
            return false;
        }

        //  in rcvPaket verlegt ReceiverMain.fsm(dgmPTemp.getData());
        // Antwortadresse für alle eingeheden Verbindungen(funktioniert gerade nur für erste Verbindung)
        // Moegliche Lösung wäre Verbindungspacka und neuer Thread für die einzelne Verbindung
        //TODO  an DatenStrom weitergeben ReceiverFSM
    }

    public void sendPacket(byte[] abpPacket) throws IOException {
        dgmPTemp = new DatagramPacket(abpPacket, abpPacket.length, clientAdress, PORT);
        socket.send(dgmPTemp);
        System.out.println("SENDER: Paket gesendet.");
    }

    /*
    private byte [] receiveP () throws IOException{
        // hier returnen oder in awaitmethode?
        return firstPaket ? awaitFirstPacket(): followingPakets();


    }



    public byte [] followingPakets() throws  IOException{
        socket.receive(dgmPTemp);

    }*/
}
