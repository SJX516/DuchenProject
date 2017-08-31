package com.duchen.exchange;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.text.TextUtils;

import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static android.content.Context.BIND_AUTO_CREATE;

public class LocalSender {

    private static final String TAG = "LocalSender";
    private static LocalSender sInstance = null;
    private static ExecutorService sThreadPool = null;

    private Context mContext;
    private String mPkgName;

    private HashMap<String, ServiceConnection> mExchangeConnectionMap;
    private HashMap<String, IExchangeAIDL> mExchangeAIDLMap;

    private LocalSender(Application context) {
        mContext = context;
        mPkgName = mContext.getPackageName();
        mExchangeConnectionMap = new HashMap<>();
        mExchangeAIDLMap = new HashMap<>();
    }

    public static synchronized LocalSender getInstance(Application context) {
        if (sInstance == null) {
            sInstance = new LocalSender(context);
        }
        return sInstance;
    }

    public ExchangeResponse execute(ExchangeRequest request) throws Exception {
        request.setSender(mPkgName);
        String receiver = request.getReceiver();
        String requestMsg = request.toFullMsg();
        HLog.d(TAG, "start request: " + requestMsg);
        ExchangeResponse response = new ExchangeResponse();
        if (TextUtils.isEmpty(receiver) || mPkgName.equals(receiver)) {
            ExecuteResult result = new ExecuteResult.Builder()
                    .code(ExecuteResult.CODE_INVALID)
                    .msg("receiver is invalid!")
                    .data(null)
                    .build();
            response.mIsAsync = false;
            response.mResultString = result.toString();
            HLog.d(TAG, "Get response from " + receiver + " end: " + System.currentTimeMillis() + "  result: " + response.mResultString);
            return response;
        }
        if (checkReceiverConnected(receiver)) {
            IExchangeAIDL exchangeAIDL = mExchangeAIDLMap.get(receiver);
            HLog.d(TAG, "Pkg:" + receiver + " async check start: " + System.currentTimeMillis());
            response.mIsAsync = exchangeAIDL.checkResponseAsync(receiver, requestMsg);
            HLog.d(TAG, "Pkg:" + receiver + " async check end: " + System.currentTimeMillis() + " isAsync: " + response.mIsAsync);
        } else {
            response.mIsAsync = true;
            BindAndRequestTask task = new BindAndRequestTask(response, receiver, requestMsg);
            response.mAsyncResponse = getThreadPool().submit(task);
            return response;
        }

        HLog.d(TAG, "Get response from " + receiver + " start: " + System.currentTimeMillis());
        IExchangeAIDL exchangeAIDL = mExchangeAIDLMap.get(receiver);
        if (!response.mIsAsync) {
            response.mResultString = exchangeAIDL.execute(receiver, requestMsg);
            HLog.d(TAG, "Get response from " + receiver + " end: " + System.currentTimeMillis() + "  result: " + response.mResultString);
        } else {
            RequestTask task = new RequestTask(receiver, requestMsg);
            response.mAsyncResponse = getThreadPool().submit(task);
        }
        return response;
    }

    private static synchronized ExecutorService getThreadPool() {
        if (null == sThreadPool) {
            sThreadPool = Executors.newCachedThreadPool();
        }
        return sThreadPool;
    }

    private boolean checkReceiverConnected(String receiver) {
        if (mExchangeAIDLMap.get(receiver) != null) {
            return true;
        }
        return false;
    }

    private boolean connectReceiver(final String receiverPkgName) {
        if (TextUtils.isEmpty(receiverPkgName)) {
            return false;
        }
        Intent binderIntent = new Intent();
        binderIntent.setAction("com.duchen.exchange.action.RECEIVE");
        binderIntent.setPackage(receiverPkgName);
        final ServiceConnection serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                IExchangeAIDL mLocalRouterAIDL = IExchangeAIDL.Stub.asInterface(service);
                IExchangeAIDL temp = mExchangeAIDLMap.get(receiverPkgName);
                if (null == temp) {
                    mExchangeAIDLMap.put(receiverPkgName, mLocalRouterAIDL);
                    mExchangeConnectionMap.put(receiverPkgName, this);
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                mExchangeAIDLMap.remove(receiverPkgName);
                mExchangeConnectionMap.remove(receiverPkgName);
            }
        };
        mContext.bindService(binderIntent, serviceConnection, BIND_AUTO_CREATE);
        return true;
    }

    private boolean disconnectReceiver(String receiverPkgName) {
        if (TextUtils.isEmpty(receiverPkgName)) {
            return false;
        } else if (null == mExchangeConnectionMap.get(receiverPkgName)) {
            return false;
        } else {
            mContext.unbindService(mExchangeConnectionMap.get(receiverPkgName));
            mExchangeAIDLMap.remove(receiverPkgName);
            mExchangeConnectionMap.remove(receiverPkgName);
            return true;
        }
    }

    private class RequestTask implements Callable<String> {

        private String mReceiver;
        private String mRequestString;

        public RequestTask(String receiver, String requestString) {
            this.mReceiver = receiver;
            this.mRequestString = requestString;
        }

        @Override
        public String call() throws Exception {
            IExchangeAIDL exchangeAIDL = mExchangeAIDLMap.get(mReceiver);
            String result = exchangeAIDL.execute(mReceiver, mRequestString);
            HLog.d(TAG, "Get response from " + mReceiver + " end: " + System.currentTimeMillis() + "  result: " + result);
            return result;
        }
    }


    private class BindAndRequestTask implements Callable<String> {
        private ExchangeResponse mResponse;
        private String mReceiver;
        private String mRequestString;

        public BindAndRequestTask(ExchangeResponse response, String receiver, String requestString) {
            this.mResponse = response;
            this.mReceiver = receiver;
            this.mRequestString = requestString;
        }

        @Override
        public String call() throws Exception {
            HLog.d(TAG, "Bind " + mReceiver + " start: " + System.currentTimeMillis());
            connectReceiver(mReceiver);
            int time = 0;
            while (true) {
                if (!checkReceiverConnected(mReceiver)) {
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    time++;
                } else {
                    break;
                }
                //bind操作十秒视为超时
                if (time >= 200) {
                    ExecuteResult result = new ExecuteResult.Builder()
                            .code(ExecuteResult.CODE_CANNOT_BIND_REMOTE)
                            .msg("Bind remote time out. Can not bind " + mReceiver)
                            .data(null)
                            .build();
                    mResponse.mResultString = result.toString();
                    HLog.d(TAG, "Get response from " + mReceiver + " end: " + System.currentTimeMillis() + "  result: " + result.toString());
                    return result.toString();
                }
            }
            HLog.d(TAG, "Bind " + mReceiver + " end: " + System.currentTimeMillis());
            HLog.d(TAG, "Get response from " + mReceiver + " start: " + System.currentTimeMillis());
            IExchangeAIDL exchangeAIDL = mExchangeAIDLMap.get(mReceiver);
            String result = exchangeAIDL.execute(mReceiver, mRequestString);
            HLog.d(TAG, "Get response from " + mReceiver + " end: " + System.currentTimeMillis() + "  result: " + result);
            return result;
        }
    }
}
