package com.test.thinkinjava.generics.src;

import java.util.ArrayList;
import java.util.Collection;

interface Generator<T> {
    T next();
}

public class Fibonacci implements Generator<Integer> {

    public int index = 0;

    private static int[] tempArray = {1, 1};

    public Integer nextWithTemp() {
        return fibWithTemp(index++);
    }

    private Integer fibWithTemp(int i) {
        if (i < 2) return 1;
        else {
            int result = tempArray[0] + tempArray[1];
            tempArray[0] = tempArray[1];
            tempArray[1] = result;
            return result;
        }
    }

    private int fib(int n) {
        if (n < 2) return 1;
        else return fib(n - 2) + fib(n - 1);
    }


    @Override
    public Integer next() {
        return fib(index++);
    }


    public static <T> Collection<T> fill(Collection<T> coll, Generator<T> gen, int n) {

        for (int i = 0; i < n; i++) {
            coll.add(gen.next());
        }
        return coll;
    }


    public static void main(String[] args) {

        Fibonacci gen = new Fibonacci();

        Collection<Integer> numbers = fill(new ArrayList<>(), gen, 10);
        for (int i : numbers) {
            System.out.print(i + "  ");
        }


    }
}
