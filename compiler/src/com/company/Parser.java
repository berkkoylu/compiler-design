package com.company;

import java.util.List;

public class Parser {
    Lexer lexer;

    Parser(){
    }
    Parser(Lexer lexer){

        this.lexer = lexer;
    }


    public void dummyMethod(){

        List<Token> tokenList = lexer.getNextToken();



    }



}
