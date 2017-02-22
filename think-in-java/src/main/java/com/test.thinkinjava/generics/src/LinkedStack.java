package com.test.thinkinjava.generics.src;

/**
 * Created by 51619 on 2016/4/1 0001.
 */
public class LinkedStack<T> {

    //作为静态内部类的泛型应该这样传递
    static class Node<K>{
        public K item;
        public Node<K> next;

        public Node(K item , Node<K> next){
            this.item = item;
            this.next = next;
        }

        public boolean end(){
            return item == null && next == null;
        }
    }

    private Node<T> top = new Node<>(null,null);

    public void push(T item){
        top = new Node<>(item , top);
    }

    public T pop(){
        T result = top.item;
        if( !top.end() ) {
            top = top.next;
        }
        return result;
    }


    public static void main(String[] args){
        LinkedStack<String> lss = new LinkedStack<>();
        for(String s : "one two three four".split(" ") ){
            lss.push(s);
        }
        String temp;
        while ( ( temp = lss.pop() ) != null ){
            System.out.println(temp);
        }
    }
}
