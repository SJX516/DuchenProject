package com.duchen.google.usage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.duchen.template.utils.DLog;
import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.messages.BleSignal;
import com.google.android.gms.nearby.messages.Distance;
import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.MessageListener;

public class MainActivity extends AppCompatActivity {

    MessageListener mMessageListener;
    Message mMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMessageListener = new MessageListener() {

            @Override
            public void onFound(Message message) {
                DLog.d("Found Message: " + new String(message.getContent()));
            }

            @Override
            public void onLost(Message message) {
                DLog.d("Lost Message: " + new String(message.getContent()));
            }

            @Override
            public void onDistanceChanged(Message message, Distance distance) {
                DLog.d("onDistanceChanged: " + new String(message.getContent()) + " " + distance.toString());
            }

            @Override
            public void onBleSignalChanged(Message message, BleSignal bleSignal) {
                DLog.d("onBleSignalChanged: " + new String(message.getContent()));
            }
        };
        mMessage = new Message("Hello world".getBytes());
    }

    @Override
    protected void onStart() {
        super.onStart();
        Nearby.getMessagesClient(this).publish(mMessage);
        Nearby.getMessagesClient(this).subscribe(mMessageListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Nearby.getMessagesClient(this).unpublish(mMessage);
        Nearby.getMessagesClient(this).unsubscribe(mMessageListener);
    }
}
