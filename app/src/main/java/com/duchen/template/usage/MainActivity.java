package com.duchen.template.usage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.duchen.template.usage.Kotlin.KotlinMainActivity;
import com.duchen.template.usage.PinScrollable.PinScrollableActivity;
import com.duchen.template.usage.TestAutoLoopViewPager.AutoLoopViewPagerActivity;
import com.duchen.template.usage.TestBrowser.TestBrowserActivity;
import com.duchen.template.usage.TestLifeCircle.LifeCircleActivityA;
import com.duchen.template.usage.TestNotification.NotificationsActivity;
import com.duchen.template.usage.TestViewPager.NormalViewPagerActivity;
import com.duchen.template.usage.TouchEventDispatch.EventDispatchActivity;
import com.duchen.template.usage.ViewEventBus.ViewEventBusActivity;
import com.duchen.template.utils.ToastUtil;

public class MainActivity extends AppActivityBase implements View.OnClickListener {

    public static final String[] TITLES = {"KotlinMain", "TouchEventDispatch", "TestNotifications", "TestViewPager",
            "PinScrollable", "TestBrowser", "TestLifeCircle", "TestAutoLoopViewPager", "ViewEventBus"};
    public static final Class[] CLASSES = {KotlinMainActivity.class, EventDispatchActivity.class,
            NotificationsActivity.class, NormalViewPagerActivity.class, PinScrollableActivity.class,
            TestBrowserActivity.class, LifeCircleActivityA.class, AutoLoopViewPagerActivity.class,
            ViewEventBusActivity.class};

    private RecyclerView mRecyclerView;
    private RecycleAdapter mAdapter;

    public static void launchNewTask(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        MainApplication.getInstance().setMainActivityDestroyed(false);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    public void findViews() {
        mRecyclerView = (RecyclerView) findViewById(R.id.list_main);
        mAdapter = new RecycleAdapter(this);
    }

    @Override
    public void initViews() {
        mRecyclerView.setAdapter(mAdapter);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));//这里用线性显示 类似于listview
//        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));//这里用线性宫格显示 类似于grid view

        //这里用线性宫格显示类似于瀑布流
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL));
    }

    // 使用点击两次back，退出应用
    private long mLastTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            long now = System.currentTimeMillis();
            if (now - mLastTime > 1500) {
                ToastUtil.showToast("再按一次退出");
                mLastTime = now;
                return true;
            }
        }
        MainApplication.getInstance().resetActivityLifecycle();
        MainApplication.getInstance().setMainActivityDestroyed(true);
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void handleClick(int id, View v) {

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
                mButton = (Button) itemView.findViewById(R.id.btn_item);
                mButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(v.getContext(), CLASSES[getLayoutPosition()]);
                        i.putExtra(AppActivityBase.KEY_TITLE, TITLES[getLayoutPosition()]);
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
