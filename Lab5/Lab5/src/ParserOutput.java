import java.util.*;

public class ParserOutput {

    Grammar grammar;
    Map<String, Integer> nrOfChildren;

    List<String> values;
    List<Integer> father;
    List<Integer> leftChild;
    List<Integer> rightSibling;

    public ParserOutput(Grammar grammar) {
        this.grammar = grammar;
        values = new ArrayList<>();
        father = new ArrayList<>();
        leftChild = new ArrayList<>();
        rightSibling = new ArrayList<>();

        nrOfChildren = new HashMap<>();
        for (String nonterminal : grammar.getN()) {
            List<List<String>> productions = grammar.getProductionForNonterminal(nonterminal);
            for (int i = 0; i < productions.size(); i++) {
                nrOfChildren.put(nonterminal + " " + (i + 1), productions.get(i).size());
            }
        }
        for (String terminal : grammar.getE()) {
            nrOfChildren.put(terminal, 0);
        }
        nrOfChildren.put("epsilon", 1);
    }

    public void addProductionString(List<String> productionString) {
        Stack<Integer> stack = new Stack<>();
        stack.push(-1);
        for (String p : productionString) {
            int father = stack.pop();
            int idx = add(p, father);
            for (int i = 0; i < nrOfChildren.get(p); i++) {
                stack.push(idx);
            }
        }

    }

    public int add(String node, int parent) {
        values.add(node);
        int index = values.size() - 1;
        rightSibling.add(-1);
        leftChild.add(-1);
        if (parent == -1) {
            return index;
        }

        if (leftChild.get(parent) == -1) {
            leftChild.set(parent, index);
        } else {
            int current = leftChild.get(parent);
            int prev = -1;
            while (current != -1) {
                prev = current;
                current = rightSibling.get(current);
            }
            rightSibling.set(prev, index);
        }
        father.add(parent);
        return index;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Parser output:\n\n");
        sb.append("Values: ");
        sb.append(this.values).append("\n");
        sb.append("Father: ");
        sb.append(this.father).append("\n");
        sb.append("Left child: ");
        sb.append(this.leftChild).append("\n");
        sb.append("Right sibling: ");
        sb.append(this.rightSibling).append("\n\n");
        if(this.values.size() == 0){
            return sb.toString();
        }
        return sb.toString();
    }

}
