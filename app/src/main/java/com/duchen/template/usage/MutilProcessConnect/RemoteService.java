package com.duchen.template.usage.MutilProcessConnect;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;

import com.duchen.template.usage.IShop;
import com.duchen.template.utils.LogUtil;

public class RemoteService extends Service {

    public RemoteService() {
    }

    private Product mProduct;

    private Binder mBinder = new IShop.Stub() {
        @Override
        public String sell() throws RemoteException {
            return "客官，你需要点什么？";
        }

        @Override
        public Product buy() throws RemoteException {
            return mProduct;
        }

        @Override
        public void setProduct(Product product) throws RemoteException {
            mProduct = product;
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
