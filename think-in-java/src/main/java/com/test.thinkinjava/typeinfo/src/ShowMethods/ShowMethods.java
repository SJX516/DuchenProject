package com.test.thinkinjava.typeinfo.src.ShowMethods;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.regex.Pattern;

public class ShowMethods {

    private static Pattern p = Pattern.compile("(\\w+\\.)|final |native ");

    public static void main(String[] args) {
        int lines = 0;
        try{
            Class<?> c = Class.forName(args[0]);
            Method[] methods = c.getMethods();
            Constructor[] ctors = c.getConstructors();
            for( Method method : methods ) {
                System.out.println(p.matcher(method.toString()).replaceAll(""));
            }
            for( Constructor ctor:ctors){
                System.out.println(p.matcher(ctor.toString()).replaceAll(""));
            }
            lines = methods.length + ctors.length;
        }catch (ClassNotFoundException e) {
            System.out.println("no such class : " + e);
        }
    }
}
