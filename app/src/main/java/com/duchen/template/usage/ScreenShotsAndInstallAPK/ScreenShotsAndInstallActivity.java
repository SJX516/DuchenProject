package com.duchen.template.usage.ScreenShotsAndInstallAPK;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.duchen.template.usage.AppActivityBase;
import com.duchen.template.usage.R;
import com.duchen.template.usage.ScreenShotsAndInstallAPK.shell.CommandManager;
import com.duchen.template.usage.ScreenShotsAndInstallAPK.shell.CommandResult;
import com.duchen.template.usage.ScreenShotsAndInstallAPK.shell.ShellUtils;
import com.duchen.template.usage.ScreenShotsAndInstallAPK.without_root.CaptureScreenUtil;
import com.duchen.template.usage.ScreenShotsAndInstallAPK.without_root.InstallUtils;
import com.duchen.template.utils.ToastUtil;

import java.io.File;

public class ScreenShotsAndInstallActivity extends AppActivityBase implements CaptureScreenUtil.CaptureScreenListener, CommandManager.CommandResultListener {

    private Button mScreenShotBtn;
    private Button mInstallAPKBtn;
    private Button mUnInstallAPKBtn;
    private ImageView mShowImg;
    private EditText mCommandEdt;
    private Button mRunCommandBtn;
    private Button mClearBtn;
    private TextView mCommandResultTxt;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_screen_shots_and_install);
    }

    @Override
    public void findViews() {
        mScreenShotBtn = (Button) findViewById(R.id.btn_screen_shots);
        mInstallAPKBtn = (Button) findViewById(R.id.btn_install_apk);
        mUnInstallAPKBtn = (Button) findViewById(R.id.btn_uninstall_apk);
        mRunCommandBtn = (Button) findViewById(R.id.btn_run_command);
        mCommandEdt = (EditText) findViewById(R.id.edt_command);
        mClearBtn = (Button) findViewById(R.id.btn_clear);
        mCommandResultTxt = (TextView) findViewById(R.id.txt_command_result);
        mShowImg = (ImageView) findViewById(R.id.img_show);
    }

    @Override
    public void initViews() {
        mScreenShotBtn.setOnClickListener(this);
        mInstallAPKBtn.setOnClickListener(this);
        mUnInstallAPKBtn.setOnClickListener(this);
        mRunCommandBtn.setOnClickListener(this);
        mClearBtn.setOnClickListener(this);
        requestCapturePermission();
        CommandManager.getInstance().setResultListener(this);
    }

    @Override
    public void handleClick(int id, View v) {
        String para = mCommandEdt.getText().toString();
        switch (id) {
            case R.id.btn_screen_shots:
                CommandManager.getInstance().ScreenCapture(para);
//                CaptureScreenUtil.getInstance().startScreenShot(this);
                break;
            case R.id.btn_install_apk:
                CommandManager.getInstance().installApk(this, para);
//                startInstall();
                break;
            case R.id.btn_uninstall_apk:
                CommandManager.getInstance().uninstallApk(this, para);
                break;
            case R.id.btn_run_command:
                runCommand();
                break;
            case R.id.btn_clear:
                mCommandResultTxt.setText("");
                break;
        }
    }

    private static final String COMMAND_RESULT_FORMAT="resultCode:%s,successMsg:%s,errorMsg:%s\n";

    private void runCommand() {
        final String command = mCommandEdt.getText().toString();
        new Thread(new Runnable() {
            @Override
            public void run() {
                final CommandResult result = ShellUtils.execCommand(command, false);
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (!TextUtils.isEmpty(result.successMsg)) {
                            mCommandResultTxt.append("\n---SUCC---   " + result.result + "\n");
                            mCommandResultTxt.append(result.successMsg);

                        }
                        if (!TextUtils.isEmpty(result.errorMsg)) {
                            mCommandResultTxt.append("\n---ERROR---   " + result.result + "\n");
                            mCommandResultTxt.append(result.errorMsg);
                        }
//                        ToastUtil.showToast(String.format(COMMAND_RESULT_FORMAT, result.result, result.successMsg, result.errorMsg));
                    }
                });
            }
        }).start();
    }

    public static final int REQUEST_ACCESSIBILITY_SERVICES = 11;
    public static final int REQUEST_MEDIA_PROJECTION = 22;

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        requestCapturePermission();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void requestCapturePermission() {
        startActivityForResult(getMediaProjectionManager().createScreenCaptureIntent(), REQUEST_MEDIA_PROJECTION);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_MEDIA_PROJECTION:
                if (resultCode == RESULT_OK && data != null) {
                    CaptureScreenUtil.getInstance().setMediaProjection(getMediaProjectionManager().getMediaProjection(Activity.RESULT_OK, data));
                }
                break;
            case REQUEST_ACCESSIBILITY_SERVICES:
                startInstall();
                break;
        }
    }

    private MediaProjectionManager getMediaProjectionManager() {
        return (MediaProjectionManager) getSystemService(Context.MEDIA_PROJECTION_SERVICE);
    }

    @Override
    public void onCapture(Bitmap bitmap) {
        mShowImg.setImageBitmap(bitmap);
    }

    private void startInstall() {
        new Thread() {
            @Override
            public void run() {
                final String apk = getBaseDir() + File.separator + "Download" +
                        File.separator + "test.apk";
                InstallUtils.installNormal(ScreenShotsAndInstallActivity.this, apk);
            }
        }.start();
    }

    private String getBaseDir() {
        return "/storage/emulated/0";
    }

    @Override
    public void onInstallResult(boolean isSuccess, String packageName) {
        if (isSuccess) {
            ToastUtil.showToast(packageName + "安装成功");
            PackageUtil.launchApp(this, packageName);
        }
    }

    @Override
    public void onUnInstallResult(boolean isSuccess, String packageName) {
        if (isSuccess) {
            ToastUtil.showToast(packageName + "卸载成功");
        }
    }

    @Override
    public void onScreenCaptureResult(boolean isSuccess, String filePath) {
        if (isSuccess) {
            ToastUtil.showToast("截图成功，In " + filePath);
        }
    }
}
