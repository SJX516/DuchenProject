package com.duchen.exchange;


import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LocalReceiver {

    private static LocalReceiver sInstance = null;
    private static ExecutorService threadPool = null;
    private List<ActionProcessor> mProcessors = null;

    private LocalReceiver() {
        mProcessors = new ArrayList<>();
    }

    public static synchronized LocalReceiver getInstance() {
        if (sInstance == null) {
            sInstance = new LocalReceiver();
        }
        return sInstance;
    }

    boolean isAsync(ExchangeRequest request) {
        ActionProcessor processor = findProcessor(request.getAction());
        if (processor != null) {
            return processor.isAsync(request.getAction());
        } else {
            return false;
        }
    }

    public ExchangeResponse execute(Context context, ExchangeRequest request) {
        String curPkgName = context.getPackageName();
        ExchangeResponse response = new ExchangeResponse();
        if (request.getReceiver().equals(curPkgName)) {
            ActionProcessor processor = findProcessor(request.getAction());
            if (processor != null) {
                response.mIsAsync = processor.isAsync(request.getAction());
                if (!response.mIsAsync) {
                    ExecuteResult result = processor.execute(context, request);
                    if (result == null) {
                        result = new ExecuteResult.Builder().code(ExecuteResult.CODE_SUCCESS).build();
                    }
                    response.mResultString = result.toString();
                } else {
                    LocalTask task = new LocalTask(context, request, processor);
                    response.mAsyncResponse = getThreadPool().submit(task);
                }
                return response;
            }
        }
        response.mIsAsync = false;
        response.mResultString = new ExecuteResult.Builder().code(ExecuteResult.CODE_ERROR)
                .msg("Msg to " + request.getReceiver() + ", current is " + curPkgName + " or no processor for action " + request.getAction())
                .build().toString();
        return response;
    }

    private static synchronized ExecutorService getThreadPool() {
        if (null == threadPool) {
            threadPool = Executors.newCachedThreadPool();
        }
        return threadPool;
    }


    private class LocalTask implements Callable<String> {
        private ExchangeRequest mRequest;
        private Context mContext;
        private ActionProcessor mProcessor;

        public LocalTask(Context context, ExchangeRequest request, ActionProcessor processor) {
            this.mContext = context;
            this.mProcessor = processor;
            this.mRequest = request;
        }

        @Override
        public String call() throws Exception {
            ExecuteResult result = mProcessor.execute(mContext, mRequest);
            if (result == null) {
                result = new ExecuteResult.Builder().code(ExecuteResult.CODE_SUCCESS).build();
            }
            return result.toString();
        }
    }

    private ActionProcessor findProcessor(int action) {
        for (ActionProcessor processor : mProcessors) {
            if (processor.isMyAction(action)) {
                return processor;
            }
        }
        return null;
    }

    public void registerAction(ActionProcessor processor) {
        mProcessors.add(processor);
    }

}
