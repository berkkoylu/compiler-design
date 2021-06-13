package com.company;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class SemanticAnalyzer {
    private static final List<String> symbolTable = new LinkedList<>();
    private static final List<String> methodCallList = new LinkedList<>();
    private static final List<String> methodIdentifierList = new LinkedList<>();
    private static final List<String> identifierTable = new LinkedList<>();
    private static final List<String> errorLogMethodDeclaration = new LinkedList<>();
    private static final List<String> variableTable = new LinkedList<>();


    public  List<String> getErrorLogMethodDeclaration() {
        return errorLogMethodDeclaration;
    }

    public  List<String> getVariableTable() {
        return variableTable;
    }

    SemanticAnalyzer(){

    }

    public  List<String> getIdentifierTable() {
        return identifierTable;
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

        for (int i = 0; i <methodIdentifierList.size() ;) {
            String string =  methodIdentifierList.get(i);
            methodIdentifierList.remove(string);
            if(methodIdentifierList.contains(string)){
                methodIdentifierList.removeAll(Collections.singleton(string));
                String newString = new String("Error! Variable \"" + string + "\" already defined in method parameter scope.");
                errorLogMethodDeclaration.add(newString);
            }
        }

        methodIdentifierList.clear();
    }
     public void printErrorLogMethodDecleration(){
//         for (int i = 0; i < errorLogMethodDeclaration.size();) {
//             String string  = errorLogMethodDeclaration.get(i);
//             errorLogMethodDeclaration.remove(string);
//             if(errorLogMethodDeclaration.contains(string)){
//                 errorLogMethodDeclaration.removeAll(Collections.singleton(string));
//                 System.out.println(string);
//             }
//
//
//         }

         for (String string :
                 errorLogMethodDeclaration) {
             System.out.println(string);
         }

     }

    public void analyse() {
        for (String string : methodCallList) {
            if(!symbolTable.contains(string)){
                System.out.println("Error! Method \"" + string + "\" not declared before.");
            }

        }
    }
    public void analyseId() {

        for (int i = 0; i < identifierTable.size();) {
            String string  = identifierTable.get(i);
            identifierTable.remove(string);
            if(identifierTable.contains(string)){
                identifierTable.removeAll(Collections.singleton(string));
                System.out.println("Error! Variable \"" + string + "\"  declared before.");
            }


        }

    }


}
