package com.test.thinkinjava.generics.src;

/**
 * Created by 51619 on 2016/4/24 0024.
 */
public class Tuple {

    public static class TwoTuple<A,B>{
        A a;
        B b;
        TwoTuple(A a ,B b){
            this.a = a;
            this.b = b;
        }

        @Override
        public String toString() {
            return a.toString() +","+ b.toString();
        }
    }

    public static class ThreeTuple<A,B,C> extends  TwoTuple<A,B>{
        C c;
        ThreeTuple(A a, B b,C c) {
            super(a, b);
            this.c = c;
        }

        @Override
        public String toString() {
            return super.toString() + "," + c.toString();
        }
    }

    public static <A,B> TwoTuple<A,B> tuple(A a , B b){
        return new TwoTuple<>(a,b);
    }


    public static <A,B,C> ThreeTuple<A,B,C> tuple(A a , B b ,C c){
        return new ThreeTuple<>(a,b,c);
    }

    public static void  main(String[] args){
        System.out.println(tuple(1, "123"));
        System.out.println(tuple(new Tuple(), "123", false));
    }

}
