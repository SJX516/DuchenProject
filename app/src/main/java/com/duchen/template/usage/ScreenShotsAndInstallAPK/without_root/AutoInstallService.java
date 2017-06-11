package com.duchen.template.usage.ScreenShotsAndInstallAPK.without_root;

import android.accessibilityservice.AccessibilityService;
import android.annotation.TargetApi;
import android.app.Notification;
import android.os.Build;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.duchen.template.component.ApplicationBase;
import com.duchen.template.usage.ScreenShotsAndInstallAPK.PackageUtil;

import java.util.List;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class AutoInstallService extends AccessibilityService {

    public static boolean mIsAbleToAutoInstall = false;
    public static String mInstallFilePath = "";

    public static boolean mIsAbleToAutoUnInstall = false;
    public static boolean mShouldInstallAfterUnInstall = false;

    public static final String TAG = AutoInstallService.class.getSimpleName();

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN || event == null) {
            return;
        }

        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
        if (nodeInfo == null) {
            return;
        }
        if (event.getEventType() == AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED && "com.android.packageinstaller".equals(event.getPackageName())) {
            filterFailingApp(event, nodeInfo);
        }
        String className = event.getClassName().toString();
        if ("com.android.packageinstaller.UninstallerActivity".equals(className) || isGoOnUnInstalling(nodeInfo)) {
            //执行卸载
            processUnInstalledEvent(nodeInfo);
            return;
        }
        //安装分支
        if (isGoOnInstalling(nodeInfo)) {
            //执行安装
            processInstalledEvent(nodeInfo);
        }
    }

    /**
     * 过滤失败的应用，如果安装失败从白名单中移出去
     * 目前只发现魅族会出现此种情况，会弹出toast“应用未安装”，如果不处理会无限点击安装
     *
     * @param event
     * @param nodeInfo
     */
    private void filterFailingApp(AccessibilityEvent event, AccessibilityNodeInfo nodeInfo) {
        if (event == null || nodeInfo == null) {
            return;
        }
        Parcelable parcelable = event.getParcelableData();
        if (!(parcelable instanceof Notification)) {
            List<CharSequence> messages = event.getText();
            if (!messages.isEmpty()) {
                String toastMsg = (String) messages.get(0);
                if (!TextUtils.isEmpty(toastMsg) && toastMsg.contains("应用未安装")) {
                    mIsAbleToAutoInstall = false;
                }
            }
        }
    }

    private boolean isGoOnInstalling(AccessibilityNodeInfo nodeInfo) {
        return mIsAbleToAutoInstall;
    }

    private boolean isGoOnUnInstalling(AccessibilityNodeInfo nodeInfo) {
        return mIsAbleToAutoUnInstall;
    }

    /**
     * 处理安装事件
     *
     * @param nodeInfo
     */
    private void processInstalledEvent(AccessibilityNodeInfo nodeInfo) {

        if (nodeInfo == null) {
            return;
        }

        findAndPerformAction("安装", nodeInfo);
        findAndPerformAction("下一步", nodeInfo);
        findAndPerformAction("完成", nodeInfo);
        String country = getResources().getConfiguration().locale.getCountry();
        if (country != null && !"CN".equalsIgnoreCase(country)) {
            findAndPerformAction("Install", nodeInfo);
            findAndPerformAction("Next", nodeInfo);
            findAndPerformAction("Complete", nodeInfo);
        }
    }

    /**
     * 处理卸载事件
     *
     * @param nodeInfo
     */
    private void processUnInstalledEvent(AccessibilityNodeInfo nodeInfo) {
        if (nodeInfo == null) {
            return;
        }

        findAndPerformAction("确定", nodeInfo);
        findAndPerformAction("卸载", nodeInfo);//华为
    }

    /**
     * 模拟点击指定的按钮或文字
     *
     * @param text
     * @param source
     */
    private void findAndPerformAction(String text, AccessibilityNodeInfo source) {
        if (source == null || text == null) {//text不过滤“”
            Log.e(TAG, "findAndPerformAction 参数为空!");
            return;
        }
        List<AccessibilityNodeInfo> nodes = source.findAccessibilityNodeInfosByText(text);
        if (nodes != null && !nodes.isEmpty()) {
            AccessibilityNodeInfo node;
            for (int i = 0; i < nodes.size(); i++) {
                node = nodes.get(i);
                if (node == null) {
                    continue;
                }
                CharSequence ch = node.getText();
                if (ch == null) {
                    continue;
                }
                //equals不可以，只能用contains，并且如果不匹配的不进行点击 ps：模拟点击安装，界面上有“外部安装程序”不处理就会浪费点击
                if (ch.toString().contains(text) && ch.length() != text.length()) {
                    continue;
                }
                performActionClick(node);
            }
        }
    }

    /**
     * 执行点击操作
     *
     * @param nodeInfo
     */
    private void performActionClick(AccessibilityNodeInfo nodeInfo) {
        if (nodeInfo == null || (!nodeInfo.isEnabled() && !nodeInfo.isClickable())) {
            return;
        }
        if (isButton(nodeInfo) || isTextView(nodeInfo) || isView(nodeInfo)) {
            nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            CharSequence ch = nodeInfo.getText();
            if (ch.toString().contains("完成") && ch.length() == "完成".length()) {
                if (!TextUtils.isEmpty(mInstallFilePath)) {
                    PackageUtil.launchAppFromPath(ApplicationBase.getInstance(), mInstallFilePath);
                }
                mIsAbleToAutoInstall = false;
            }
            if (ch.toString().contains("确定") && ch.length() == "确定".length()) {
                mIsAbleToAutoUnInstall = false;
                if (mShouldInstallAfterUnInstall && !TextUtils.isEmpty(mInstallFilePath)) {
                    mShouldInstallAfterUnInstall = false;
                    InstallUtils.installNormal(ApplicationBase.getInstance(), mInstallFilePath);
                }
            }
        }
    }


    /**
     * 节点是否为Button控件
     *
     * @param node
     * @return
     */
    private boolean isButton(AccessibilityNodeInfo node) {
        return node != null && "android.widget.Button".equals(node.getClassName());
    }

    /**
     * 节点是否为TextView控件
     *
     * @param node
     * @return
     */
    private boolean isTextView(AccessibilityNodeInfo node) {
        return node != null && "android.widget.Button".equals(node.getClassName());
    }

    /**
     * 节点是否为View控件
     *
     * @param node
     * @return
     */
    private boolean isView(AccessibilityNodeInfo node) {
        return node != null && "android.widget.Button".equals(node.getClassName());
    }


    @Override
    public void onInterrupt() {
        Log.e(TAG, "onInterrupt:  ");
    }

}

