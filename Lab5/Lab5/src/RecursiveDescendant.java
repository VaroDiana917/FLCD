import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class RecursiveDescendant {

    public Configuration configuration;
    public Grammar grammar;

    public RecursiveDescendant(Grammar grammar) {
        this.grammar = grammar;

        //Initialising Configuration with -
        //  normal state = q,
        //  position of current symbol of input sequence = 1,
        //  workingStack - stores the way the parse is built,
        //  inputStack - part of the tree to be built
        configuration = new Configuration("q", 1, new Stack<>(), new Stack<>());
        configuration.workingStack.push("epsilon");
        configuration.inputStack.push("epsilon");
        configuration.inputStack.push(grammar.getS());
    }

    public List<String> run(List<String> seq) throws Exception {
        //No error or final state
        while (!configuration.state.equals("f") && !configuration.state.equals("e")) {
            String inputTop = configuration.inputStack.peek();
            String workingTop = configuration.workingStack.peek();
            if (configuration.position == seq.size() + 1 && configuration.inputStack.peek().equals("epsilon")) {
//                System.out.println("success");
                success();
            }
            if (configuration.state.equals("q")) {

                //Head of input stack is a non-terminal
                if (grammar.getN().contains(inputTop)) {
//                    System.out.println("expand");
                    expand();
                }

                //Head of input stack is a terminal = current symbol from input => we call advance()
                else if (grammar.getE().contains(inputTop) &&
                        seq.size() >= configuration.position && seq.get(configuration.position - 1).equals(inputTop)) {
//                    System.out.println("advance");
                    advance();
                }

                //Head of input stack is a terminal != current symbol from input => we call mi()
                else if (grammar.getE().contains(inputTop) &&
                        (seq.size() < configuration.position || !seq.get(configuration.position - 1).equals(inputTop))) {
//                    System.out.println("momentary insuccess");
                    momentaryInsuccess();
                }
            } else if (configuration.state.equals("b")) {

                //Head of working stack is a terminal:
                if (grammar.getE().contains(workingTop)) {
//                    System.out.println("another try");
                    back();
                }

                else {
//                    System.out.println("back");
                    anotherTry();
                }

            }
        }
        if (configuration.state.equals("f")) {
            System.out.println("Sequence accepted\n");
            return configuration.workingStack;
        } else {
            throw new Exception("Syntax Error!");
        }
    }

    //Head of input stack is a non-terminal:
    public void expand() {
        String nonTerminal = configuration.inputStack.pop();
        List<String> productionForNT = new ArrayList<>(grammar.getProductionForNonterminal(nonTerminal).get(0));

        Collections.reverse(productionForNT);
        productionForNT.forEach(p -> configuration.inputStack.push(p));
        configuration.workingStack.add(nonTerminal + " " + "1");
    }


    //Head of input stack is a terminal = current symbol from input => we call advance()
    public void advance() {
        configuration.position++;
        String terminal = configuration.inputStack.pop();
        configuration.workingStack.push(terminal);
    }


    //Head of input stack is a terminal != current symbol from input => we call mi()
    public void momentaryInsuccess() {
        configuration.state = "b";
    }


    //Head of working stack is a terminal:
    public void back() {
        configuration.position--;
        configuration.inputStack.push(configuration.workingStack.pop());
    }


    //Head of working stack is a non-terminal:
    public void anotherTry() {
        String topWorking = configuration.workingStack.peek();
        List<String> gamma = grammar.getProductionForNonterminal(topWorking.split(" ")[0])
                                .get(Integer.parseInt(topWorking.split(" ")[1])
                                - 1);

        if (Integer.parseInt(topWorking.split(" ")[1]) != grammar.getProductionForNonterminal(topWorking.split(" ")[0]).size()) {
            configuration.state = "q";

            gamma.forEach(s -> configuration.inputStack.pop());
            configuration.workingStack.pop();
            configuration.workingStack.push(topWorking.split(" ")[0] + " " + (Integer.parseInt(topWorking.split(" ")[1]) + 1));

            List<String> gamma2 = new ArrayList<>(grammar.getProductionForNonterminal(topWorking.split(" ")[0]).get(Integer.parseInt(topWorking.split(" ")[1])));
            Collections.reverse(gamma2);
            gamma2.forEach(s -> configuration.inputStack.push(s));

        } else if (configuration.position == 1 && topWorking.split(" ")[0].equals(grammar.getS())) {
//            System.out.println(gamma+topWorking);
//            System.out.println(Integer.parseInt(topWorking.split(" ")[1])+".."+grammar.getProductionForNonterminal(topWorking.split(" ")[0]).size());
            configuration.state = "e";
        } else {
            configuration.workingStack.pop();
            gamma.forEach(s -> configuration.inputStack.pop());
            configuration.inputStack.push(topWorking.split(" ")[0]);
        }
    }


    public void success() {
        configuration.state = "f";
    }
}
