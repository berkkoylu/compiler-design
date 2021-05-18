package com.company;

import java.io.FileNotFoundException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        Lexer lexer = new Lexer();
        List<Token> tokenList = lexer.tokenization();
        Parser parser = new Parser(tokenList);
        boolean isSuccess  = parser.Start();
        System.out.println(isSuccess);
    }
}
