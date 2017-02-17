package com.duchen.template.usage;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.duchen.template.usage.TestNotification.TestNotificationsActivity;
import com.duchen.template.usage.TouchEventDispatch.TestDispatchActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String[] TITLES = {"TouchEventDispatch", "TestNotifications"};
    public static final Class[] CLASSES = {TestDispatchActivity.class, TestNotificationsActivity.class};

    private RecyclerView mRecyclerView;
    private RecycleAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        mAdapter = new RecycleAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));//这里用线性显示 类似于listview
//        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));//这里用线性宫格显示 类似于grid view
//        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL));//这里用线性宫格显示 类似于瀑布流

    }

    @Override
    public void onClick(View v) {

    }

    public static class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.DefaultViewHolder> {

        private final LayoutInflater mLayoutInflater;
        private final Context mContext;

        public RecycleAdapter(Context context) {
            mContext = context;
            mLayoutInflater = LayoutInflater.from(context);
        }

        @Override
        public RecycleAdapter.DefaultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new DefaultViewHolder(mLayoutInflater.inflate(R.layout.item_main, parent, false));
        }

        @Override
        public void onBindViewHolder(DefaultViewHolder holder, int position) {
            holder.bindData();
        }

        @Override
        public int getItemCount() {
            return TITLES.length;
        }

        public static class DefaultViewHolder extends RecyclerView.ViewHolder {

            Button mButton;

            public DefaultViewHolder(View itemView) {
                super(itemView);
                mButton = (Button) itemView.findViewById(R.id.item_button);
                mButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(v.getContext(), CLASSES[getLayoutPosition()]);
                        v.getContext().startActivity(i);
                    }
                });
            }

            public void bindData() {
                mButton.setText(TITLES[getLayoutPosition()]);
            }
        }
    }
}
