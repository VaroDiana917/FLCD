package domain;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Scanner;

public class MyScanner {
    SymbolTable st = new SymbolTable();

    ArrayList<AbstractMap.SimpleEntry<String, Integer>> pif;
    Specification specs = new Specification();

    public MyScanner(String programFileName) {
        scan(programFileName);
    }

    public void scan(String programFileName) {
        ArrayList<AbstractMap.SimpleEntry<String, Integer>> tokenPairs = new ArrayList<>();
        try {
            File file = new File(programFileName);
            Scanner reader = new Scanner(file);

            int lineNr = 1;

            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                ArrayList<String> tokens = tokenize(line);

                for (String token : tokens) {
                    tokenPairs.add(new AbstractMap.SimpleEntry<>(token, lineNr));
                }

                ++lineNr;
            }

            reader.close();

            //build PIF table

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> tokenize(String line) {
        ArrayList<String> tokens = new ArrayList<>();

        for (int i = 0; i < line.length(); ++i) {
            if (specs.isASeparator(String.valueOf(line.charAt(i))) && !(String.valueOf(line.charAt(i))).equals(" ")) {
                tokens.add(String.valueOf(line.charAt(i)));
            } else if (line.charAt(i) == '\"') {
                //Read the string

            } else if (line.charAt(i) == '\'') {
                //Read the char

            } else if (specs.isAnOperator(String.valueOf(line.charAt(i)))) {
                //Read the operator

            } else if (line.charAt(i) != ' ') {
                //Anything else
            }
        }
        return tokens;
    }
}
