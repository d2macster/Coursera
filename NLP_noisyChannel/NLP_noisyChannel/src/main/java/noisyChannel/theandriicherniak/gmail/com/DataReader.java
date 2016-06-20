package noisyChannel.theandriicherniak.gmail.com;

import java.io.*;

/**
 * Created by andriicherniak on 6/16/16.
 */
public class DataReader {
    public static void readData(String input, DataProcessor processor){
        try {
            FileReader fileReader = new FileReader(new File(input));
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;

            while ((line = bufferedReader.readLine()) != null){
                processor.processLine(line);
            }
            fileReader.close();

        }catch (IOException e){
            System.out.println(e.getLocalizedMessage());
        }
    }
}
