package Receiver;

import FSMUtilities.Methods;
// ABP Sender FSM Implementation
public class ReceiverMain implements Runnable {
    state currentState = state.WAIT_FOR_ZERO;
    byte[] paketBuffer;

    @Override
    public void run() {

        public void stateMachine ( byte[] rcvP){
            boolean receivedCoherentPackage = false;
            while (!receivedCoherentPackage) {
                boolean[] sumSeq = Methods.confirmSumSeq(rcvP);
                boolean sum = sumSeq[0];
                boolean seq = sumSeq[1];
                switch (currentState) {

                    case WAIT_FOR_ONE:
                        //TODO 3 Möglichkeiten des Paketzustands mit Abfrage und Reaktion implementieren (FSM)

                        if (!sum)
                            break;
                        if (sum && !seq)
                            break;
                        if (sum && seq)
                            break;


                        break;

                    case WAIT_FOR_ZERO:
                        //TODO 3 Möglichkeiten des Paketzustands mit Abfrage und Reaktion implementieren (FSM)
                        if (!sum)
                            break;
                        if (sum && seq)
                            break;
                        if (sum && !seq)
                            break;
                        break;
                }
            }
        }

    //TODO checksum/integrity Check aus FSM Utilities
    //TODO Check SeqNumber aus FSM Utilities

    //TODO weitergeben an ToData

    //TODO Zustandsmaschine implementieren
}

    public static enum state {
        WAIT_FOR_ONE, WAIT_FOR_ZERO
    }
