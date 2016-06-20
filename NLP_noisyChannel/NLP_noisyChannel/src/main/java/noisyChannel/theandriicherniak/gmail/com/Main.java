package noisyChannel.theandriicherniak.gmail.com;

/**
 * Created by andriicherniak on 6/16/16.
 */
public class Main {
    public static void main(String [] args){
        int patternL = 3;

        NosyChannelModel model = new NosyChannelModel(patternL);

        String dataPrefix = "/Users/andriicherniak/Desktop/noisyChannelData/";
        NorvigProcessor p1 = new NorvigProcessor(model);
        AspellProcessor p2 = new AspellProcessor(model);

        DataReader.readData(dataPrefix + "spell-errors.txt", p1);
        DataReader.readData(dataPrefix + "aspell.txt", p2);

        PhraseProcessor phrase = new PhraseProcessor(5, patternL, model);

        DataReader.readData(dataPrefix + "input.txt", phrase);

    }
}
