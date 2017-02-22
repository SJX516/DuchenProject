package com.test.thinkinjava.src;

public class Main {

    public static void main(String[] args) {
        float start = 0;
        float  end = 1;
        float speed = 30;

        int time = 0;
        float current = start;

        while( current < end - 0.01 ){
            current = ( end - current ) * speed * (1/60.0f) + current;
            System.out.println(current);
            time++;
        }

        System.out.println(time );

    }
}
