package domain;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;


public class MyScanner {
    private final SymbolTable identifiers = new SymbolTable();
    private final SymbolTable constants = new SymbolTable();

    private final MyPIF pif = new MyPIF();
    private final Specification langSpecs = new Specification();

    private final String path;

    public MyScanner(String path) {
        this.path = path;
    }

    public void scan() {
        try {
            File file = new File(this.path);
            Scanner reader = new Scanner(file);

            int lineNr = 1;
            boolean isOk = true;

            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                List<String> tokens = tokenize(line);

                for (String token : tokens) {
                    if (langSpecs.isAReservedWord(token) || langSpecs.isAnOperator(token) || langSpecs.isASeparator(token)) {
                        pif.addReservedWord(token);
                    } else if (langSpecs.isIdentifier(token)) {
                        identifiers.addInST(token);
                        AbstractMap.SimpleEntry<Integer, Integer> pos = identifiers.getPosition(token);
                        pif.addIdentifier(pos);

                    } else if (langSpecs.isConstant(token)) {
                        constants.addInST(token);
                        AbstractMap.SimpleEntry<Integer, Integer> pos = constants.getPosition(token);
                        pif.addConstant(pos);
                    } else {
                        isOk = false;
                        System.out.println("ERROR in " + path + "! Line: " + lineNr + "; token: " + token);
                    }
                }

                ++lineNr;
            }

            reader.close();
            if(isOk){
                System.out.println("Everything is ok with "+path+"\n"+pif+"Identifier ST:\n"+identifiers+"\n\nConstants ST:\n"+constants);
                FileWriter myWriter = new FileWriter("src/out/PIF.out");
                myWriter.write(""+pif);
                myWriter.close();

                FileWriter myWriter2 = new FileWriter("src/out/ST.out");
                myWriter2.write("Identifier ST:\n"+identifiers+"\n\nConstants ST:\n"+constants);
                myWriter2.close();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<String> tokenize(String line) {
        ArrayList<String> tokens = new ArrayList<>();

        String regex = String.join("|", langSpecs.getOperatorsForRegex());
        regex = regex.concat("|" + String.join("|", langSpecs.getSeparatorsForRegex()));
        regex = regex.concat("|" + String.join("|", langSpecs.getReservedWords()));

        regex = "((?=(" + regex + "))|(?<=(" + regex + ")))";
        String[] result = line.split(regex);

        for (int i = 0; i < result.length; i++) {
            if (i + 1 < result.length) {
                if (Objects.equals(result[i], ":") || Objects.equals(result[i], "!") || Objects.equals(result[i], "<") || Objects.equals(result[i], ">")) {
                    if (Objects.equals(result[i + 1], "=")) {
                        tokens.add(result[i] + result[i + 1]);
                        i = i + 1;
                    } else tokens.add(result[i]);
                } else if (i - 1 >= 0) {
                    if (langSpecs.isAnOperator(result[i - 1])) {
                        if (Objects.equals(result[i], "+") || Objects.equals(result[i], "-")) {
                            if (langSpecs.isANumber(result[i + 1])) {
                                tokens.add(result[i] + result[i + 1]);
                                i = i + 1;
                            } else tokens.add(result[i]);
                        } else tokens.add(result[i]);
                    } else tokens.add(result[i]);
                } else tokens.add(result[i]);
            } else tokens.add(result[i]);
        }
        return tokens;
    }
}