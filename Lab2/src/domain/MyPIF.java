package domain;

import java.util.AbstractMap;
import java.util.ArrayList;

public class MyPIF {
    private ArrayList<AbstractMap.SimpleEntry<String,AbstractMap.SimpleEntry<Integer,Integer>>> pifList;

    public MyPIF() {
        this.pifList = new ArrayList<>();
    }

    void addReservedWord(String token){
        AbstractMap.SimpleEntry<Integer,Integer> ST_pos = new AbstractMap.SimpleEntry<>(-1,-1);
        AbstractMap.SimpleEntry<String,AbstractMap.SimpleEntry<Integer,Integer>> elem = new AbstractMap.SimpleEntry<String,AbstractMap.SimpleEntry<Integer,Integer>>(token, ST_pos);
        this.pifList.add(elem);
    }

    void addConstant(AbstractMap.SimpleEntry<Integer,Integer> ST_pos){
        AbstractMap.SimpleEntry<String,AbstractMap.SimpleEntry<Integer,Integer>> elem = new AbstractMap.SimpleEntry<String,AbstractMap.SimpleEntry<Integer,Integer>>("Constant", ST_pos);
        this.pifList.add(elem);
    }

    void addIdentifier(AbstractMap.SimpleEntry<Integer,Integer> ST_pos){
        AbstractMap.SimpleEntry<String,AbstractMap.SimpleEntry<Integer,Integer>> elem = new AbstractMap.SimpleEntry<String,AbstractMap.SimpleEntry<Integer,Integer>>("Identifier", ST_pos);
        this.pifList.add(elem);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("\n\nMyPIF:\n");
        for(AbstractMap.SimpleEntry<String,AbstractMap.SimpleEntry<Integer,Integer>> token: pifList){
            str.append("Token: ");
            str.append(token.getKey());
            str.append("\n");
            str.append("Identifier: (");
            str.append(token.getValue().getKey());
            str.append(", ");
            str.append(token.getValue().getValue());
            str.append(")");

            str.append("\n\n");
        }

        return str.toString();
    }
}
