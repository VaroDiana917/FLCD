package domain;

import java.util.AbstractMap;
import java.util.ArrayList;

public class SymbolTable {
    public ArrayList<ArrayList<String>> st;
    public int st_size;

    public int hashFunction(String symbol){
        int hashSum=0;
        for(int i=0; i<symbol.length(); i++){
            hashSum += symbol.charAt(i);
        }
        return hashSum%13;
    }

    public SymbolTable(){
        this.st_size=0;
        this.st=new ArrayList<>(13);
        for(int i=0;i<13;i++){
            this.st.add(new ArrayList<>());
        }
    }

    public void addInST(String symbol){
        if(!this.existsInST(symbol)){
            int hashKey = this.hashFunction(symbol);
            this.st.get(hashKey).add(symbol);
            this.st_size++;
        }
    }

    public boolean existsInST(String symbol){
        return this.st.get(this.hashFunction(symbol)).contains(symbol);
    }

    public AbstractMap.SimpleEntry<Integer, Integer> getPosition(String symbol){
        if(existsInST(symbol)){
            int hashValue = hashFunction(symbol);
            int position = this.st.get(hashValue).indexOf(symbol);
            return new AbstractMap.SimpleEntry<>(hashValue, position);
        }
        else return new AbstractMap.SimpleEntry<>(null,null);
    }

    public void printST(){
        System.out.println(this.st);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (ArrayList<String> token : st){
            str.append(token);
        }
        return str.toString();
    }
}
