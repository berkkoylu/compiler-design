package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Lexer {

    private List<String> wordList;
    private List<String> tokenList;

    public Lexer() throws FileNotFoundException {
        this.wordList = readInputFile();
        this.tokenList = tokenization();
    }

    private List<String> readInputFile() throws FileNotFoundException {
        List<String> wordList = new LinkedList<>();

        String file = "C:/Users/ozfat/IdeaProjects/compiler-desing/compiler/input.txt";
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
            }
            else{
                wordList.add(next);
            }
        }
        scanner.close();
        System.out.println("wordList " + wordList);
        return wordList;
    }

    public List<String> tokenization(){

        List<String> tokenList = new LinkedList<>();
        Hashtable<String, String> ht = new Hashtable<>();
        Token token = new Token();
        String tokenClass;
        String prevTokenClass;
        Parser parser = new Parser();
        boolean isDefined = false;
        for(int i = 0 ; i<wordList.size();i++){

            tokenClass = token.getToken(wordList.get(i));
            tokenList.add(tokenClass);

            if(tokenClass.equals("id")){
                if(i==0){
                    System.out.println("Error! " + wordList.get(i) + " is not identified.");
                }
                else{
                    prevTokenClass = wordList.get(i-1);

                    if(prevTokenClass.equals("int") || prevTokenClass.equals("short") || prevTokenClass.equals("double")){
                        //System.out.println("prevTokenClass " + prevTokenClass + " for " + wordList.get(i));
                        for(int j = 0; j<ht.size();j++){
                            if(ht.containsKey(wordList.get(i))){
                                System.out.println("Error! " + wordList.get(i) + " is previously defined.");
                                isDefined = true;
                                break;
                            }
                        }
                        if (!isDefined){
                            ht.put(wordList.get(i), prevTokenClass);
                            //System.out.println("Hash table updated for " + wordList.get(i) );
                            //System.out.println("Hash table  " + ht );
                        }
                        isDefined = false;
                    }
                    //id önceden tanımlanmamış
                    if(!(ht.containsKey(wordList.get(i)))){
                        System.out.println("Error! " + wordList.get(i) + " is not identified.");
                    }
                }
            }
            parser.getNextToken(wordList.get(i), tokenClass);
        }

        for (int i = 0; i < tokenList.size(); i++) {
            System.out.print("<" + tokenList.get(i) + "," + wordList.get(i) + "> ");
        }
        return tokenList;
    }
}
