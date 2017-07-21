package com.manuhortet;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DList implements IDList{

    public DNode header;
    public DNode trailer;
    public int size = 0;

    public DList(){
        header = new DNode();
        trailer = new DNode();
        header.next = trailer;
        trailer.prev = header;
    }

    public boolean isEmpty(){
        return trailer.prev == header;
    }

    public void readFile(String path){
        try{
            Scanner sc =  new Scanner(new File(path));
            while (sc.hasNext()) {
                String word = sc.next();
                word = word.replaceAll("[^a-zA-Z]",""); // deleting all special characters, as '?' or '!'
                add(word.toLowerCase());
            }
        }
        catch(FileNotFoundException e){
            System.out.println("The given path was wrong...");
            System.exit(0);
        }
    }

    public void addFirst(String word){
        DNode node = new DNode(word);
        header.next = node;
        trailer.prev = node;
        node.next = trailer;
        node.prev = header;
    }

    public void add(String word){
        if(isEmpty()) addFirst(word);
        DNode node = new DNode(word);
        node.prev = trailer.prev;
        node.next = trailer;
        trailer.prev.next = node;
        trailer.prev = node;
        size++;
    }

}
