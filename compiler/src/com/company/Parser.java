package com.company;

public class Parser {

    private String token;
    private String tokenClass;
    Parser(){

    }

    public void getNextToken(String lexeme, String tokenClass) {

        this.token = lexeme;
        this.tokenClass = tokenClass;

    }
}
