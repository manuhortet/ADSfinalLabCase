package com.manuhortet;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        System.out.println("Introduce the path of the TXT file you want to take the words from: ");
        Scanner sc = new Scanner(System.in);
        String path = sc.next();

        System.out.println("Now the number of words you want to play with: ");
        int n = sc.nextInt();

        Graph graph = new Graph();
        graph.getWords(8, "/home/frux/Desktop/test.txt"); // n, path

        System.out.println("\n" + "Everything is already prepared. What do you want to do now?");

        boolean on = true; // This is to let the user kill the program

        while (on) {
            System.out.println("\n" +
                    "1) Get the longest chain starting for a known word." + "\n" +
                    "2) Get the longest possible chain." + "\n" +
                    "3) Print the graph representing the words and their relations." + "\n" +
                    "4) Exit.");
            int userDecision = sc.nextInt();
            switch (userDecision) {
                case 1:
                    System.out.println("With which word do you want to start the chain?: ");
                    String word = sc.next();
                    graph.longestChain(word);
                    break;
                case 2:
                    graph.longestChain(graph.words[1]);
                    break;
                case 3:
                    for (int i = 0; i < graph.vertices.length; i++) {
                        System.out.print(graph.words[i] + ":  ");
                        for (int j = 0; j < graph.vertices[i].length; j++) System.out.print(graph.vertices[i][j] + " ");
                        System.out.println();
                    }
                    break;
                case 4:
                    on=false;
            }

        }
    }
}

