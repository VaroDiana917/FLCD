package domain;

import java.util.*;

public class Specification {
    private final List<String> separators;
    private final List<String> openSeparators;
    private final List<String> closedSeparators;
    private final List<String> separatorsForRegex;
    private final List<String> operators;
    private final List<String> operatorsForRegex;
    private final List<String> reservedWords;

    public Specification() {
        this.separatorsForRegex = Arrays.asList("\\(", "\\)", "\\[", "\\]", "\\{", "\\}", "\\:", "\\;", " ", ",");
        this.separators = Arrays.asList("(", ")", "[", "]", "{", "}", ":", ";", " ", ",");
        this.openSeparators = Arrays.asList("(","[", "{");
        this.closedSeparators = Arrays.asList(")","]", "}");
        this.operatorsForRegex = Arrays.asList("\\+", "-", "\\*", "/", ":=", "=", "!=", "<", ">", "<=", ">=", "%" );
        this.operators = Arrays.asList("+", "-", "*", "/", ":=", "=", "!=", "<", ">", "<=", ">=", "%" );
        this.reservedWords = Arrays.asList("ARRAY", "CHAR", "CONST", "DO", "ELSE", "IF", "INTEGER", "OF", "PROGRAM", "READ", "THEN", "VAR", "WHILE", "WRITE", "BEGIN", "END" );
    }

    public List<String> getReservedWords() {return reservedWords;}
    public List<String> getSeparatorsForRegex() {return separatorsForRegex;}
    public List<String> getOperatorsForRegex() {return operatorsForRegex;}

    public boolean isASeparator(String token){return separators.contains(token);}

    public boolean isAnOperator(String token){
        return operators.contains(token);
    }
    public boolean isOpenSeparator(String token){
        return openSeparators.contains(token);
    }
    public boolean isClosedSeparator(String token){
        return closedSeparators.contains(token);
    }

    public boolean isAReservedWord(String token){
        return reservedWords.contains(token);
    }

    public boolean isIdentifier(String token){
        String regex = "^[a-zA-Z]([a-z|A-Z|0-9])*$";
        return token.matches(regex);
    }

    public boolean isANumber(String token){
        String numericPattern = "^0|[+|-][1-9]([0-9])*|[1-9]([0-9])*|[+|-][1-9]([0-9])*\\.([0-9])*|[1-9]([0-9])*\\.([0-9])*$";
        return token.matches(numericPattern);
    }
    public boolean isConstant(String token) {
        String numericPattern = "^0|[+|-][1-9]([0-9])*|[1-9]([0-9])*|[+|-][1-9]([0-9])*\\.([0-9])*|[1-9]([0-9])*\\.([0-9])*$";
        String charPattern = "^\'[a-zA-Z0-9_?!#*.%+=<>;)(}{_]\'";
        String stringPattern = "^\"[a-zA-Z0-9_?!#*.%+=<>;)(}{_]+\"";
        return token.matches(numericPattern) ||
                token.matches(charPattern) ||
                token.matches(stringPattern);
    }
}
