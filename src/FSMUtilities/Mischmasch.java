package FSMUtilities;

import java.io.IOException;
import java.net.*;

public class Mischmasch implements Runnable {


    public static final int PORT = 9991;
    private static final int PackageSIZE = 1400;
    byte[] paketBuffer;
    byte[] dataBuffer = new byte[1409];
    private long timerStart;
    private long timerStop;
    private int recievedPacketCount; //server
    private int lostPackageCount;
    private int timeout;
    private State currentState;

    private void setTimeout() {
        //TODO Schätzung RTT
        //TODO Schätzung mit RTT Messung und RFC 2988
    }

    public void stateMachine(byte[] data) {
        boolean receivedCoherentPackage = false;
        while (!receivedCoherentPackage) {

            switch (currentState) {

                case WAIT_FOR_ONE:
                    //TODO 3 Möglichkeiten des Paketzustands mit Abfrage und Reaktion implementieren (ReceiverFSM2ndtry)
                    break;

                case WAIT_FOR_ZERO:
                    //TODO 3 Möglichkeiten des Paketzustands mit Abfrage und Reaktion implementieren (ReceiverFSM2ndtry)
                    break;
            }
        }
    }

    //receivePacket wird durch empfangen eines Pakets von Socket ausgeführt (Statemachine)

    public void run() {
        try {
            DatagramSocket s = new DatagramSocket(PORT);
            timerStart = System.currentTimeMillis() / 1000;

            while (true) {

                s.setSoTimeout(10000);
                DatagramPacket pReceive = new DatagramPacket(paketBuffer, paketBuffer.length);

                s.receive(pReceive);
                dataBuffer = pReceive.getData();
                stateMachine(dataBuffer);
                //TODO irgendwo counten

            }


        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketTimeoutException e) {
            timerStop = System.currentTimeMillis() / 1000;
            long timer = timerStop - timerStart;
            // Durchsatz berechnen
            long goodput;
            goodput = (recievedPacketCount * 1409 * 8) / 1024 / timer;
            System.out.println("Server: Die Empfangsrate ist: " + goodput + " kbit/s.");
            System.out.println("Server: Senderate: " + (((recievedPacketCount + lostPackageCount) * dataBuffer.length * 8) / 1024) / timer + " kbit/s");
            System.out.println("Server: Keine Pakete mehr. \nTimer abgelaufen. \nEmpfangene Pakete:  " + recievedPacketCount);
//          System.out.println("Gesendete Pakete: " + (packetCount + lost) + ", \nVerlorene Pakete: " + lost );
            s.close();
        } catch (SocketException e) {
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // all states of this ReceiverFSM2ndtry

}
//TODO? Soll die Datei enden? Soll neue Datei woanders anfangen?


