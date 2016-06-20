package noisyChannel.theandriicherniak.gmail.com;

/**
 * Created by andriicherniak on 6/16/16.
 */
public class NorvigProcessor implements DataProcessor {
    NosyChannelModel noisyModel;
    public NorvigProcessor(NosyChannelModel model){
        noisyModel = model;
    }
    public void processLine(String line){
        String [] ar = line.trim().split(":");
        String [] ar2 = ar[1].replace(" ", "").split(",");

        for (String v: ar2){
            noisyModel.processInput(ar[0], v);
        }
    }
}
