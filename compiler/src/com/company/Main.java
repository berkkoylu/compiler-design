package com.company;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {

        Lexer lexer = new Lexer();
        List<Token> tokenList = lexer.tokenization();
        System.out.println();
        Parser parser = new Parser(tokenList);
        boolean isSuccess  = parser.Start();


        System.out.println();
        System.out.println("Parser phase completed " + (isSuccess ? "Successfully." : "Failed.") );
        System.out.println();

        SemanticAnalyzer semanticAnalyzer = new SemanticAnalyzer();
        semanticAnalyzer.analyse();
        semanticAnalyzer.analyseId();
        semanticAnalyzer.printErrorLogMethodDecleration();

    }


}
