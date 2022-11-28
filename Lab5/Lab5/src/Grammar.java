import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class Grammar {
    private Set<String> N = new HashSet<>();
    private Set<String> E = new HashSet<>();
    private final HashMap<ArrayList<String>, Set<List<String>>> P = new HashMap<>();
    private String S = "";

    public Grammar(String filename) {
        readFromFile(filename);
    }

    private HashSet<String> splitBySpace(String input){
        String[] lineSplit = input.split("=",input.indexOf("="));
        StringBuilder line = new StringBuilder();
        for(int i=1;i<lineSplit.length;++i)
            line.append(lineSplit[i]);
        StringBuilder builder = new StringBuilder(line.toString());
        builder.deleteCharAt(1).deleteCharAt(line.length()-2);
        line = new StringBuilder(builder.toString());
        return new HashSet<>(Arrays.asList(line.toString().strip().split(" ")));
    }

    private void readFromFile(String filename) {
        try{
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            this.N = splitBySpace(reader.readLine());
            this.E = splitBySpace(reader.readLine());

            this.S = reader.readLine().split("=")[1].strip();

            reader.readLine();//skip over {
            String line = reader.readLine();
            while(line != null){
                if(!line.equals("}")) {
                    String[] tokens = line.split("->");
                    String[] lhsTokens =new String[]{tokens[0]};
                    String[] rhsTokens = tokens[1].split("\\|");
                    ArrayList<String> lhs = new ArrayList<>();
                    for(String l : lhsTokens)
                        lhs.add(l.strip());
                    if(!P.containsKey(lhs))
                        P.put(lhs,new HashSet<>());

                    for(String rhsT : rhsTokens) {
                        ArrayList<String> productionElements = new ArrayList<>();
                        String[] rhsTokenElement = rhsT.strip().split(" ");
                        for(String r : rhsTokenElement)
                            productionElements.add(r.strip());
                        P.get(lhs).add(productionElements);
                    }
                }
                line = reader.readLine();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public String printNonTerminals() {
        StringBuilder sb = new StringBuilder("N = { ");
        for(String n : N)
            sb.append(n).append(" ");
        sb.append("}");
        return sb.toString();
    }

    public String printTerminals() {
        StringBuilder sb = new StringBuilder("E = { ");
        for(String e : E)
            sb.append(e).append(" ");
        sb.append("}");
        return sb.toString();
    }

    public String printProductions() {
        StringBuilder sb = new StringBuilder("P = { \n");
        P.forEach((lhs, rhs) -> {
            sb.append("\t");
            int count = 0;
            for(String lh : lhs) {
                sb.append(lh);
                count++;
                if(count<lhs.size())
                    sb.append(" ");
            }
            sb.append(" -> ");
            count = 0;
            for(List<String> rh : rhs){
                for(String r : rh) {
                    sb.append(r).append(" ");
                }
                count++;
                if (count < rhs.size())
                    sb.append("| ");

            }
            sb.append("\n");
        });
        sb.append("}");
        return sb.toString();
    }

    public String printProductionsForNonTerminal(String nonTerminal){
        StringBuilder sb = new StringBuilder();

        for(ArrayList<String> lhs : P.keySet()) {
            if(lhs.contains(nonTerminal)) {
                sb.append(nonTerminal).append(" -> ");
                Set<List<String>> rhs = P.get(lhs);
                int count = 0;
                for (List<String> rh : rhs) {
                    for(String r : rh) {
                        sb.append(r).append(" ");
                    }
                    count++;
                    if (count < rhs.size())
                        sb.append("| ");
                }
            }
        }

        return sb.toString();
    }

    public boolean checkIfCFG(){
        var checkStartingSymbol = false;
        for(ArrayList<String> lhs : P.keySet())
            if (lhs.contains(S)) {
                checkStartingSymbol = true;
                break;
            }
        if(!checkStartingSymbol)
            return false;

        for(ArrayList<String> lhs : P.keySet()){
            if(lhs.size()>1)
                return false;
            else if(!N.contains(lhs.iterator().next()))
                return false;

            Set<List<String>> rhs = P.get(lhs);

            for(List<String> rh : rhs) {
                for (String r : rh) {
                    if(!(N.contains(r) || E.contains(r) || r.equals("epsilon")))
                        return false;
                }
            }
        }
        return true;
    }

    public Set<String> getN() {
        return N;
    }

    public Set<String> getE() {
        return E;
    }

    public HashMap<ArrayList<String>, Set<List<String>>> getP() {
        return P;
    }

    public String getS() {
        return S;
    }

    public Set<List<String>> getProductionForNonterminal(String nonTerminal) {
        for (ArrayList<String> lhs : P.keySet()) {
            if (lhs.contains(nonTerminal)) {
                return P.get(lhs);
            }
        }
        return new HashSet<>();
    }
}
