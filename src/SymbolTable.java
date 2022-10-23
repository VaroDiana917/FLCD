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

    public SymbolTable(int n){
        this.st_size=0;
        this.st=new ArrayList<>(n);
        for(int i=0;i<n;i++){
            this.st.add(new ArrayList<>(n));
        }
    }

    public void addInST(String symbol){
        if(!this.existsInST(symbol)){
            int hashKey = this.hashFunction(symbol);
            this.st.get(hashKey).add(symbol);
            this.st_size++;
            System.out.println("Added "+ symbol +" to Symbol Table");
        }
        else System.out.println("Symbol already exists");
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


}
