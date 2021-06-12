package com.company;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {

        Lexer lexer = new Lexer();
        List<Token> tokenList = lexer.tokenization();
        Parser parser = new Parser(tokenList);
        boolean isSuccess  = parser.Start();
        System.out.println();
        System.out.println("Parser phase completed " + (isSuccess ? "Successfully" : "Failed ") );
        SemanticAnalyzer semanticAnalyzer = new SemanticAnalyzer();
        semanticAnalyzer.analyse();

    }


}
