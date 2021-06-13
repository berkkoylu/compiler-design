package com.company;

import java.util.Hashtable;
import java.util.List;
import java.util.Objects;

public class Parser {

    private String token;
    private String tokenClass;
    private int index;
    private final List<Token> stringOfTokens;
    private final SemanticAnalyzer semanticAnalyzer;
    private Hashtable<String, String> symbolTable = new Hashtable<>();
    private String type;
    private String value;

    Parser(List<Token> stringOfTokens){
        this.stringOfTokens = stringOfTokens;
        this.semanticAnalyzer = new SemanticAnalyzer();
    }


    public boolean Start(){
        this.index = 0;

        boolean c = method();
        boolean a = decls();
        boolean b = compoundstmt();
        return (a && b && c) ;
    }
    public boolean method(){

        int before = this.index;
        boolean result;

        if (term("{")){
            if (methodStatements()){
                if (term("}")){
                    result = true;
                }else{
                    this.index = before;
                    result = false;
                }
            }else{
                this.index = before;
                result = false;
            }
        }else{
            result = false;
        }



        return result;
    }

    public boolean methodStatements(){

        boolean result;
        int before = this.index;


        if(methodStatement()){
            if(methodStatements()){
                result = true;
            }else{
                this.index = before;
                result = false;
            }
        }else{
            result = empty();
        }

        return result;

    }

    public boolean methoddecls (){
        boolean result ;
        int before = this.index;

        if(declaration()){
            semanticAnalyzer.getMethodIdentifierList().add(this.stringOfTokens.get(before + 1 ).getTokenValue());
            if(term(",")){
                if (methoddecls()){
                    result = true;
                }else{
                    this.index = before;
                    result = false;
                }
            }else{
                this.index = before;
                result = false;
            }
        }else {
            result = empty();
        }

        return result;

    }
    public boolean methodStatement(){

        boolean result ;
        int before = this.index;
        if(type()){
            if(IDtypes()){
                addToSymbolTable();
                if(term("(")){
                    if(methoddecls()){
                        semanticAnalyzer.analyseMethodDecleration();
                        if (term(")")){
                            if(term("{")){
                                if (statements()){
                                    if (term("}")){
                                        semanticAnalyzer.getSymbolTable().add(this.stringOfTokens.get(before + 1 ).getTokenValue());
                                        result = true;
                                    }else{
                                        this.index = before;
                                        result = false;
                                    }
                                }else{
                                    this.index = before;
                                    result = false;
                                }
                            }else{
                                this.index = before;
                                result = false;
                            }

                        }else{
                            this.index = before;
                            result = false;
                        }
                    }else{
                        this.index = before;
                        result = false;
                    }
                }else{
                    this.index = before;
                    result = false;
                }
            }else {
                this.index = before;
                result = false;
            }
        } else {
            result = false;
        }

        return  result;

    }
    public void setTypeforSymbolTable(String type) {
        this.type = type;
    }

    public void setValueforSymbolTable(String value) {
        this.value = value;
    }

    public String getTypeforSymbolTable() {
        return type;
    }

    public String getValueforSymbolTable() {
        return value;
    }
    public void addToSymbolTable() {
        boolean isDefined = false;
        for (int j = 0; j < symbolTable.size(); j++) {
            if (symbolTable.containsKey(getValueforSymbolTable())) {
                semanticAnalyzer.getErrorLogMethodDeclaration().add("Error! " + getValueforSymbolTable() + " is previously defined.");
//                System.out.println("Error! " + getValueforSymbolTable() + " is previously defined.");
                isDefined = true;
                break;
            }
        }
        if (!isDefined) {
            symbolTable.put(getValueforSymbolTable(), getTypeforSymbolTable());
//            System.out.println(getTypeforSymbolTable());
//            System.out.println(getValueforSymbolTable());
//            System.out.println("Hash table  " + symbolTable );
            //initialized = false;
        }


        if (!(symbolTable.containsKey(getValueforSymbolTable()))) {
            System.out.println("Error! " + getValueforSymbolTable() + " is not identified.");
        }

    }

    public boolean decls(){

        boolean result ;
        int before = this.index;

        if(declaration()){
            addToSymbolTable();
            semanticAnalyzer.getIdentifierTable().add(this.stringOfTokens.get(before + 1).getTokenValue());
            semanticAnalyzer.getVariableTable().add(this.stringOfTokens.get(before + 1).getTokenValue());
            if(term(";")){
               if (decls()){
                   result = true;
               }else{
                   this.index = before;
                   result = false;
               }
            }else{
                this.index = before;
                result = false;
            }
        }else {
            result = empty();
        }

        return result;
    }

    public boolean declaration(){
        return type() && IDtypes();
    }

    public boolean type(){

        if(this.index != this.stringOfTokens.size()) {
            if ((Objects.equals(this.stringOfTokens.get(this.index).getTokenValue(), "int"))) {
                    this.index++;
                setTypeforSymbolTable("int");

                return true;
            }
        }
        if(this.index != this.stringOfTokens.size()) {

            if ((Objects.equals(this.stringOfTokens.get(this.index).getTokenValue(), "double"))) {
            if(this.index != this.stringOfTokens.size()){
                setTypeforSymbolTable("double");

                this.index++;
            }
            return true;
        }}
        if(this.index != this.stringOfTokens.size()) {

            if ((Objects.equals(this.stringOfTokens.get(this.index).getTokenValue(), "short"))) {
                if (this.index != this.stringOfTokens.size()) {
                    setTypeforSymbolTable("short");

                    this.index++;
                }
                return true;
            }
        }
        return false;
    }

    public boolean compoundstmt(){

        int before = this.index;
        boolean result;

        if (term("{")){
            if (statements()){
                if (term("}")){
                    result = true;
                }else{
                    this.index = before;
                    result = false;
                }
            }else{
                this.index = before;
                result = false;
            }
        }else{
            result = false;
        }



        return result;
    }

    public boolean statements(){

        boolean result;
        int before = this.index;


        if(statement()){
                if(statements()){
                    result = true;
                }else{
                    this.index = before;
                    result = false;
                }
            }else{
                result = empty();
            }

        return result;
    }

    public boolean statementDec(){
        boolean result ;
        int before = this.index;

        if(declaration()){
            semanticAnalyzer.getIdentifierTable().add(this.stringOfTokens.get(before + 1).getTokenValue());
            if(term(";")){
                    result = true;
            }else{
                this.index = before;
                result = false;
            }
        }else {
            this.index = before;
            result = false;
        }

        return result;

    }

    public boolean statement(){

        boolean a = ifstmt();
        boolean b = whilestmt();
        boolean c = assignment();
        boolean d = compoundstmt();
        boolean f = methodCall();
        boolean deneme = statementDec();

        boolean result = a || b || c || d || f || deneme;

        return result;
    }

    public boolean methodCall(){
        boolean result;
        int before = this.index;

        if (IDtypes()){
            if (term("(")){
                if (optparameters()){
                    if (term(")")){
                        if (term("end_op")){
                            semanticAnalyzer.getMethodCallList().add(this.stringOfTokens.get(before).getTokenValue());
                            result = true;
                        }else{
                            this.index = before;
                            result = false;
                        }
                    }else{
                        this.index = before;
                        result = false;
                    }
                }else{
                    this.index = before;
                    result = false;
                }
            }else{
                this.index = before;
                result = false;
            }
        }else{
            result = false;
        }


        return result;
    }

    public boolean optparameters(){

        boolean result;

                if (params()){
                    result = true;
                }else{
                    result = empty();
                }

        return result;
    }

    public boolean params(){
        boolean result;
        int before = this.index  ;

        if (param()){

            if (APrime()){
                if(!semanticAnalyzer.getVariableTable().contains(this.stringOfTokens.get(before).getTokenValue())){
//                   semanticAnalyzer.getErrorLogMethodDeclaration().add("Error! Method \"" + this.stringOfTokens.get(before).getTokenValue() + "\" not declared before.");
                }
                result = true;
            }else{
                this.index = before;
                result = false;
            }
        }else{
            this.index = before;
            result = false;
        }



        return result;
    }

    public boolean APrime(){
        boolean result;
        int before = this.index  ;

        if( term(",") ){
            if(param()){
                if (APrime()){
                    result = true;
                }else {
                    this.index = before;
                    result = false;
                }
            }else{
                this.index = before;
                result = false;
            }
        }else{
            result = empty();
        }

        return result;
    }

    public boolean param(){

        return  IDtypes() || term("number") ||term("number") ||term("number");
    }

    public boolean ifstmt(){
        boolean result;
        int before = this.index ;

        if (term("if")  ){
            if (term("(")){
                if (booleanExp()){
                    if (term(")")){
                        if ( statement()){
                            if (term("else") ){
                                if ( statement()){
                                    result = true;
                                }else{
                                    this.index = before;
                                    result = false;
                                }
                            }else{
                                this.index = before;
                                result = false;
                            }
                        }else{
                            this.index = before;
                            result = false;
                        }
                    }else{
                        this.index = before;
                        result = false;
                    }
                }else{
                    this.index = before;
                    result = false;
                }

            }else{
                this.index = before;
                result = false;
            }
        }else{
            result = false;
        }

        return result;
    }

    public boolean whilestmt(){
        boolean result;
        int before = this.index ;

        if (term("while") ){
            if (term("(") ) {
                if (booleanExp() ){
                    if (term(")")){
                        if (statement()){
                            result = true;
                        }else{
                            this.index = before;
                            result = false;
                        }
                    }else{
                        this.index = before;
                        result = false;
                    }
                }else{
                    this.index = before;
                    result = false;
                }
            }else{
                this.index = before;
                result = false;
            }
        }else{
            result = false;
        }



        return result ;
    }

    public boolean booleanExp(){
        return arithmeticExp() && booleanOp() && arithmeticExp();
    }

    public boolean booleanOp(){



        return term("<") || term(">") || term("<=") ||term(">=") ||term("==") || term("!=");
    }

    public boolean assignment(){

        boolean result;
        int before = this.index ;
        String typeofLeftSide;
        String typeofRightSide = "dkj";
        if (IDtypes()){
            typeofLeftSide = getTypeforSymbolTable();

            if (term("=")){
                if(symbolTable.containsKey(this.stringOfTokens.get(this.index-2).getTokenValue())){
                    String firstVariable = symbolTable.get(this.stringOfTokens.get(this.index - 2).getTokenValue());
                    if (symbolTable.containsKey(this.stringOfTokens.get(this.index).getTokenValue())){
                        String secondVariable = symbolTable.get(this.stringOfTokens.get(this.index).getTokenValue());
                        if(!firstVariable.equals(secondVariable)){
                            System.out.println("Error! Type Check " + this.stringOfTokens.get(this.index).getTokenValue()
                            );
                        }
                    }
                }
                if (arithmeticOrUnary()){
                    if (term(";")){
                        if(!semanticAnalyzer.getVariableTable().contains(this.stringOfTokens.get(before).getTokenValue())){
//                            semanticAnalyzer.getErrorLogMethodDeclaration().add("Error! Method \"" + this.stringOfTokens.get(before).getTokenValue() + "\" not declared before.");
                        }
                        result = true;
                    }else{
                        this.index = before;
                        result = false;
                    }
                }else{
                    this.index = before;
                    result = false;
                }
            }else{
                this.index = before;
                result = false;
            }
        }else{
            result = false;
        }

        return result;
    }

    public boolean arithmeticOrUnary (){
        return arithmeticExp() || unaryExp();
    }

    public boolean unaryExp(){
        boolean result;
        int before = this.index ;

        if(term("++")){
            if (IDtypes()){
                result = true;
            }else{
                this.index = before;
                result = false;
            }
        }else{
            result = false;
        }

        return result;
    }

    public boolean arithmeticExp(){
        return multexpr() && BPrime();
    }

    public boolean BPrime(){

        boolean result;
        int before = this.index ;

        if (term("+")){
            if (multexpr())  {
                if (BPrime()){
                    result = true;
                }else{
                    this.index = before;
                    result = false;
                }
            }else{
                this.index = before;
                result = false;
            }
        }else if (term("-")){
            if (multexpr()){
                if (BPrime()){
                    result = true;
                }else {
                    this.index = before;
                    result = false;
                }
            }else{
                this.index = before;
                result = false;
            }
        }else{
            result = empty();
        }

        return result;
    }

    public boolean multexpr(){
        return simpleexpr() && CPrime();
    }

    public boolean CPrime(){
        boolean result;
        int before = this.index ;


        if (term("*")){
            if (simpleexpr()){
                if (CPrime()){
                    result = true;
                }else{
                    this.index = before;
                    result = false;
                }
            }else{
                this.index = before;
                result = false;
            }
        }else if (term("/")){
            if (simpleexpr()){
                if (CPrime()){
                    result = true;
                }else {
                    this.index = before;
                    result = false;
                }
            }else {
                this.index = before;
                result = false;
            }
        }else {
            result = empty();
        }

        return result;
    }

    public boolean simpleexpr(){
        boolean a = false ;
        boolean b = false;
        if (IDtypes()){
            a = true;
        }else if (term("number")){
            b = true;
        }

        boolean result ;
        int before = this.index ;

        if (term("(")){
            if (arithmeticExp()){
                if (term(")")){
                    result = true;
                }else{
                    this.index = before;
                    result = false;
                }
            }else{
                this.index = before;
                result = false;
            }
        }else{
            result = false;
        }

        return  a || b || result;
    }
    public boolean IDtypes(){
        if (this.index != this.stringOfTokens.size()) {

            if ((Objects.equals(this.stringOfTokens.get(this.index).getTokenName(), "id"))) {
                if (this.index != this.stringOfTokens.size()) {
                    setValueforSymbolTable(this.stringOfTokens.get(this.index).getTokenValue());
                    this.index++;
                }
                /*if(!initialized){
                    System.out.println("Error! " + getValueforSymbolTable() + " is not identified.");
                    initialized = false;
                }
                else{
                    initialized = false;
                }*/
                return true;
            }
        }
        return false;
    }

    public boolean term(String terminal){
        if(this.index != this.stringOfTokens.size()) {

            if ((Objects.equals(this.stringOfTokens.get(this.index).getTokenValue(), terminal)) || (Objects.equals(this.stringOfTokens.get(this.index).getTokenName(), terminal))) {
                if (this.index != this.stringOfTokens.size()) {
                    this.index++;
                }
                return true;
            }
        }
        return false;
    }

    public boolean empty(){
        return true;
    }
}
