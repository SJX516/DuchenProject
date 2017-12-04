package com.duchen.design.proxy.remote.proxy;

import com.duchen.design.state.GumballMachineRemote;

import java.util.HashMap;
import java.util.Map;

/**
 * 这里是模拟这两个 GumballMachineRemote 完全处于不同的进程里面(甚至是完全的跨网络访问),这个类是一个中间类能同时访问到两边,
 * 起到一个远程代理的作用
 */
public class Naming {

    private static Map<String, GumballMachineRemote> mActualMachine = new HashMap<>();

    public static void rebind(String name, GumballMachineRemote actualMachine) {
        mActualMachine.put(name, actualMachine);
    }

    public static com.duchen.design.proxy.remote.GumballMachineRemote lookup(final String name) {
        if (mActualMachine.containsKey(name)) {
            return new com.duchen.design.proxy.remote.GumballMachineRemote() {
                @Override
                public String getDesc() {
                    return mActualMachine.get(name).getDesc();
                }
            };
        } else {
            return null;
        }
    }

}
