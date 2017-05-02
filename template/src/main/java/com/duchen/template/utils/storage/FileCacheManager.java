package com.duchen.template.utils.storage;

import android.text.TextUtils;

import com.duchen.template.component.ApplicationBase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FileCacheManager {

    private static final String BASE_CACHE_FOLDER = "BaseSimpleCache";
    private static final String DEFAULT_CACHE_SUBFOLDER = "default";
    // 过期时间30天
    private static long EXPIRE_TIME = 30 * 24 * 60 * 60 * 1000L;
    private String mSubCacheFolder = DEFAULT_CACHE_SUBFOLDER;

    private ExecutorService mExecutorService;

    private static Map<String, FileCacheManager> mInstanceMap = new HashMap<>();

    public interface LoadListener {
        void onLoadResult(FileCacheEntity entity);
    }

    public static FileCacheManager getInstance() {
        return getInstance(DEFAULT_CACHE_SUBFOLDER);
    }

    public static FileCacheManager getInstance(String folderName) {
        if (TextUtils.isEmpty(folderName)) {
            throw new InvalidParameterException("The folder name is not invalid!");
        }
        if (mInstanceMap == null) {
            mInstanceMap = new HashMap<>();
        }
        if (mInstanceMap.get(folderName) == null) {
            mInstanceMap.put(folderName, new FileCacheManager(folderName));
        }

        return mInstanceMap.get(folderName);
    }

    private FileCacheManager(String cacheFolderName) {
        mExecutorService = Executors.newSingleThreadExecutor();
        init(cacheFolderName);
    }

    private void init(String folderName) {
        if (!TextUtils.isEmpty(folderName)) {
            mSubCacheFolder = folderName;
            trimCache();
        }
    }

    public void save(String fileName, String content) {
        final FileCacheEntity entity = buildFileCacheEntity(fileName);
        entity.setContent(content);
        mExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                entity.saveData();
            }
        });
    }

    public void load(String fileName, final LoadListener loadListener) {
        final FileCacheEntity entity = buildFileCacheEntity(fileName);
        final WeakReference<LoadListener> loadListenerRef = new WeakReference<LoadListener>(loadListener);
        mExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                entity.loadData();
                if (loadListenerRef.get() != null) {
                    loadListenerRef.get().onLoadResult(entity);
                }
            }
        });
    }

    public void delete(String fileName) {
        final FileCacheEntity entity = buildFileCacheEntity(fileName);
        mExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                entity.deleteFile();
            }
        });
    }

    public void deleteAll() {
        mExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                traverseDelete(new File(getPath()), new DeleteCondition(DeleteCondition.DeleteType.DEFAULT));
            }
        });
    }

    public void fuzzyDelete(final String fuzzyName) {
        mExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                DeleteCondition condition = new DeleteCondition(DeleteCondition.DeleteType.FUZZY_DELETE);
                condition.setFuzzyName(fuzzyName);
                traverseDelete(new File(getPath()), condition);
            }
        });
    }

    public void trimCache() {
        mExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                traverseDelete(new File(getPath()), new DeleteCondition(DeleteCondition.DeleteType.EXPIRE_DELETE));
            }
        });
    }

    private void traverseDelete(File file, DeleteCondition condition) {
        if (file != null && file.exists()) {
            if (file.isDirectory()) {
                File[] subs = file.listFiles();
                if (subs != null) {
                    for (File sub : subs) {
                        traverseDelete(sub, condition);
                    }
                }
            } else {
                deleteFile(file, condition);
            }
        }
    }

    private void deleteFile(File file, DeleteCondition condition) {
        if (file == null || !file.exists()) {
            return;
        }
        if (condition.canDelete(file)) {
            file.delete();
        }
    }

    private static class DeleteCondition {

        enum DeleteType {
            DEFAULT,
            FUZZY_DELETE,
            EXPIRE_DELETE
        }

        private DeleteType mDeleteType = DeleteType.DEFAULT;
        private String mFuzzyName = "";

        public DeleteCondition(DeleteType deleteType) {
            mDeleteType = deleteType;
        }

        public void setFuzzyName(String fuzzyName) {
            mFuzzyName = fuzzyName;
        }

        public boolean canDelete(File file) {
            switch (mDeleteType) {
                case FUZZY_DELETE:
                    if (file.getName().contains(mFuzzyName)) {
                        return true;
                    } else {
                        return false;
                    }
                case EXPIRE_DELETE:
                    if (System.currentTimeMillis() - file.lastModified() >= EXPIRE_TIME) {
                        return true;
                    } else {
                        return false;
                    }
                default:
                    return true;
            }
        }
    }

    private String getPath() {
        String fileCachePath;
        if (ApplicationBase.getInstance().getExternalFilesDir(null) != null) {
            fileCachePath = ApplicationBase.getInstance().getExternalFilesDir(null).getAbsolutePath();
        } else {
            fileCachePath = ApplicationBase.getInstance().getFilesDir().getAbsolutePath();
        }
        return fileCachePath + File.separator + BASE_CACHE_FOLDER + File.separator + mSubCacheFolder;
    }

    private FileCacheEntity buildFileCacheEntity(String fileName) {
        return new FileCacheEntity(fileName, getPath());
    }

    public static class FileCacheEntity {

        private String mFileName;
        private String mPath;

        private boolean mExist;
        private long mLastModifyTime;
        private String mContent;

        public FileCacheEntity(String fileName, String path) {
            mFileName = fileName;
            mPath = path;
        }

        public boolean isExist() {
            return mExist;
        }

        public long getLastModifyTime() {
            return mLastModifyTime;
        }

        public String getContent() {
            return mContent;
        }

        public void setContent(String content) {
            if (content == null) {
                mContent = "";
            } else {
                mContent = content;
            }
        }

        public void saveData() {
            if (TextUtils.isEmpty(mFileName)) {
                return;
            }
            File file = new File(mPath + File.separator + mFileName);
            FileUtil.createFile(file);
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(file);
                out.write(mContent.getBytes());
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public void loadData() {
            String content = "";
            if (!TextUtils.isEmpty(mFileName)) {
                content = FileUtil.readDataFromFile(mPath + File.separator, mFileName);
            }
            if (TextUtils.isEmpty(content)) {
                mExist = false;
                mContent = "";
                mLastModifyTime = 0;
            } else {
                mExist = true;
                mContent = content;
                File file = new File(mPath, mFileName);
                mLastModifyTime = file.lastModified();
            }
        }

        public void deleteFile() {
            if (TextUtils.isEmpty(mFileName)) {
                return;
            }
            FileUtil.deleteFile(mPath + File.separator + mFileName);
        }
    }

}
