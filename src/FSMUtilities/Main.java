package FSMUtilities;
//TODO Funktionen Testen
import java.io.ByteArrayInputStream;
import java.nio.ByteBuffer;
import java.util.zip.Adler32;

public class Main {
    Adler32 checker = new Adler32();
    ;
    ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);

    public static void main(String[] args) {
        // write your code here
        //Bitte nicht das ist nur der Standard Text xD
    }

    //Wraps Data in ABP Package
    //8 Bytes are added for checksum + 2 for Ack and Sequence Number
    public byte[] makeP(acks ack, boolean seq, byte[] data) {


        byte[] packet = new byte[data.length + 10];
        //checksum
        //Adler32 checker = new Adler32();
        checker.update(data);
        long checksum = checker.getValue();
        //long to byteArray
        //ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(0, checksum);
        byte[] checksumBytes = buffer.array();

        // Wrap data in reliable data transfer packet
        setAck(ack, packet);
            /* Warum geht die scheiße als if aber nciht so? XO
        ack == acks.ACK ?  packet[8]=1: packet[8]=0;
            */
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

    public boolean confirmSum(byte[] rdtPaket) {
        ByteArrayInputStream bais = new ByteArrayInputStream(rdtPaket);
        byte[] checksumA = new byte[8];

        bais.read(checksumA, 0, 8);
        buffer.put(checksumA, 0, checksumA.length);
        buffer.flip();//need flip
        long sent = buffer.getLong();

        checker.update(rdtPaket, 10, 1400);
        long calc = checker.getValue();


        return sent == calc ? true : false;
    }

    //isAck
    public boolean seqE1(byte seq) {
        return seq == 1 ? true : false;
    }

    public boolean seqE0(byte seq) {
        return seq == 0 ? true : false;
    }

    private void setAck(acks ack, byte[] packet) {
        if (ack == acks.ACK)
            packet[8] = 1;
        else
            packet[8] = 0;
    }

    //enums
    enum acks {
        ACK, NACK
    }

}