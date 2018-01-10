package Sender;

import java.io.ByteArrayInputStream;
import java.nio.ByteBuffer;
import java.util.zip.Adler32;

// 1. The "wrapper"
public class SenderFSM2ndTry {


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

    private byte[] makeP(acks ack, boolean seq, byte[] data) {
        //TODO hier gibt es keinen Ack, aber byte könnte als Flag benutzt werden
        byte[] answer = new byte[2];
        byte[] packet = new byte[data.length + 10];
        //TODO checksum verbessern(muss seq,ack  und data beinhalten oder?)
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

    public enum acks {ACK, NACK}

}

class A extends State {
    public void on(SenderFSM2ndTry fsm) {
        System.out.println("A + on  = C");
        fsm.changeState(2);
    }

    public void off(SenderFSM2ndTry fsm) {
        System.out.println("A + off = B");
        fsm.changeState(1);
    }

    public void ack(SenderFSM2ndTry fsm) {
        System.out.println("A + ack = A");
        fsm.changeState(0);
    }
}

class B extends State {
    public void on(SenderFSM2ndTry fsm) {
        System.out.println("B + on  = A");
        fsm.changeState(0);
    }

    public void off(SenderFSM2ndTry fsm) {
        System.out.println("B + off = C");
        fsm.changeState(2);
    }
}

// 7. Only override some messages
class C extends State {
    // 8. "call back to" the wrapper class
    public void on(SenderFSM2ndTry fsm) {
        System.out.println("C + on  = B");
        fsm.changeState(1);
    }
}

public class StateDemo {
    public static void main(String[] args) {
        SenderFSM2ndTry fsm = new SenderFSM2ndTry();
        int[] msgs = {2, 1, 2, 1, 0, 2, 0, 0};
        for (int msg : msgs) {
            if (msg == 0) {
                fsm.on();
            } else if (msg == 1) {
                fsm.off();
            } else if (msg == 2) {
                fsm.ack();
            }
        }
    }
}
}
