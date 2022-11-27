public class Main {
    public static void main(String[] args) {
        Grammar grammar = new Grammar("src/Files/g1.txt");

        System.out.println(grammar.printNonTerminals());
        System.out.println(grammar.printTerminals());
        System.out.println(grammar.printProductions());
//        System.out.println(grammar.printProductionsForNonTerminal("S"));
//        System.out.println(grammar.printProductionsForNonTerminal("A"));
//        System.out.println(grammar.printProductionsForNonTerminal("B"));
//        System.out.println(grammar.printProductionsForNonTerminal("C"));
        System.out.println(grammar.checkIfCFG());
    }
}