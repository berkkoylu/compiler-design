package com.company;

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

        if(declaration()){
            if(term(";")){
               if (decls()){
                   result = true;
               }else{
                   result = false;
               }
            }else{
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
        return term("{") && statements() && term("}");
    }

    public boolean statements(){
        return (statement() && statements()) || empty();
    }

    public boolean statement(){
        return ifstmt() || whilestmt() || assignment() || compoundstmt() || methodCall();
    }

    public boolean methodCall(){
        return IDtypes() && term("(") && optparameters() && term(")") && term("end_op" );
    }

    public boolean optparameters(){
        return params() ||empty();
    }

    public boolean params(){
        return param() || APrime();
    }

    public boolean APrime(){
        return term(",") && param() && APrime() || empty();
    }

    public boolean param(){
        return  IDtypes() || term("number") ||term("number") ||term("number");
    }

    public boolean ifstmt(){
        return term("if") && term("(") && booleanExp() && term(")") &&
                statement() && term("else")  && statement() ;
    }

    public boolean whilestmt(){
        return term("while") && term("(") && booleanExp() && term(")")
                && statement() ;
    }

    public boolean booleanExp(){
        return arithmeticExp() && booleanOp() && arithmeticExp();
    }

    public boolean booleanOp(){
        return term("<") || term(">") || term("<=") ||term(">=") ||term("==") || term("!=");
    }

    public boolean assignment(){
        return IDtypes() && term("=") && arithmeticOrUnary() && term(";");
    }

    public boolean arithmeticOrUnary (){
        return arithmeticExp() || unaryExp();
    }

    public boolean unaryExp(){
        return term("++") && IDtypes();
    }

    public boolean arithmeticExp(){
        return multexpr() && BPrime();
    }

    public boolean BPrime(){
        return term("+") && multexpr() && BPrime() || term("-") && multexpr() && BPrime() || empty();
    }

    public boolean multexpr(){
        return simpleexpr() && CPrime();
    }

    public boolean CPrime(){
        return term("*") && simpleexpr() && CPrime() || term("/") && simpleexpr() && CPrime() || empty();
    }

    public boolean simpleexpr(){
        return  IDtypes() || term("number") ||term("number") || term("number") ||
                (term("(") && arithmeticExp() || term(")"));
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
