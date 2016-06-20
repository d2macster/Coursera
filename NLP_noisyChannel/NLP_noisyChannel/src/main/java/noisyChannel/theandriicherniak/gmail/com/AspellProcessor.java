package noisyChannel.theandriicherniak.gmail.com;

/**
 * Created by andriicherniak on 6/16/16.
 */
public class AspellProcessor implements DataProcessor {
    NosyChannelModel noisyModel;

    public AspellProcessor(NosyChannelModel model){
        noisyModel = model;
    }

    public void processLine(String line){
        String[] ar = line.trim().replaceAll("\\s+", " ").split(" ");

        noisyModel.processInput(ar[1], ar[0]);
    }
}
