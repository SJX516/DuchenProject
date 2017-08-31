// IExchangeAIDL.aidl
package com.duchen.exchange;

// Declare any non-default types here with import statements

interface IExchangeAIDL {
    boolean checkResponseAsync(String pkgName, String msg);
    String execute(String pkgName, String msg);
}
