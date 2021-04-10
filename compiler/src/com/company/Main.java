package com.company;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
	// write your code here
        Lexer lexer = new Lexer();
        List<String> check = new ArrayList<>();
        check = lexer.getNextToken();
        for(int i = 0; i<check.size();i++){
            System.out.println(check.get(i));
        }
        Parser parser = new Parser(lexer);

    }
}
