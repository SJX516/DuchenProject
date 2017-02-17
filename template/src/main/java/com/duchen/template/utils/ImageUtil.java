package com.duchen.template.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ImageUtil {

    private static final String TAG = "ImageUtil";
    private static final int MAX_RETRY_TIME_OOM = 5;

    /*********************************************************
     * ImageUtil 提供了保存图片、旋转图片等方法
     * <p/>
     * 1、保存图片 saveBitmap2File
     * 2、获取最大可显示图片 getBitmapFromFileLimitSize
     * 3、
     ********************************************************/

    public static int calculateInSampleSize(BitmapFactory.Options options, int width, int height) {
        int inSampleSize = 1;
        if (width > 0) {
            if (height > 0) {
                inSampleSize = Math.min(options.outWidth/width, options.outHeight/height);
            } else {
                inSampleSize = options.outWidth/width;
            }
        } else {
            if (height > 0) {
                inSampleSize = options.outHeight/height;
            }
        }

        return inSampleSize;
    }


    /**
     * 获取精确裁剪图片
     *
     * @param source
     * @param width
     * @param height
     * @return
     */
    public static Bitmap getThumImage(Bitmap source, int width, int height) {
        if (source == null) return source;

        if (width <= 0 || height <= 0) {
            return source;
        }

        if (source.getWidth() == width) {
            if (source.getHeight() == height || height == 0) {
                return source;
            }
        } else if (source.getHeight() == height && width == 0) {
            return source;
        }

        Matrix matrix = new Matrix();

        float tmp_w = ((float) source.getWidth())/width;
        float tmp_h = ((float) source.getHeight())/height;
        float tmp = tmp_w < tmp_h ? tmp_w : tmp_h;

        int clipWidth = (int) (width*tmp);
        int clipHeight = (int) (height*tmp);

        clipWidth = clipWidth > source.getWidth() ? source.getWidth() : clipWidth;
        clipHeight = clipHeight > source.getHeight() ? source.getHeight() : clipHeight;

        matrix.setScale(1/tmp, 1/tmp);

        int pading_x = (source.getWidth() - clipWidth) >> 1;
        int pading_y = source.getHeight()/3 - clipHeight/2;
        pading_y = pading_y < 0 ? 0 : pading_y;

        Bitmap thumb = null;
        try {
            thumb = Bitmap.createBitmap(source, pading_x, pading_y, clipWidth, clipHeight, matrix, true);

            if (thumb != source) {
                source.recycle();
            }
        } catch (java.lang.OutOfMemoryError e) {
            try {
                thumb = Bitmap.createBitmap(source, pading_x, pading_y, clipWidth, clipHeight, matrix, true);

                if (thumb != source) {
                    source.recycle();
                }
            } catch (java.lang.OutOfMemoryError e1) {
                if (thumb != null) {
                    thumb.recycle();
                }

                thumb = null;
                System.gc();
            }
        }

        return thumb;
    }

    /**
     * 从资源包中解图
     */
    public static Bitmap getBitmap(Resources res, int resId, int width, int height) {
        BitmapFactory.Options options = new BitmapFactory.Options();

        if (width > 0 || height > 0) {
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeResource(res, resId, options);

            options.inSampleSize = calculateInSampleSize(options, width, height);

            options.inJustDecodeBounds = false;
        } else {
            options.inJustDecodeBounds = false;
        }

        Bitmap bitmap = null;
        int i = 0;
        while (i < MAX_RETRY_TIME_OOM) {
            i++;
            try {
                bitmap = BitmapFactory.decodeResource(res, resId, options);
                bitmap = getThumImage(bitmap, width, height);
            } catch (OutOfMemoryError e) {
                options.inSampleSize = options.inSampleSize*2;
            }
        }
        return bitmap;
    }

    /**
     * 从流中解图
     *
     * @param in
     * @param width
     * @param height
     * @return
     */
    public static Bitmap getBitmap(InputStream in, int width, int height) {
        Bitmap bitmap = null;
        if (in != null) {
            BitmapFactory.Options options = new BitmapFactory.Options();

            if (width > 0 || height > 0) {
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeStream(in, null, options);

                options.inSampleSize = calculateInSampleSize(options, width, height);

                options.inJustDecodeBounds = false;
            } else {
                options.inSampleSize = 1;
                options.inJustDecodeBounds = false;
            }
            int i = 0;
            while (i < MAX_RETRY_TIME_OOM) {
                i++;
                try {
                    bitmap = BitmapFactory.decodeStream(in, null, options);
                    bitmap = getThumImage(bitmap, width, height);
                } catch (OutOfMemoryError e) {
                    options.inSampleSize = options.inSampleSize*2;
                    LogUtil.e(TAG, e.getMessage());
                }

            }
        }
        return bitmap;
    }

    /**
     * 从文件中获得不超过最大宽度的bitmap。
     *
     * @param filePath 图片路径。
     * @param maxWidth 最大允许的宽度。
     * @return 符合要求的bitmap，如果decode失败，返回null。
     */
    public static Bitmap getBitmap(String filePath, int maxWidth) {
        Bitmap bitmap = null;
        int maxHeight = 0;
        if (filePath != null) {
            BitmapFactory.Options options = new BitmapFactory.Options();

            if (maxWidth > 0) {
                options.inJustDecodeBounds = true;
                options.inPreferredConfig = Bitmap.Config.RGB_565;
                BitmapFactory.decodeFile(filePath, options);

                options.inSampleSize = calculateInSampleSize(options, maxWidth, maxHeight);
                if (maxWidth < options.outWidth) {
                    maxHeight = options.outHeight*maxWidth/options.outWidth;
                }

                options.inJustDecodeBounds = false;
            } else {
                options.inSampleSize = 1;
                options.inJustDecodeBounds = false;
            }

            int i = 0;
            while (i < MAX_RETRY_TIME_OOM) {
                i++;
                try {
                    bitmap = BitmapFactory.decodeFile(filePath, options);
                    bitmap = getThumImage(bitmap, maxWidth, maxHeight);
                } catch (OutOfMemoryError e) {
                    options.inSampleSize = options.inSampleSize*2;
                }
            }
        }
        return bitmap;
    }

    /**
     * 圆角图标
     *
     * @param source
     * @return
     */
    public static Bitmap getRoundedCornerBitmap(Bitmap source, float cornerSize) {
        if (cornerSize <= 0) {
            cornerSize = 5.0F;
        }

        int width = source.getWidth();
        int height = source.getHeight();

        Rect rect = new Rect(0, 0, width, height);
        RectF rectF = new RectF(rect);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(0xFF424242);

        Bitmap outBmp = null;
        try {
            outBmp = Bitmap.createBitmap(width, height, Config.ARGB_8888);
            Canvas canvas = new Canvas(outBmp);

            canvas.drawARGB(0, 0, 0, 0);
            canvas.drawRoundRect(rectF, cornerSize, cornerSize, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(source, rect, rect, paint);

            source.recycle();
        } catch (java.lang.OutOfMemoryError e) {
            paint = new Paint();
            paint.setAntiAlias(true);
            paint.setColor(0xFF424242);

            try {
                outBmp = Bitmap.createBitmap(width, height, Config.ARGB_8888);
                Canvas canvas = new Canvas(outBmp);
                canvas.drawARGB(0, 0, 0, 0);
                canvas.drawRoundRect(rectF, cornerSize, cornerSize, paint);
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
                canvas.drawBitmap(source, rect, rect, paint);

                source.recycle();
            } catch (java.lang.OutOfMemoryError e1) {
                if (outBmp != null) {
                    outBmp.recycle();
                    outBmp = null;
                }
            }
        }

        return outBmp;
    }

    /**
     * 圆形图标
     *
     * @param source
     * @return
     */
    public static Bitmap getCircleBitmap(Bitmap source) {
        int size = Math.min(source.getWidth(), source.getHeight());
        int left = source.getWidth() > size ? (source.getWidth() - size)/2 : 0;
        int top = source.getHeight() > size ? (source.getHeight() - size)/2 : 0;

        Rect rect = new Rect(left, top, size, size);
        RectF rectF = new RectF(0, 0, size, size);
        Paint paint = new Paint();

        Bitmap outBmp = null;
        try {
            outBmp = Bitmap.createBitmap(size, size, Config.ARGB_8888);
            Canvas canvas = new Canvas(outBmp);
            paint.setAntiAlias(true);
            canvas.drawCircle(size/2, size/2, size/2, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(source, rect, rectF, paint);

            source.recycle();
        } catch (java.lang.OutOfMemoryError e) {
            try {
                outBmp = Bitmap.createBitmap(size, size, Config.ARGB_8888);
                paint = new Paint();
                Canvas canvas = new Canvas(outBmp);
                paint.setAntiAlias(true);
                canvas.drawCircle(size/2, size/2, size/2, paint);
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
                canvas.drawBitmap(source, rect, rectF, paint);

                source.recycle();
            } catch (java.lang.OutOfMemoryError e1) {
                if (outBmp != null) {
                    outBmp.recycle();
                    outBmp = null;
                }
            }
        }

        return outBmp;
    }

    /**
     * 圆形图标,不回收source,保证原图是方形，否则会变形
     *
     * @param source
     * @return
     */
    public static Bitmap getCircleBitmapNoRecycleSource(Bitmap source, int circlesize) {
        int size = circlesize > 0 ? circlesize : Math.min(source.getWidth(), source.getHeight());
//        int left = source.getWidth() > size ? (source.getWidth() - size) / 2 : 0;
//        int top = source.getHeight() > size ? (source.getHeight() - size) / 2 : 0;

        Rect rect = new Rect(0, 0, source.getWidth(), source.getHeight());
        RectF rectF = new RectF(0, 0, size, size);
        Paint paint = new Paint();

        Bitmap outBmp = null;
        try {
            outBmp = Bitmap.createBitmap(size, size, Config.ARGB_8888);
            Canvas canvas = new Canvas(outBmp);
            paint.setAntiAlias(true);
            canvas.drawCircle(size/2, size/2, size/2, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(source, rect, rectF, paint);

        } catch (java.lang.OutOfMemoryError e) {
            try {
                outBmp = Bitmap.createBitmap(size, size, Config.ARGB_8888);
                paint = new Paint();
                Canvas canvas = new Canvas(outBmp);
                paint.setAntiAlias(true);
                canvas.drawCircle(size/2, size/2, size/2, paint);
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
                canvas.drawBitmap(source, rect, rectF, paint);

            } catch (java.lang.OutOfMemoryError e1) {
                if (outBmp != null) {
                    outBmp.recycle();
                    outBmp = null;
                }
            }
        }

        return outBmp;
    }

    /**
     * 保存图片到文件
     *
     * @param bmp
     * @param path
     * @return
     */
    public static boolean saveBitMaptoFile(Bitmap bmp, String path) {
        if (bmp == null || bmp.isRecycled()) return false;

        OutputStream stream = null;
        try {
            File file = new File(path);
            File filePath = file.getParentFile();
            if (!filePath.exists()) {
                filePath.mkdirs();
            }
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    LogUtil.e(TAG, e.getMessage());
                    return false;
                }
            }
            stream = new FileOutputStream(path);
        } catch (FileNotFoundException e) {
            LogUtil.e(TAG, e.getMessage());
            return false;
        }

        CompressFormat format = CompressFormat.JPEG;
        if (bmp.hasAlpha()) {
            format = CompressFormat.PNG;
        }

        return bmp.compress(format, 80, stream);
    }

    /**
     * 保存图片到文件
     *
     * @param bmp
     * @param quality
     * @param file
     * @return
     */
    public static boolean saveBitmap2File(Bitmap bmp, int quality, File file) {
        boolean ret = false;
        OutputStream stream = null;
        try {
            stream = new FileOutputStream(file);
            CompressFormat format = CompressFormat.JPEG;
            if (bmp.hasAlpha()) {
                format = CompressFormat.PNG;
            }

            ret = bmp.compress(format, quality, stream);
            stream.close();
        } catch (FileNotFoundException e) {
            LogUtil.e(TAG, e.getMessage());
        } catch (IOException e) {
            LogUtil.e(TAG, e.getMessage());
        }
        return ret;
    }

    /**
     * 解大图内存不足时尝试5次, samplesize增大
     *
     * @param file
     * @param maxWidth  允许的最大宽度
     * @param maxHeight 允许的最大高度
     * @return
     * @author huangyan
     */
    public static Bitmap getBitmapFromFileLimitSize(String file, int maxWidth, int maxHeight) {
        if (file == null) return null;
        Bitmap bm = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 1;

        if (maxWidth > 0 || maxHeight > 0) {
            options.inJustDecodeBounds = true;
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            // 获取这个图片的宽和高
            bm = BitmapFactory.decodeFile(file, options);
            options.inJustDecodeBounds = false;

            float blW = (float) options.outWidth/maxWidth;
            float blH = (float) options.outHeight/maxHeight;

            if (blW > 1 || blH > 1) {
                if (blW > blH) options.inSampleSize = (int) (blW + 0.9f);
                else options.inSampleSize = (int) (blH + 0.9f);
            }
        }

        int i = 0;
        while (i < MAX_RETRY_TIME_OOM) {
            i++;
            try {
                bm = BitmapFactory.decodeFile(file, options);
                break;
            } catch (OutOfMemoryError e) {
                options.inSampleSize++;
                LogUtil.e(TAG, e.getMessage());
            }
        }
        return bm;
    }

    /**
     * 解大图内存不足时尝试5次, samplesize增大
     *
     * @param file
     * @param max  宽或高的最大值, <= 0 , 能解多大解多大, > 0, 最大max, 内存不足解更小
     * @return
     * @author panjf
     */
    public static Bitmap getBitmapFromFileLimitSize(String file, int max) {
        if (file == null) return null;
        Bitmap bm = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 1;

        if (max > 0) {
            options.inJustDecodeBounds = true;
            // 获取这个图片的宽和高
            bm = BitmapFactory.decodeFile(file, options);
            options.inJustDecodeBounds = false;

            float blW = (float) options.outWidth/max;
            float blH = (float) options.outHeight/max;

            if (blW > 1 || blH > 1) {
                if (blW > blH) options.inSampleSize = (int) (blW + 0.9f);
                else options.inSampleSize = (int) (blH + 0.9f);
            }
        }

        int i = 0;
        while (i < MAX_RETRY_TIME_OOM) {
            i++;
            try {
                bm = BitmapFactory.decodeFile(file, options);
                break;
            } catch (OutOfMemoryError e) {
                options.inSampleSize++;
                LogUtil.e(TAG, e.getMessage());
            }
        }
        return bm;
    }

    /**
     * 解大图内存不足时尝试5次, samplesize增大
     *
     * @param file
     * @param shortmax 短边的最大值, <= 0 , 能解多大解多大, > 0, 最大max, 内存不足解更小
     * @return
     * @author panjf
     */
    public static Bitmap getBitmapFromFileLimitShortSize(String file, int shortmax) {
        if (file == null) return null;
        Bitmap bm = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 1;

        if (shortmax > 0) {
            options.inJustDecodeBounds = true;
            // 获取这个图片的宽和高
            bm = BitmapFactory.decodeFile(file, options);
            options.inJustDecodeBounds = false;

            int changdu = options.outWidth < options.outHeight ? options.outWidth : options.outHeight;
            float bl = (float) changdu/shortmax;

            options.inSampleSize = (int) (bl + 0.9f);
        }

        int i = 0;
        while (i < MAX_RETRY_TIME_OOM) {
            i++;
            try {
                bm = BitmapFactory.decodeFile(file, options);
                break;
            } catch (OutOfMemoryError e) {
                options.inSampleSize++;
                LogUtil.e(TAG, e.getMessage());
            }
        }
        return bm;
    }

    /**
     * 解大图内存不足时尝试5次, samplesize增大
     *
     * @param context
     * @param uri
     * @param max
     * @return
     * @author panjf
     */
    public static Bitmap getBitmapFromUriLimitSize(Context context, Uri uri, int max) {
        InputStream in = null;
        try {
            in = context.getContentResolver().openInputStream(uri);
        } catch (FileNotFoundException e) {
            LogUtil.e(TAG, e.getMessage());
            return null;
        }

        Bitmap bm = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 1;

        if (max > 0) {
            options.inJustDecodeBounds = true;
            // 获取这个图片的宽和高
            bm = BitmapFactory.decodeStream(in, null, options);
            options.inJustDecodeBounds = false;
            try {
                in.close();
            } catch (IOException e) {
                LogUtil.e(TAG, e.getMessage());
            }

            float blW = (float) options.outWidth/max;
            float blH = (float) options.outHeight/max;

            if (blW > 1 || blH > 1) {
                if (blW > blH) options.inSampleSize = (int) (blW + 0.9f);
                else options.inSampleSize = (int) (blH + 0.9f);
            }
        }

        int i = 0;
        while (i < MAX_RETRY_TIME_OOM) {
            i++;
            try {
                try {
                    in = context.getContentResolver().openInputStream(uri);
                } catch (FileNotFoundException e) {
                    LogUtil.e(TAG, e.getMessage());
                    return null;
                }

                bm = BitmapFactory.decodeStream(in, null, options);
                break;
            } catch (OutOfMemoryError e) {
                options.inSampleSize++;
                LogUtil.e(TAG, e.getMessage());
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        LogUtil.e(TAG, e.getMessage());
                    }
                }
            }
        }
        return bm;
    }

    /**
     * 旋转图片
     *
     * @param b
     * @param degrees
     * @return
     */
    public static Bitmap rotateBitmap(Bitmap b, int degrees) {
        if (degrees != 0 && b != null) {
            Matrix m = new Matrix();
            m.postRotate(degrees);
            try {
                Bitmap b2 = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), m, true);
                if (b != b2) {
                    b.recycle();
                    b = b2;
                }
            } catch (OutOfMemoryError ex) {
                LogUtil.e(TAG, ex.getMessage());
            }
        }
        return b;
    }

    /**
     * 缩放成指定大小的图片
     *
     * @param file
     * @param destW
     * @param destH
     * @return
     */
    public static Bitmap getScalBitmapToSize(String file, int destW, int destH) {
        if (file == null) return null;
        BitmapFactory.Options options = new BitmapFactory.Options();

        options.inJustDecodeBounds = true;
        // 获取这个图片的宽和高
        Bitmap bm = BitmapFactory.decodeFile(file, options);
        options.inJustDecodeBounds = false;
        options.inPurgeable = true;

        float blW = (float) options.outWidth/destW;
        float blH = (float) options.outHeight/destH;


        options.inSampleSize = 1;
        float bl = (blW > blH ? blW : blH);
        if (bl >= 2 && bl < 4) {
            options.inSampleSize = 2;
        } else if (bl >= 4) {
            options.inSampleSize = 4;
        }
        try {
            bm = BitmapFactory.decodeFile(file, options);
        } catch (OutOfMemoryError e) {
            LogUtil.e(TAG, e.getMessage());
            return null;
        } catch (Exception e) {
            LogUtil.e(TAG, e.getMessage());
            return null;
        }
        return bm;
    }

    public static Bitmap getBitmapFromDrawable(Drawable drawable) {
        // 取 drawable 的长宽
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();

        // 取 drawable 的颜色格式
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;
        // 建立对应 bitmap
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        // 建立对应 bitmap 的画布
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        // 把 drawable 内容画到画布中
        drawable.draw(canvas);

        return bitmap;
    }

    public static InputStream bitmap2InputStream(Bitmap bm, int quality) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, quality, baos);
        InputStream is = new ByteArrayInputStream(baos.toByteArray());
        return is;
    }

    // 压缩图片大小
    // 该方法实现存在问题，废弃，暂未发现引用点
    @Deprecated
    public static Bitmap compressImage(Bitmap image, float kb) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (options > 0 && baos.toByteArray().length/1024 > kb) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            if (options <= 10) {
                options--;
            } else {
                options -= 10;//每次都减少10
            }
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        return getBitmap(isBm, 0, 0);//把ByteArrayInputStream数据生成图片
    }

    public static Bitmap getBlurBitmap(Bitmap bitmap, Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return blurBitmap(bitmap, context);
        } else {
            return bitmap;
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private static Bitmap blurBitmap(Bitmap bitmap, Context context) {
        if (bitmap == null || bitmap.isRecycled() || context == null) {
            return null;
        }
        try {
            Bitmap outBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
            RenderScript rs = RenderScript.create(context);
            ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
            Allocation allIn = Allocation.createFromBitmap(rs, bitmap);
            Allocation allOut = Allocation.createFromBitmap(rs, outBitmap);
            blurScript.setRadius(25.f);
            blurScript.setInput(allIn);
            blurScript.forEach(allOut);
            allOut.copyTo(outBitmap);
            bitmap.recycle();
            rs.destroy();
            return outBitmap;
        } catch (OutOfMemoryError error) {
            LogUtil.e(TAG, error.getMessage());
            return bitmap;
        }
    }
}
