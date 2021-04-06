package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Lexer {

    private List<String> wordList;

    public Lexer() throws FileNotFoundException {
        this.wordList = readInputFile();

    }

    private List<String> readInputFile() throws FileNotFoundException {
         List<String> wordList = new LinkedList<>();

        String file = "/Users/berkkoylu/Desktop/compiler-desing/compiler/input.txt";
        Scanner scanner = new Scanner(new File(file));
        scanner.useDelimiter(" ");

        while(scanner.hasNext()){
            String next = scanner.next();
            wordList.add(next);
        }
        scanner.close();
        return wordList;
    }


//    Identifierlara bakilicak asil kisim burasi
    public List<Token> getNextToken() {

        List<Token> tokenList = new LinkedList<>();

        String word = wordList.get(0);








        return tokenList;
    }
}


