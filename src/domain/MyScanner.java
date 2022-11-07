package domain;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


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

                for (int i = 0; i<tokens.toArray().length; i++) {
                    if(i+2<tokens.toArray().length && i-2>=0){
                        if((Objects.equals(tokens.get(i - 2), "+") || Objects.equals(tokens.get(i - 2), "-") || Objects.equals(tokens.get(i - 2), "*") || Objects.equals(tokens.get(i - 2), "/") || Objects.equals(tokens.get(i - 2), "%")) &&
                                langSpecs.isOpenSeparator(tokens.get(i-1)) &&
                                (Objects.equals(tokens.get(i), "-") || Objects.equals(tokens.get(i), "+")) &&
                                (langSpecs.isANumber(tokens.get(i+1)) || langSpecs.isIdentifier(tokens.get(i+1))) &&
                                langSpecs.isClosedSeparator(tokens.get(i+2))
                        ){
                            if(langSpecs.isANumber(tokens.get(i+1))) {
                                constants.addInST(tokens.get(i)+tokens.get(i+1));
                                AbstractMap.SimpleEntry<Integer, Integer> pos = constants.getPosition(tokens.get(i)+tokens.get(i+1));
                                pif.addConstant(pos);
                            }
                            else if(langSpecs.isIdentifier(tokens.get(i+1))) {
                                identifiers.addInST(tokens.get(i)+tokens.get(i+1));
                                AbstractMap.SimpleEntry<Integer, Integer> pos = identifiers.getPosition(tokens.get(i)+tokens.get(i+1));
                                pif.addIdentifier(pos);
                            }
                            i=i+2;
                        }
                    }
                    if(i+1<tokens.toArray().length && i-1>=0){
                        if((Objects.equals(tokens.get(i - 1), "!=") || Objects.equals(tokens.get(i - 1), ":=") || Objects.equals(tokens.get(i - 1), "=") || Objects.equals(tokens.get(i - 1), ">") || Objects.equals(tokens.get(i - 1), "<") || Objects.equals(tokens.get(i - 1), ">=") || Objects.equals(tokens.get(i - 1), "<=")) &&
                                (Objects.equals(tokens.get(i), "-") || Objects.equals(tokens.get(i), "+")) &&
                                (langSpecs.isANumber(tokens.get(i+1)) || langSpecs.isIdentifier(tokens.get(i+1)))
                        ){
                            if(langSpecs.isANumber(tokens.get(i+1))) {
                                constants.addInST(tokens.get(i)+tokens.get(i+1));
                                AbstractMap.SimpleEntry<Integer, Integer> pos = constants.getPosition(tokens.get(i)+tokens.get(i+1));
                                pif.addConstant(pos);
                            }
                            else if(langSpecs.isIdentifier(tokens.get(i+1))) {
                                identifiers.addInST(tokens.get(i)+tokens.get(i+1));
                                AbstractMap.SimpleEntry<Integer, Integer> pos = identifiers.getPosition(tokens.get(i)+tokens.get(i+1));
                                pif.addIdentifier(pos);
                            }
                            i=i+2;
                        }
                        else if(Objects.equals(tokens.get(i - 1), "+") || Objects.equals(tokens.get(i - 1), "-") || Objects.equals(tokens.get(i - 1), "*") || Objects.equals(tokens.get(i - 1), "/") || Objects.equals(tokens.get(i - 1), "%")){
                            if ((Objects.equals(tokens.get(i), "-") || Objects.equals(tokens.get(i), "+")) &&
                                    (langSpecs.isANumber(tokens.get(i+1)) || langSpecs.isIdentifier(tokens.get(i+1)))){

                                System.out.println("ERROR in " + path + "! Line: " + lineNr + "; token: " + tokens.get(i-1)+tokens.get(i)+tokens.get(i+1));
                                isOk=false;

                                i=i+2;
                            }
                        }
                        else if(!langSpecs.isIdentifier(tokens.get(i-1)) && !langSpecs.isConstant(tokens.get(i-1)))
                            if ((Objects.equals(tokens.get(i), "-") || Objects.equals(tokens.get(i), "+")) &&
                                (langSpecs.isANumber(tokens.get(i+1)) || langSpecs.isIdentifier(tokens.get(i+1)))){
                                if(langSpecs.isANumber(tokens.get(i+1))) {
                                    constants.addInST(tokens.get(i)+tokens.get(i+1));
                                    AbstractMap.SimpleEntry<Integer, Integer> pos = constants.getPosition(tokens.get(i)+tokens.get(i+1));
                                    pif.addConstant(pos);
                                }
                                else if(langSpecs.isIdentifier(tokens.get(i+1))) {
                                    identifiers.addInST(tokens.get(i)+tokens.get(i+1));
                                    AbstractMap.SimpleEntry<Integer, Integer> pos = identifiers.getPosition(tokens.get(i)+tokens.get(i+1));
                                    pif.addIdentifier(pos);
                                }
                                i=i+2;
                        }

                    }
                    if (langSpecs.isAReservedWord(tokens.get(i)) || langSpecs.isAnOperator(tokens.get(i)) || langSpecs.isASeparator(tokens.get(i))) {
                        pif.addReservedWord(tokens.get(i));
                    } else if (langSpecs.isIdentifier(tokens.get(i))) {
                        identifiers.addInST(tokens.get(i));
                        AbstractMap.SimpleEntry<Integer, Integer> pos = identifiers.getPosition(tokens.get(i));
                        pif.addIdentifier(pos);

                    } else if (langSpecs.isConstant(tokens.get(i))) {
                        constants.addInST(tokens.get(i));
                        AbstractMap.SimpleEntry<Integer, Integer> pos = constants.getPosition(tokens.get(i));
                        pif.addConstant(pos);
                    } else {
                        isOk = false;
                        System.out.println("ERROR in " + path + "! Line: " + lineNr + "; token: " + tokens.get(i));
                    }
                }

                ++lineNr;
            }

            reader.close();
            if(isOk){
                System.out.println("Everything is ok with "+path+"\n"+pif+"Identifier ST:\n"+identifiers+"\n\nConstants ST:\n"+constants);
                FileWriter myWriter = new FileWriter("src/out/PIF.out", true);
                myWriter.write(""+pif);
                myWriter.close();

                FileWriter myWriter2 = new FileWriter("src/out/ST.out", true);
                myWriter2.write("\n\nIdentifier ST:\n"+identifiers+"\nConstants ST:\n"+constants);
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
                } else tokens.add(result[i]);
            } else tokens.add(result[i]);
        }
        return tokens;
    }
}