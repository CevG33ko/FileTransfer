package Sender;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FromData {

    //TODO Als Input Argument bei Programmstart

    private static final String FILENAME = "E:\\test\\filename.txt";

    public static void main(String[] args) {

        File file = new File(FILENAME);
        try (FileInputStream fin = new FileInputStream(file)) {

            byte fileContent[] = new byte[(int) file.length()];

        } catch (FileNotFoundException e) {
            System.out.println("File not found" + e);
        } catch (IOException ioe) {
            System.out.println("Exception while reading file" + ioe);
        }
    }
}
