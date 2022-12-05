public class Main {
    public static void main(String[] args) {
        Grammar grammar = new Grammar("src/Files/g2.txt");

        System.out.println(grammar.printNonTerminals());
        System.out.println(grammar.printTerminals());
        System.out.println(grammar.printProductions());
//        System.out.println(grammar.printProductionsForNonTerminal("S,A"));
        System.out.println(grammar.printProductionsForNonTerminal("factor"));
//        System.out.println(grammar.printProductionsForNonTerminal("B"));
//        System.out.println(grammar.printProductionsForNonTerminal("C"));
        System.out.println(grammar.checkIfCFG());

    }
}