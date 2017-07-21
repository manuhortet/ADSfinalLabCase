package com.manuhortet;

public class DNode {

    public DNode prev;
    public DNode next;
    public String elem;

    public DNode(){}
    public DNode(String elem) {
        this.elem = elem;
    }
}