package Receiver;

import java.io.IOException;

public class Main {
    private byte[] abpBuffer;
    //Kontrollierende Klasse
    public void main(String[] args) {
        UDPReceive udt = new UDPReceive();
        udt.run();
        ReceiverFSM fsm = new ReceiverFSM();
        fsm.run();
        ToData writer = new ToData();
        writer.run();
        while (true) {
            try {
                abpBuffer = udt.receiveP();
                fsm.rcvP(abpBuffer);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}
