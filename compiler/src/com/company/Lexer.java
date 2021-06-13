package com.company;

import java.io.*;
import java.util.*;

public class Lexer {




    private List<String> wordList;
    private Hashtable<String, String> symbolTable = new Hashtable<>();

    public Lexer() throws IOException {
        this.wordList = readInputFile1();
    }


    private List<String> readInputFile1() throws IOException {

        String line, word = "";
        int count = 0, maxCount = 0;
        ArrayList<String> words = new ArrayList<String>();

        //Opens file in read mode
        FileReader file = new FileReader("input.txt");
        BufferedReader br = new BufferedReader(file);

        //Reads each line
        while ((line = br.readLine()) != null) {
            String string[] = line.toLowerCase().split("([.\\s]+)");
            //Adding all words generated in previous step into words
            for (String s : string) {
                if (!s.equals("")) {
                    words.add(s);
                }
            }
        }

        return words;
    }


    private List<String> readInputFile() throws FileNotFoundException {
        List<String> wordList = new LinkedList<>();

        String file = "input.txt";
        Scanner scanner = new Scanner(new File(file));
        scanner.useDelimiter(" ");

        while (scanner.hasNext()) {
            String next = scanner.next();
            if (next.contains("\n")) { //Burada eğer alt bir satıra geçiyorsak eğer wordliste bunu bildiriyoruz ve diğer indexe eklemeye devam ediyoruz aksi halde satır sonu ile satır başı beraber ekleniyodu
                char[] ch = new char[next.length()];
                char[] ch2 = new char[next.length()];

                for (int i = 0; i < next.length(); i++) {

                    if (next.charAt(i) == '\n') {
                        String before = new String(ch);
                        before.replaceAll(String.valueOf((char) 0), "");
                        char chars = before.charAt(0);
                        String before2 = (String.valueOf(chars));

                        wordList.add(before2);
//                        next = next.replace("\r","");
                        next = next.replace("\n", "");

                        for (int j = i - 1; j < next.length(); j++) {
                            ch2[j] = next.charAt(j);
                        }
                        char[] ch3 = new char[next.length() - (i - 1)];
                        for (int k = 1; k <= next.length() - (i - 1); k++) {
                            ch3[k - 1] = ch2[k];
                        }
                        String after = new String(ch3);
                        after = after.replace("\u0000", "");
                        after.replaceAll(String.valueOf((char) 0), "");
                        wordList.add(after);
                        break;
                    } else {
                        if (next.charAt(i) == '\r') {
                            continue;
                        } else {
                            ch[i] = next.charAt(i);
                        }

                    }
                }

            } else {
                wordList.add(next);
            }

        }
        scanner.close();
        return wordList;
    }

    public List<Token> tokenization() {
        List<Token> tokenPairList = new LinkedList<>();
        List<String> tokenList = new LinkedList<>();
        List<String> stringOfTokens = new LinkedList<>();
        Token token = new Token();
        String tokenClass;
        String prevTokenValue;
        boolean isDefined = false;
        boolean startSymbolTable = false;

        for (int i = 0; i < wordList.size(); i++) {

            tokenClass = token.getToken(wordList.get(i));
            tokenList.add(tokenClass);
            Token token1 = new Token(tokenClass, wordList.get(i));
            tokenPairList.add(token1);
            stringOfTokens.add(tokenClass);

            if (i == 0) {
                continue;
            }
            prevTokenValue = wordList.get(i - 1);

        }

        for (int i = 0; i < tokenList.size(); i++) {
            System.out.print("<" + tokenList.get(i) + "," + wordList.get(i) + "> ");
        }
        return tokenPairList;
    }

    public Hashtable<String, String> getSymbolTable() {
        return symbolTable;
    }
}
