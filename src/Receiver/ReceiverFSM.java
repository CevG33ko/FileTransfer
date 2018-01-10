package Receiver;


import java.io.ByteArrayInputStream;
import java.nio.ByteBuffer;
import java.util.zip.Adler32;

// ABP Sender ReceiverFSM2ndtry Implementation
public class ReceiverFSM implements Runnable {
    state currentState = state.WAIT_FOR_ZERO;
    byte[] paketBuffer;
    static byte[] sndP;

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
                        //TODO 3 Möglichkeiten des Paketzustands mit Abfrage und Reaktion implementieren (ReceiverFSM2ndtry)

                        if (!sum)
                            sndP = currentState.makeP()
                        if (sum && !seq)
                            break;
                        if (sum && seq)
                            break;


                        break;

                    case WAIT_FOR_ZERO:
                        //TODO 3 Möglichkeiten des Paketzustands mit Abfrage und Reaktion implementieren (ReceiverFSM2ndtry)
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

        //TODO checksum/integrity Check aus ReceiverFSM2ndtry Utilities
        //TODO Check SeqNumber aus ReceiverFSM2ndtry Utilities
        // TODO weitergeben an ToData
        //TODO Zustandsmaschine implementieren
        abstract class state {
            // Default behaviour
            static Adler32 checker = new Adler32();
            static ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);

            public static void main(String[] args) {
                // write your code here
                //Bitte nicht das ist nur der Standard Text xD
            }

            //Wraps Data in ABP Package
            //8 Bytes are added for checksum + 2 for Ack and Sequence Number


           /* boolean[] confirmSumSeq(int seqStart, byte[] rdtPaket) {
                boolean[] checkStatus = new boolean[2];
                //confirmSum
                ByteArrayInputStream bais = new ByteArrayInputStream(rdtPaket);
                byte[] checksumA = new byte[8];
                //Byte to Long
                bais.read(checksumA, seqStart-1, 8);
                buffer.put(checksumA, 0, checksumA.length);
                buffer.flip();//need flip
                long sent = buffer.getLong();
                //
                checker.update(rdtPaket, 10, 1400);
                long calc = checker.getValue();

                checkStatus[0] = sent == calc ? true : false;

                //Confirm Seqnum
                byte[] seqnum = new byte[1];
                bais.read(seqnum, 0, 1);

                checkStatus[1] = seqnum[0] == 1 ? true : false;

                return checkStatus;
            }*/


            //isAck
            public static boolean seqE1(byte seq) {
                return seq == 1 ? true : false;
            }

            public static boolean seqE0(byte seq) {
                return seq == 0 ? true : false;
            }

            private static void setAck(FSMUtilities.state.acks ack, byte[] packet) {
                if (ack == FSMUtilities.state.acks.ACK)
                    packet[8] = 1;
                else
                    packet[8] = 0;
            }

            //enums
            public enum acks {
                ACK, NACK
            }

        }
    }

