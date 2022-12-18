import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main2 {
    public static void main(String[] args) {
        try {
            Grammar grammar = new Grammar("src/Files/g2.txt");


            RecursiveDescendant algorithm = new RecursiveDescendant(grammar);
            List<String> seq = sequenceFromPIF("src/Files/PIF.out");
            System.out.println("Sequence: " + seq);

            List<String> productionString = algorithm.run(seq);
            ParserOutput parserOutput = new ParserOutput(grammar);
            parserOutput.addProductionString(productionString);

            System.out.println(parserOutput);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static List<String> sequenceFromPIF(String pifFileName) throws FileNotFoundException {
        Scanner scanner = new Scanner(new BufferedReader(new FileReader(pifFileName)));
        List<String> seq = new ArrayList<>();
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            seq.add(line.substring(line.indexOf('(') + 1, line.indexOf('$')));
        }
        return seq;
    }


}

