import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Grammar grammar = new Grammar("src/Files/g1.txt");

//        System.out.println(grammar.printNonTerminals());
//        System.out.println(grammar.printTerminals());
//        System.out.println(grammar.printProductions());
//        System.out.println(grammar.printProductionsForNonTerminal("S,A"));
//        System.out.println(grammar.printProductionsForNonTerminal("factor"));
//        System.out.println(grammar.printProductionsForNonTerminal("B"));
//        System.out.println(grammar.printProductionsForNonTerminal("C"));
//        System.out.println(grammar.checkIfCFG());


        RecursiveDescendant algorithm = new RecursiveDescendant(grammar);
        List<String> seq = sequenceFromFile("src/Files/seq.txt");
        System.out.println("Sequence: " + seq);

        List<String> productionString = algorithm.run(seq);
        ParserOutput parserOutput = new ParserOutput(grammar);
        parserOutput.addProductionString(productionString);

        System.out.println(parserOutput);

        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("src/Files/out1.txt"));
        bufferedWriter.write(parserOutput.toString());
        bufferedWriter.close();


    }

    public static List<String> sequenceFromFile(String fileName) throws FileNotFoundException {
        Scanner scanner = new Scanner(new BufferedReader(new FileReader(fileName)));
        return Arrays.asList(scanner.nextLine().split(" "));
    }
}