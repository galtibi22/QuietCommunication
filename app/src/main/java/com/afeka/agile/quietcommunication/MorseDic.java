package com.afeka.agile.quietcommunication;

import java.util.HashMap;

public class MorseDic {


    public static HashMap<Character, String> letterToMorse = new HashMap<>();
    public static HashMap<String, Character> morseToLetters = new HashMap<>();

    public static void init(){
        letterToMorse.put('a', ".-");
        morseToLetters.put(".-",'a');
        letterToMorse.put('b', "-...");
        morseToLetters.put("-...",'b');
        letterToMorse.put('c',  "-.-");
        morseToLetters.put( "-.-",'c');
        letterToMorse.put('d',  "-..");
        morseToLetters.put("-..",'d');
        letterToMorse.put('e',".");
        morseToLetters.put(".",'e');
        letterToMorse.put('f', "..-.");
        morseToLetters.put("..-.",'f');
        letterToMorse.put('g',  "--.");
        morseToLetters.put("--.",'g');
        letterToMorse.put('h', "....");
        morseToLetters.put("....",'h');
        letterToMorse.put('i',"..");
        morseToLetters.put("..",'i');
        letterToMorse.put('j', ".---");
        morseToLetters.put(".---",'j');
        letterToMorse.put('k',   "-.");
        morseToLetters.put("-.",'k');
        letterToMorse.put('l', ".-..");
        morseToLetters.put(".-..",'l');
        letterToMorse.put('m',   "--");
        morseToLetters.put("--",'m');
        letterToMorse.put('n',   "-.");
        morseToLetters.put("-.",'n');
        letterToMorse.put('o',  "---");
        morseToLetters.put("---",'o');
        letterToMorse.put('p', ".--.");
        morseToLetters.put(".--.",'p');
        letterToMorse.put('q', "--.-");
        morseToLetters.put("--.-",'q');
        letterToMorse.put('r', ".-.");
        morseToLetters.put(".-.",'r');
        letterToMorse.put('s',  "...");
        morseToLetters.put("...",'s');
        letterToMorse.put('t',   "-");
        morseToLetters.put("-",'t');
        letterToMorse.put('u',  "..-");
        morseToLetters.put("..-",'u');
        letterToMorse.put('v', "...-");
        morseToLetters.put("...-",'v');
        letterToMorse.put('w',  ".--");
        morseToLetters.put(".--",'w');
        letterToMorse.put('x', "-..-");
        morseToLetters.put("-..-",'x');
        letterToMorse.put('y', "-.--");
        morseToLetters.put("-.--",'y');
        letterToMorse.put('z', "--..");
        morseToLetters.put("--..",'z');
    }

}
