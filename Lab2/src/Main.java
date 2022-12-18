import domain.MyScanner;
import domain.SymbolTable;

import java.util.AbstractMap;

public class Main {
    public static void main(String[] args) {
        SymbolTable st = new SymbolTable();
        String[] symbols = {"abc", "a", "bc", "bca", "1234", "2341", "2341", "abc"};

        for (String symbol : symbols) {
            st.addInST(symbol);
        }
        st.printST();
        System.out.println("Check if bca exists: "+st.existsInST("bca"));
        System.out.println("Check if bac exists: "+st.existsInST("bac"));

        AbstractMap.SimpleEntry<Integer, Integer> pos = st.getPosition("2341");
        System.out.println("Position of '2341': "+pos.getKey()+", "+pos.getValue());

        pos = st.getPosition("abc");
        System.out.println("Position of 'abc': "+pos.getKey()+", "+pos.getValue());

        pos = st.getPosition("bac");
        System.out.println("Position of 'bac': "+pos.getKey()+", "+pos.getValue());


        System.out.println("-------------------------------------");
//        MyScanner scanner = new MyScanner("src/problems/p1err.txt");
//        scanner.scan();
        MyScanner scanner1 = new MyScanner("src/problems/p1.txt");
        scanner1.scan();
//        MyScanner scanner2 = new MyScanner("src/problems/p2.txt");
//        scanner2.scan();
//        MyScanner scanner3 = new MyScanner("src/problems/p3.txt");
//        scanner3.scan();
    }
}