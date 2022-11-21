public class Main {
    public static void main(String[] args) {
        Grammar grammar = new Grammar("src/Files/g1.txt");

        System.out.println(grammar.printNonTerminals());
        System.out.println(grammar.printTerminals());
        System.out.println(grammar.printProductions());
    }
}