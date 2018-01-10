package Receiver;

import java.io.ByteArrayInputStream;
import java.nio.ByteBuffer;
import java.util.zip.Adler32;

// 1. The wrapper
public class ReceiverFSM2ndtry {
    // 2. States array
    private State[] states = {new A(), new B(), new C()};

    // 3. Current state
    private int currentState = 0;

    // 4. Delegation and pass the this pointer
    public void on() {
        states[currentState].on(this);
    }

    public void off() {
        states[currentState].off(this);
    }

    public void ack() {
        states[currentState].ack(this);
    }

    public void changeState(int index) {
        currentState = index;
    }


}

// 5. The State base class
abstract class State {
    static Adler32 checker = new Adler32();
    static ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);

    private static void setAck(acks ack, byte[] packet, byte[] anotherPacket) {
        if (ack == acks.ACK) {
            packet[1] = 1;
            anotherPacket[1] = 1;
        } else {
            packet[1] = 0;
            anotherPacket[1] = 0;
        }
    }

    private static void setSeq(boolean seq, byte[] packet, byte[] anotherPacket) {
        if (seq) {
            packet[0] = 1;
            anotherPacket[0] = 1;
        } else {
            packet[0] = 0;
            anotherPacket[0] = 0;
        }
    }

    // 6. Default behavior
    public boolean[] confirmSumSeq(byte[] rdtPaket) {
        boolean[] checkStatus = new boolean[2];
        //confirmSum
        ByteArrayInputStream bais = new ByteArrayInputStream(rdtPaket);
        byte[] checksumA = new byte[8];

        bais.read(checksumA, 0, 8);
        buffer.put(checksumA, 0, checksumA.length);
        buffer.flip();//need flip
        long sent = buffer.getLong();

        checker.update(rdtPaket, 10, 1400);
        long calc = checker.getValue();

        checkStatus[0] = sent == calc ? true : false;

        //Confirm Seqnum
        byte[] seqnum = new byte[1];
        bais.read(seqnum, 8, 1);

        checkStatus[1] = seqnum[0] == 1 ? true : false;

        return checkStatus;
    }

    //Antwort Package erstellen mit (ACK/NACK,Seq)
    byte[] makeP(acks ack, boolean seq) {
        byte[] packet = new byte[10];
        byte[] answer = new byte[2];
        //Structure is [Seq|Ack|Checksum]
        setSeq(seq, packet, answer);
        setAck(ack, packet, answer);
        //Checksum
        checker.update(answer);
        long checksum = checker.getValue();
        byte[] checksumBytes = longToByteArray(checksum);
        //write checksum to answerPacket
        //TODO Bztearrazoutputstream für Schleife einfügen
        for (int i = 0; i < 8; i++) {
            answer[2 + i] = checksumBytes[0];
        }
        return answer;
    }

    private byte[] longToByteArray(long l) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(0, l);
        return buffer.array();
    }

    //TODO Methode zum weterleiten der Pakete (udt/UDP) // sendP
//TODO Methode zur Übergabe an FileWriter
    public void on(ReceiverFSM2ndtry receiverFsm2ndtry) {
        System.out.println("error");
    }

    public void off(ReceiverFSM2ndtry receiverFsm2ndtry) {
        System.out.println("error");
    }

    public void ack(ReceiverFSM2ndtry receiverFsm2ndtry) {
        System.out.println("error");
    }

    enum acks {ACK, NACK}
}

class A extends State {
    public void on(ReceiverFSM2ndtry receiverFsm2ndtry) {
        System.out.println("A + on  = C");
        receiverFsm2ndtry.changeState(2);
    }

    public void off(ReceiverFSM2ndtry receiverFsm2ndtry) {
        System.out.println("A + off = B");
        receiverFsm2ndtry.changeState(1);
    }

    public void ack(ReceiverFSM2ndtry receiverFsm2ndtry) {
        System.out.println("A + ack = A");
        receiverFsm2ndtry.changeState(0);
    }
}

class B extends State {
    public void on(ReceiverFSM2ndtry receiverFsm2ndtry) {
        System.out.println("B + on  = A");
        receiverFsm2ndtry.changeState(0);
    }

    public void off(ReceiverFSM2ndtry receiverFsm2ndtry) {
        System.out.println("B + off = C");
        receiverFsm2ndtry.changeState(2);
    }
}

// 7. Only override some messages
class C extends State {
    // 8. "call back to" the wrapper class
    public void on(ReceiverFSM2ndtry receiverFsm2ndtry) {
        System.out.println("C + on  = B");
        receiverFsm2ndtry.changeState(1);
    }
}

public class StateDemo {
    public static void main(String[] args) {
        ReceiverFSM2ndtry receiverFsm2ndtry = new ReceiverFSM2ndtry();
        int[] msgs = {2, 1, 2, 1, 0, 2, 0, 0};
        for (int msg : msgs) {
            if (msg == 0) {
                receiverFsm2ndtry.on();
            } else if (msg == 1) {
                receiverFsm2ndtry.off();
            } else if (msg == 2) {
                receiverFsm2ndtry.ack();
            }
        }
    }
}
