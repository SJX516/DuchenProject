package com.duchen.template.utils.download;

import android.text.TextUtils;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.duchen.template.component.helper.NetworkHelper;
import com.duchen.template.component.request.RequestManager;
import com.duchen.template.utils.encryption.MD5;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class SimpleFileDownloader {

    private static final String TAG = "SimpleFileDownloader";

    private static final int MAX_DOWNLOAD_COUNT = 2;

    private FileInfo mFileInfo;
    private String mFilePath;
    private String mDownloadUrl;
    private String mFileMD5;
    private boolean mCheckMD5 = false;

    private int mDownloadCount;

    private DownloadFileListener mGetFileListener;

    private Set<Integer> mRequestIds = new HashSet<>();

    public interface DownloadFileListener {
        void onSuccess(String filePath, FileInfo info);
        void onFail(String reason, FileInfo info);
    }

    public SimpleFileDownloader(FileInfo info) {
        if (info != null){
            mFileInfo = info;
            mFilePath = info.getLocalPath();
            mDownloadUrl = info.getDownloadUrl();
            mCheckMD5 = info.needMD5Check();
            mFileMD5 = info.getMD5();
        }
    }

    public void getFile(DownloadFileListener listener) {
        if (TextUtils.isEmpty(mFilePath) || TextUtils.isEmpty(mDownloadUrl)) {
            notifyFail("local path or download url is empty!");
            return;
        }
        mGetFileListener = listener;
        loadActualFile();
    }

    public void release() {
        resetDownloadInfo();
        for (Integer id : mRequestIds) {
            RequestManager.getInstance().cancelRequest(id);
        }
    }

    private void loadActualFile() {
        File file = new File(mFilePath);
        if (file.exists()) {
            if (mCheckMD5) {
                String fileMD5 = MD5.getMD5_32L(file);
                if (!fileMD5.equals(mFileMD5)) {
                    file.delete();
                    if (mDownloadCount < MAX_DOWNLOAD_COUNT) {
                        downloadFile();
                    } else {
                        notifyFail("Download " + MAX_DOWNLOAD_COUNT + " times And MD5 not equals!");
                    }
                    return;
                }
            }
            notifyFilePrepared();
        } else {
            downloadFile();
        }
    }

    private void downloadFile() {
        if (NetworkHelper.getInstance().hasNetworkConnection()) {
            mDownloadCount++;
            int reqId = RequestManager.getInstance().downloadFile(mDownloadUrl, mFilePath, new Response.Listener<File>() {
                @Override
                public void onResponse(File file) {
                    if (file.getAbsolutePath().equals(mFilePath)) {
                        loadActualFile();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    notifyFail("Download failed! " + volleyError.getMessage());
                }
            });
            mRequestIds.add(reqId);
        } else {
            notifyFail("No Cache And No Network!");
        }
    }

    private void notifyFail(String reason) {
        if (mGetFileListener != null) {
            mGetFileListener.onFail(reason, mFileInfo);
        }
    }

    private void notifyFilePrepared() {
        if (mGetFileListener != null) {
            mGetFileListener.onSuccess(mFilePath, mFileInfo);
        }
    }

    private void resetDownloadInfo() {
        mFilePath = "";
        mDownloadUrl = "";
        mFileMD5 = "";
        mGetFileListener = null;
        mDownloadCount = 0;
    }

    public static boolean isLocalCacheUsable(FileInfo info) {
        String filePath = info.getLocalPath();
        File file = new File(filePath);
        if (file.exists()) {
            if (info.needMD5Check()) {
                String fileMD5 = MD5.getMD5_32L(file);
                if (!fileMD5.equals(info.getMD5())) {
                    file.delete();
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
