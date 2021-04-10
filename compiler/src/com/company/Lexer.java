package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Lexer {

    private List<String> wordList;

    public Lexer() throws FileNotFoundException {
        this.wordList = readInputFile();

    }

    private List<String> readInputFile() throws FileNotFoundException {
         List<String> wordList = new LinkedList<>();

        String file = "C:/Users/eray7/Documents/compiler-desing/compiler/input.txt";
        Scanner scanner = new Scanner(new File(file));
        scanner.useDelimiter(" ");

        while(scanner.hasNext()){
            String next = scanner.next();
            if(next.contains("\r\n")){ //Burada eğer alt bir satıra geçiyorsak eğer wordliste bunu bildiriyoruz ve diğer indexe eklemeye devam ediyoruz aksi halde satır sonu ile satır başı beraber ekleniyodu
                char [] ch = new char[next.length()];
                char [] ch2 = new char[next.length()];

                for(int i = 0 ; i<next.length();i++){

                    if(next.charAt(i) == '\n'){
                        String before = new String(ch);
                        before.replaceAll(String.valueOf((char) 0), "");
                        char chars = before.charAt(0);
                        String before2 = (String.valueOf(chars));

                        wordList.add(before2);
                        next = next.replace("\r","");
                        next = next.replace("\n","");

                        for(int j = i-1 ; j<next.length();j++){
                            ch2[j] =next.charAt(j);
                        }
                        char [] ch3 = new char[next.length()-(i-1)];
                        for(int k = 1 ; k<=next.length()-(i-1);k++){
                            ch3[k-1] = ch2[k];
                        }
                        String after = new String(ch3);
                        after.replaceAll(String.valueOf((char) 0), "");
                        wordList.add(after);
                        break;
                    }else{
                        if(next.charAt(i) == '\r'){
                            continue;
                        }else{
                            ch[i] = next.charAt(i);
                        }

                    }
                }

            }else{
                wordList.add(next);
            }

        }
        scanner.close();
        return wordList;
    }


//    Identifierlara bakilicak asil kisim burasi
    public List<String> getNextToken() {//List<Token>'dı eskiden

        List<Token> tokenList = new LinkedList<>();
        List<String> lexeme = new ArrayList<>();

        List<String> keyword = new ArrayList<>();
        keyword.add("if");
        keyword.add("else if");
        keyword.add("else");
        keyword.add("int");
        keyword.add("string");
        keyword.add("double");
        keyword.add("for");
        keyword.add("while");

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
        for(int i = 0 ; i<wordList.size();i++){
            if(keyword.contains(wordList.get(i))){
                lexeme.add("keyword");
            }else if(operators.contains(wordList.get(i))){
                lexeme.add("operator");
            }else if(isNumeric(wordList.get(i))){
                lexeme.add("number");
            }else if(wordList.get(i).equals("<")){
                lexeme.add("less_op");
            }else if(wordList.get(i).equals("<=")){
                lexeme.add("le_op");
            }else if(wordList.get(i).equals("=")){
                lexeme.add("assign_op");
            }else if(wordList.get(i).equals("<>")){
                lexeme.add("not_op");
            }else if(wordList.get(i).equals(">")){
                lexeme.add("g_op");
            }else if(wordList.get(i).equals(">=")){
                lexeme.add("ge_op");
            }else if(wordList.get(i).equals("==")){
                lexeme.add("is_equal_op");
            }else if(wordList.get(i).equals("!=")){
                lexeme.add("is_not_equal_op");
            }else if(wordList.get(i).equals(";")){
                lexeme.add("end_op");
            }else{
                lexeme.add("id");
            }
        }
        return lexeme;
    }
    public static boolean isNumeric(String string) {
        int intValue;



        if(string == null || string.equals("")) {
            System.out.println("String cannot be parsed, it is null or empty.");
            return false;
        }

        try {
            intValue = Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {

        }
        return false;
    }
}


