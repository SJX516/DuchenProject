package com.test.thinkinjava.typeinfo.src.pets;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 51619 on 2016/3/30 0030.
 */
public class TypeCounter extends HashMap<Class<?> , Integer> {

    private Class<?> baseType;

    public TypeCounter(Class<?> baseType) {
        this.baseType = baseType;
    }

    public void count(Object object){
        if( !baseType.isInstance(object) ){
            throw new RuntimeException(object + " incorrect type: "+
                    object.getClass() + " , should be type or subType of "+baseType);
        } else {
            countClass(object.getClass());
        }

//        if( !baseType.isAssignableFrom(object.getClass() )){
//            throw new RuntimeException(object + " incorrect type: "+
//                    object.getClass() + " , should be type or subType of "+baseType);
//        } else {
//            countClass(object.getClass());
//        }
    }

    private void countClass(Class<?> cla) {
        Integer quantity = get(cla);

        if( quantity == null ){
            System.out.println(cla + "   Is SubClass Of  " + cla.getSuperclass());
        }
        put(cla , quantity == null ? 1: quantity + 1);
        Class<?> superClass = cla.getSuperclass();
        if( superClass != null && baseType.isAssignableFrom(superClass)){
            countClass(superClass);
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("{");
        for(Map.Entry<Class<?>,Integer> pair : entrySet() ){
            result.append(pair.getKey().getSimpleName());
            result.append("=");
            result.append(pair.getValue());
            result.append(", ");
        }
        result.delete(result.length() - 2 , result.length());
        result.append("}");
        return result.toString();
    }
}
