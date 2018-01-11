package Sender;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FromData {
    File file;
    byte[] buffer = new byte[1400];
    FileInputStream fin;

    public FromData(String Filename) {
        file = new File(Filename);
        openStream();
    }

    public static void main(String[] args) {

    }

    private void openStream() {
        try {
            fin = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            System.out.println("File not found" + e);
        } catch (IOException ioe) {
            System.out.println("Exception while reading file" + ioe);
        }
    }

    public byte[] getChunk() throws IOException {
        int count = fin.read(buffer);
        if (count > 0)
            return buffer;
        else {
            byte[] endConP = new byte[0];
            endConP[0] = 0;
            fin.close();
            return endConP;
        }


    }
}
