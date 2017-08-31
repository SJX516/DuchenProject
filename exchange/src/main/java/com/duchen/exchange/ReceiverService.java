package com.duchen.exchange;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public final class ReceiverService extends Service {

    private static final String TAG = "ReceiverService";

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return stub;
    }

    IExchangeAIDL.Stub stub = new IExchangeAIDL.Stub() {
        @Override
        public boolean checkResponseAsync(String pkgName, String msg) throws RemoteException {
            ExchangeRequest exchangeRequest = new ExchangeRequest.Builder().json(msg).build();
            return LocalReceiver.getInstance().isAsync(exchangeRequest);
        }

        @Override
        public String execute(String pkgName, String msg) throws RemoteException {
            try {
                ExchangeRequest exchangeRequest = new ExchangeRequest.Builder().json(msg).build();
                ExchangeResponse response = LocalReceiver.getInstance().execute(ReceiverService.this, exchangeRequest);
                return response.get();
            } catch (Exception e) {
                e.printStackTrace();
                return new ExecuteResult.Builder().code(ExecuteResult.CODE_ERROR).msg(e.getMessage()).build().toString();
            }
        }
    };
}
