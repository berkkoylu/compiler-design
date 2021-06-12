package com.company;

import com.sun.xml.internal.bind.v2.model.core.ID;

import java.util.List;
import java.util.Objects;

public class Parser {

    private String token;
    private String tokenClass;
    private int index;
    private final List<Token> stringOfTokens;

    Parser(List<Token> stringOfTokens){
        this.stringOfTokens = stringOfTokens;
    }


    public boolean Start(){
        this.index = 0;

        boolean a = decls();
        boolean b = compoundstmt();
        return (a && b);
    }

    public boolean decls(){

        boolean result ;
        int before = this.index;

        if(declaration()){
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
                return true;
            }
        }
        if(this.index != this.stringOfTokens.size()) {

            if ((Objects.equals(this.stringOfTokens.get(this.index).getTokenValue(), "double"))) {
            if(this.index != this.stringOfTokens.size()){
                this.index++;
            }
            return true;
        }}
        if(this.index != this.stringOfTokens.size()) {

            if ((Objects.equals(this.stringOfTokens.get(this.index).getTokenValue(), "short"))) {
                if (this.index != this.stringOfTokens.size()) {
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

    public boolean statement(){

        boolean a = ifstmt();
        boolean b = whilestmt();
        boolean c = assignment();
        boolean d = compoundstmt();
        boolean f = methodCall();

        boolean result = a || b || c || d || f;

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

        if (IDtypes()){
            if (term("=")){
                if (arithmeticOrUnary()){
                    if (term(";")){
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
        if(this.index != this.stringOfTokens.size()) {

            if ((Objects.equals(this.stringOfTokens.get(this.index).getTokenName(), "id"))) {
                if (this.index != this.stringOfTokens.size()) {
                    this.index++;
                }
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
