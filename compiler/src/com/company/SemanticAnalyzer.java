package com.company;

import java.util.LinkedList;
import java.util.List;

public class SemanticAnalyzer {
    private static final List<String> symbolTable = new LinkedList<>();
    private static final List<String> methodCallList = new LinkedList<>();
    private static final List<String> methodIdentifierList = new LinkedList<>();



    SemanticAnalyzer(){

    }

    public List<String> getSymbolTable() {
        return symbolTable;
    }

    public  List<String> getMethodCallList() {
        return methodCallList;
    }

    public  List<String> getMethodIdentifierList() {
        return methodIdentifierList;
    }

    public void analyseMethodDecleration(){

        for (String string :
                methodIdentifierList) {
            methodIdentifierList.remove(string);
            if(methodIdentifierList.contains(string)){
                System.out.println();
                System.out.println("Error! Variable " + string + " already defined in method parameter scope.");
            }
        }

        methodIdentifierList.clear();
    }


    public void analyse() {
        for (String string : methodCallList) {
            if(!symbolTable.contains(string)){
                System.out.println("Error! Method \"" + string + "\" not declared before.");
            }

        }

    }
}
