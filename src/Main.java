public class Main {
    public static void main(String[] args) {
        SymbolTable st = new SymbolTable(10);
        String[] symbols = {"abc", "a", "bc", "bca", "1234", "2341", "2341", "abc"};

        for (int i = 0; i < symbols.length; ++i) {
            st.addInST(symbols[i]);
        }
        st.printST();
        System.out.println("Check if bca exists: "+st.existsInST("bca"));
        System.out.println("Check if bac exists: "+st.existsInST("bac"));
    }
}