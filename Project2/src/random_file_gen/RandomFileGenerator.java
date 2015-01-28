package random_file_gen;

import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * User: jchitel
 * Date: 1/27/15
 * Time: 8:42 PM
 */
public class RandomFileGenerator {
    public static void main(String[] args) {
        try {
            FileOutputStream outputStream = new FileOutputStream("test/random.txt");
            byte[] randomChars = new byte[10000];
            for (int i = 0; i < 10000; ++i) {
                randomChars[i] = (byte)Math.floor(Math.random() * 127);
            }
            outputStream.write(randomChars);
            outputStream.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
