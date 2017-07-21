package com.manuhortet;

import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Graph implements IGraph {

    public int[][] vertices;
    public String[] words;

    public Graph() {
    }

    public void getWords(int n, String path) {
        words = new String[n];
        DList list = new DList();
        int[] usedIndexes = new int[n];
        list.readFile(path);
        if (list.size < n) {
            System.out.println("There are not enough words in the list, sorry");
            System.exit(0);
        }
        vertices = new int[n][n];
        for (int i = 0; i < n; i++) {
            boolean counter = true;
            int ran = 0;

            while (counter) {
                counter = false;
                int randomNum = ThreadLocalRandom.current().nextInt(0, list.size + 1);
                for (int k = 0; k < usedIndexes.length; k++) {
                    if (randomNum == usedIndexes[k]) {
                        counter = true;
                    }
                }
                if (!counter) ran = randomNum;
                usedIndexes[i] = ran;
            }
            DNode aux = list.header.next;
            for (int j = 0; j < ran; j++) aux = aux.next;
            boolean checker = true;
            DNode node2 = aux;
            while (checker) {
                if (node2.elem.length() >= 3) {
                    words[i] = node2.elem;
                    checker = false;
                } else {
                    if (node2.next.elem != null) node2 = node2.next;
                    else node2 = list.header.next;
                }
            }
        }

        createAdjacencyMatrix();

        DList sinkHoles = sinkHoles();
        int counter2 = 0;
        while (sinkHoles.header.next.elem != null && counter2 < 1000000) {
                DNode aux = sinkHoles.header.next;
                for (int i = 0; i < sinkHoles.size; i++) {
                    for (int j = 0; j < words.length; j++) {
                        if (words[j].equals(aux.elem)) {
                            boolean counter = true;
                            int ran = 0;
                            while (counter) {
                                counter = false;
                                int randomNum = ThreadLocalRandom.current().nextInt(0, list.size + 1);
                                for (int k = 0; k < usedIndexes.length; k++) {
                                    if (randomNum == usedIndexes[k]) {
                                        counter = true;
                                    }
                                }
                                if (!counter) {
                                    ran = randomNum;
                                    usedIndexes[i] = ran;
                                }
                            }
                            DNode aux2 = list.header.next;
                            for (int m = 0; m < ran; m++) aux2 = aux2.next;
                            boolean checker = true;
                            DNode node2 = aux2;
                            while (checker) {
                                if (node2.elem.length() >= 3) {
                                    words[i] = node2.elem;
                                    checker = false;
                                } else {
                                    if (node2.next.elem != null) node2 = node2.next;
                                    else node2 = list.header.next;
                                }
                            }


                        }
                    }
                    aux = aux.next;
                }
            counter2++;

            createAdjacencyMatrix(); // We must update the adjacency matrix after removing the sinkholes!
            sinkHoles = sinkHoles();
        }
    }


    public DList sinkHoles() {
        DList sinkholes = new DList();
        for (int i = 0; i < words.length; i++) {
            boolean notConnected = true;
            for (int j = 0; j < vertices[i].length; j++) {
                if (vertices[i][j] == 1) {
                    notConnected = false;
                    break;
                }
            }// if flow goes in this conditional, we know that words[i] is a sinkhole.
            if (notConnected) {
                sinkholes.add(words[i]);
            }
        }
        return sinkholes;
    }

    public String lastSyllable(String word) {
        String res = new StringBuilder().append(word.charAt(word.length() - 2)).append(word.charAt(word.length() - 1)).toString();
        return res;
    }

    public String firstSyllable(String word) {
        String res = new StringBuilder().append(word.charAt(0)).append(word.charAt(1)).toString();
        return res;
    }

    public void createAdjacencyMatrix() {
        for (int i = 0; i < words.length; i++) {
            for (int j = 0; j < words.length; j++) {
                if (lastSyllable(words[i]).equals(firstSyllable(words[j]))) {
                    if (i != j) vertices[i][j] = 1;
                } else vertices[i][j] = 0;
            }
        }
    }

    public String longestChain(String firstWord) {
        boolean wrongWord = true;
        int index = 0;
        for (int i = 0; i < words.length; i++) {
            if (words[i].equals(firstWord.toLowerCase())) {
                index = i;
                wrongWord = false;
            }
        }
        if (wrongWord) {
            System.out.println("That word is not valid");
            return "That word is not valid";
        }

        int chainValue = 0;
        String chainedWords = words[index];
        int biggestValue = 0;
        String longestChain = "";
        DList visitedVertices = new DList();
        return connections(visitedVertices, biggestValue, longestChain, chainValue, chainedWords, index);
    }

    public String connections(DList visitedvertices, int biggestValue, String longestChain, int chainValue, String chainedWords, int index) {
        boolean controller = true;
        DNode node = visitedvertices.header.next;
        for (int j = 0; j < vertices[index].length; j++) {
            controller = true;
            while (node.elem != null) {
                if (node.elem.equals(words[j])) {
                    controller = false;
                    break;
                }
                node = node.next;
            }
            if (vertices[index][j] == 1 && controller) {
                chainValue++;
                DNode node3 = visitedvertices.header.next;
                for (int o = 0; o < visitedvertices.size; o++) {
                    if (words[j].equals(node3.elem)) {
                        System.out.println(chainedWords);
                        System.exit(0);
                    }
                    node3 = node3.next;
                }
                chainedWords += " > " + words[j];
                visitedvertices.add(words[j]);

                connections(visitedvertices, biggestValue, longestChain, chainValue, chainedWords, j);
            }
        }
        return "";
    }
}


