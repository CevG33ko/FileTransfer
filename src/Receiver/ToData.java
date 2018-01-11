package Receiver;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ToData {
    FileOutputStream out;
    File file;

    public ToData(int count, byte[] fileStart) {
        generateFilename(count);
        try (FileOutputStream fos = new FileOutputStream(file.getPath())) {
            fos.write(fileStart);
            fos.close();
        } catch (IOException ioE) {
            System.out.println("Error Writing new file (First Chunk)");
        }
        openstream();
    }

    public void openstream() {
        try {
            out = new FileOutputStream(file, true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void write(byte[] chunk) throws IOException {
        out.write(chunk);
        out.flush();
    }

    public void close() throws IOException {
        out.close();
    }

    private String generateFilename(int count) {
        return "E:\\test\\file" + count + ".txt";
    }
}
