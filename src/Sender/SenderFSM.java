package Sender;

import FSMUtilities.Methods;

// ABP Receiver ReceiverFSM2ndtry Implementation
public class SenderFSM {
    static byte[] sndP;

    public static enum state {
        WAIT_FOR_CALL_ONE, WAIT_FOR_CALL_ZERO, WAIT_FOR_ACK_0, WAIT_FOR_ACK_1
    }

    //TODO checksum/integrity Check aus ReceiverFSM2ndtry Utilities
    //TODO Check SeqNumber aus ReceiverFSM2ndtry Utilities
    //TODO weitergeben an ToData

    // ABP Sender ReceiverFSM2ndtry Implementation
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

            //TODO checksum/integrity Check aus ReceiverFSM2ndtry Utilities
            //TODO Check SeqNumber aus ReceiverFSM2ndtry Utilities

            //TODO weitergeben an ToData

            //TODO Zustandsmaschine implementieren
        }

        //TODO Zustandsmaschine implementieren
        private byte[] makeP(SharedMethods.acks ack, boolean seq, byte[] data) {

            byte[] packet = new byte[data.length + 10];
            //checksum
            checker.update(data);
            long checksum = checker.getValue();
            byte[] checksumBytes = longToByteArray(checksum);
            // Wrap data in reliable data transfer packet
            setAck(ack, packet);

            for (int i = 0; i < 8; i++) {
                if (i < 8)
                    packet[i] = checksumBytes[i];
                if (i == 8)
                    setAck(ack, packet);
                else
                    packet[i] = data[i + 8];
            }
            return packet;
        }
}
