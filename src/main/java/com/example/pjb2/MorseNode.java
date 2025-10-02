package com.example.pjb2;

public class MorseNode {
    char letter;
    MorseNode left;  // ponto (.)
    MorseNode right; // traço (-)

    public MorseNode(char letter) {
        this.letter = letter;
        this.left = null;
        this.right = null;
    }
}