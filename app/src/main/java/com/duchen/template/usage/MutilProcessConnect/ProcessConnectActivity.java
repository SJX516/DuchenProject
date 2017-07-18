package com.duchen.template.usage.MutilProcessConnect;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.TextView;

import com.duchen.template.usage.AppActivityBase;
import com.duchen.template.usage.IShop;
import com.duchen.template.usage.R;

public class ProcessConnectActivity extends AppActivityBase {

    private TextView mResultTxt;

    @Override
    public void handleClick(int id, View v) {
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_test_normal);
    }

    @Override
    public void findViews() {
        mResultTxt = (TextView) findViewById(R.id.txt_result);
    }

    @Override
    public void initViews() {
        Intent intent = new Intent("action.process2");
        intent.setPackage("com.duchen.template.usage");
        MyServiceConnection myServiceConnection = new MyServiceConnection();
        bindService(intent, myServiceConnection, BIND_AUTO_CREATE);
    }

    private class MyServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            IShop iShop = IShop.Stub.asInterface(service);
            try {
                mResultTxt.append(iShop.sell() + "\n");
                Product product = new Product("牛肉", 100);
                iShop.setProduct(product);
                mResultTxt.append(iShop.buy() + "\n");
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }

}
