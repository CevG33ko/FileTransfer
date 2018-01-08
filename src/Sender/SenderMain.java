package Sender;

import FSMUtilities.Methods;
// ABP Receiver FSM Implementation
public class SenderMain {
    static byte[] sndP;

    public static enum state {
        WAIT_FOR_CALL_ONE, WAIT_FOR_CALL_ZERO, WAIT_FOR_ACK_0, WAIT_FOR_ACK_1
    }

    //TODO checksum/integrity Check aus FSM Utilities
    //TODO Check SeqNumber aus FSM Utilities
    //TODO weitergeben an ToData

    // ABP Sender FSM Implementation
    public class SenderReceiverMain implements Runnable {

        state currentState = state.WAIT_FOR_CALL_ZERO;
        byte[] paketBuffer;

        @Override
        public void run() {

            public void stateMachine ( byte[] data){
                boolean receivedCoherentPackage = false;
                while (!receivedCoherentPackage) {
                    boolean[] sumSeq = Methods.confirmSumSeq(rcvP);
                    boolean sum = sumSeq[0];
                    boolean seq = sumSeq[1];
                    switch (currentState) {

                        case WAIT_FOR_CALL_ONE:
                            sndP = Methods.makeP(0, data, )
                            break;

                        case WAIT_FOR_CALL_ZERO:


                            break;
                        case WAIT_FOR_ACK_0:
                            if (!sum)
                                break;
                            if (sum && seq)
                                break;
                            if (sum && !seq)
                                break;
                            break;
                        case WAIT_FOR_ACK_1:
                            if (!sum)
                                break;
                            if (sum && !seq)
                                break;
                            if (sum && seq)
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

        //TODO Zustandsmaschine implementieren
}
