package com.test.thinkinjava.generics.src;

/**
 * Created by 51619 on 2016/4/26 0026.
 */
class Building{

}

class House extends Building{

}

public class ClassTypeCapture<T> {
    Class<T> kind;
    public ClassTypeCapture(Class<T> kind){
        this.kind = kind;
    }

    public boolean f(Object arg){
        return kind.isInstance(arg);
    }
}
