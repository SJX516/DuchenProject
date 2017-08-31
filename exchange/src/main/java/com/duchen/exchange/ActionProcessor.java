package com.duchen.exchange;


import android.content.Context;

public interface ActionProcessor {

    boolean isMyAction(int action);

    boolean isAsync(int action);

    ExecuteResult execute(Context context, ExchangeRequest request);

}
