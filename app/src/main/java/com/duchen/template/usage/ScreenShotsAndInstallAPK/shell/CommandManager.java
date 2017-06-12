package com.duchen.template.usage.ScreenShotsAndInstallAPK.shell;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInstaller;
import android.os.AsyncTask;
import android.os.Build;
import android.text.TextUtils;

import com.duchen.template.usage.ScreenShotsAndInstallAPK.PackageUtil;
import com.duchen.template.utils.LogUtil;

public class CommandManager {

    private static final String InstallCommandPrefix = "pm install -r -d ";
    private static final String UnInstallCommandPrefix = "pm uninstall ";
    private static final String ScreenCaptureCommandPrefix = "screencap ";

    private static CommandManager sInstance;

    public static CommandManager getInstance() {
        if (sInstance == null) {
            sInstance = new CommandManager();
        }
        return sInstance;
    }

    private CommandResultListener mResultListener;

    private CommandManager() {
    }

    public interface CommandResultListener {
        void onInstallResult(boolean isSuccess, String packageName);
        void onUnInstallResult(boolean isSuccess, String packageName);
        void onScreenCaptureResult(boolean isSuccess, String filePath);
    }

    public void setResultListener(CommandResultListener resultListener) {
        mResultListener = resultListener;
    }

    public CommandResultListener getResultListener() {
        return mResultListener;
    }

    public void installApk(Context context, String filePath) {
        if (TextUtils.isEmpty(filePath) || PackageUtil.getPackageInfoFromFile(context, filePath) == null) {
            return;
        } else {
            CommandTask commandTask = new CommandTask();
            commandTask.setFilePath(filePath);
            commandTask.setPackageName(PackageUtil.getPackageInfoFromFile(context, filePath).packageName);
            commandTask.execute(InstallCommandPrefix + filePath);
        }
    }

    public void uninstallApk(Context context, String packageName) {
        if (TextUtils.isEmpty(packageName) || PackageUtil.getPackageInfo(context, packageName) == null) {
            return;
        } else {
//            CommandTask commandTask = new CommandTask();
//            commandTask.setPackageName(packageName);
//            commandTask.execute(UnInstallCommandPrefix + packageName);
            uninstallIntent(context, packageName);
        }
    }

    public void ScreenCapture(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return;
        } else {
            CommandTask commandTask = new CommandTask();
            commandTask.setFilePath(filePath);
            commandTask.execute(ScreenCaptureCommandPrefix + filePath);
        }
    }

    public static final String UNINSTALL_ACTION = "com.duchen.test.uninstall";

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void uninstallIntent(Context context, String packageName) {
        Intent intent = new Intent();
        intent.setAction(UNINSTALL_ACTION);
        intent.putExtra("packageName", packageName);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        PackageInstaller packageInstaller = context.getPackageManager().getPackageInstaller();
        packageInstaller.uninstall(packageName, sender.getIntentSender());
    }

    private class CommandTask extends AsyncTask<String, Void, CommandResult> {

        private String mCommand;
        private String mFilePath;
        private String mPackageName;

        public void setFilePath(String filePath) {
            this.mFilePath = filePath;
        }

        public void setPackageName(String packageName) {
            this.mPackageName = packageName;
        }

        @Override
        protected CommandResult doInBackground(String... params) {
            if (params.length < 1 || TextUtils.isEmpty(params[0])) {
                return new CommandResult(-1, null, null);
            }
            mCommand = params[0];
            return ShellUtils.execCommand(mCommand, false);
        }

        @Override
        protected void onPostExecute(CommandResult commandResult) {
            boolean isSuccess = false;
            if (!TextUtils.isEmpty(commandResult.successMsg) || commandResult.result == 0) {
                LogUtil.d(mCommand + "  ---SUCC---   " + commandResult.result);
                LogUtil.d(commandResult.successMsg);
                isSuccess = true;
            } else {
                LogUtil.d(mCommand + "  ---ERROR---   " + commandResult.result);
                LogUtil.d(commandResult.errorMsg);
                isSuccess = false;
            }

            if (mCommand.startsWith(InstallCommandPrefix)) {
                mResultListener.onInstallResult(isSuccess, mPackageName);
            } else if (mCommand.startsWith(UnInstallCommandPrefix)) {
                mResultListener.onUnInstallResult(isSuccess, mPackageName);
            } else if (mCommand.startsWith(ScreenCaptureCommandPrefix)) {
                mResultListener.onScreenCaptureResult(isSuccess, mFilePath);
            }
        }
    }
}
