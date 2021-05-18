package com.company;

import java.util.ArrayList;
import java.util.List;

public class Token {

    private List<String> keywords;
    private List<String> operators;
    private String tokenName;
    private String tokenValue;

    public Token()  {
        this.keywords = createKeywords();
        this.operators = createOperators();
    }

    public String getTokenName() {
        return tokenName;
    }

    public void setTokenName(String tokenName) {
        this.tokenName = tokenName;
    }

    public String getTokenValue() {
        return tokenValue;
    }

    public void setTokenValue(String tokenValue) {
        this.tokenValue = tokenValue;
    }

    public Token(String tokenName, String tokenValue){
        this.tokenName = tokenName;
        this.tokenValue = tokenValue;
    }

    public List<String> createKeywords(){

        List<String> keyword = new ArrayList<>();
        keyword.add("if");
        keyword.add("else if");
        keyword.add("else");
        keyword.add("int");
        keyword.add("short");
        keyword.add("double");
        keyword.add("string");
        keyword.add("double");
        keyword.add("for");
        keyword.add("while");

        return keyword;
    }

    public List<String> createOperators(){

        List<String> operators = new ArrayList<>();
        operators.add("+");
        operators.add("/");
        operators.add("*");
        operators.add("-");
        operators.add("%");
        operators.add("(");
        operators.add(")");
        operators.add("{");
        operators.add("}");
        operators.add("[");
        operators.add("]");
        operators.add(",");
        operators.add("||");
        operators.add("&&");

        return operators;

    }
    public String getToken(String word){

        if(keywords.contains(word)){
            return("keyword");
        }else if(operators.contains(word)){
            return("operator");
        }else if(isNumeric(word)){
            return("number");
        }else if(word.equals("<")){
            return("less_op");
        }else if(word.equals("<=")){
            return("le_op");
        }else if(word.equals("=")){
            return("assign_op");
        }else if(word.equals("<>")){
            return("not_op");
        }else if(word.equals(">")){
            return("g_op");
        }else if(word.equals(">=")){
            return("ge_op");
        }else if(word.equals("==")){
            return("is_equal_op");
        }else if(word.equals("!=")){
            return("is_not_equal_op");
        }else if(word.equals(";")){
            return("end_op");
        }else if(isId(word)){
            return("id");
        }else{
            return("not identified");
        }
    }

    public static boolean isNumeric(String string) {
        int intValue;

        if(string == null || string.equals("")) {
            System.out.println(string + "String cannot be parsed, it is null or empty.");
            return false;
        }

        try {
            intValue = Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {

        }
        return false;
    }

    public static boolean isId(String string) {

        if(string == null || string.equals("")) {
            System.out.println(string + "String cannot be parsed, it is null or empty.");
            return false;
        }

        if( string.length() > 64 ){
            System.out.println("Error for " + string + " ! Identifier cannot be longer than 64.");
            return false;
        }

        int index = 0;
        if(Character.isDigit(string.charAt(0))) {
            System.out.println("Error for " + string + " ! Identifier cannot start with digit.");
            return false;
        }
        if(string.charAt(0) == '$' || string.charAt(0) == '_'){
            if (string.length()==1){
                System.out.println("Error for " + string + " ! Identifier cannot be just '$' or '_'.");
                return false;
            }
            index = 1;
        }
        for (int i = index; i < string.length(); i++) {
            if( !(Character.isDigit(string.charAt(i))) && !(Character.isLetter(string.charAt(i))) ) {
                System.out.println("Error for " + string + " ! Identifier must contain only letter or digit except first character.");
                return false;
            }
        }
        return true;
    }

}
