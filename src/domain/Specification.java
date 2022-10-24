package domain;

import java.util.*;

public class Specification {
    private final ArrayList<String> separators;
    private final ArrayList<String> operators;
    private final ArrayList<String> reservedWords;

    public Specification() {
        this.separators = (ArrayList<String>) Arrays.asList("(", ")", "[", "]", "{", "}", ":", ";", " " );
        this.operators = (ArrayList<String>) Arrays.asList("+", "-", "*", "/", ":=", "=", "!=", "<", ">", "<=", ">=", "%" );
        this.reservedWords = (ArrayList<String>) Arrays.asList("array", "char", "const", "do", "else", "if", "int", "of", "program", "read", "then", "var", "while", "write" );
    }

    public boolean isASeparator(String token){
        return separators.contains(token);
    }

    public boolean isAnOperator(String token){
        return operators.contains(token);
    }

    public boolean isAReservedWord(String token){
        return reservedWords.contains(token);
    }

    public boolean isIdentifier(String token){
        String regex = "^[a-zA-Z]([a-z|A-Z|0-9])*$";
        return token.matches(regex);
    }
}
