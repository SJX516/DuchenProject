package com.duchen.template.ui.keyboard;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.util.AttributeSet;

import com.duchen.template.R;

import java.lang.reflect.Field;
import java.util.List;

public class CustomKeyBoardView extends KeyboardView {
    private Context mContext;
    private Keyboard mKeyBoard;

    public CustomKeyBoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }

    public CustomKeyBoardView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mKeyBoard = this.getKeyboard();
        List<Keyboard.Key> keys = null;
        if (mKeyBoard != null) {
            keys = mKeyBoard.getKeys();
        }

        if (keys != null) {
            for (Keyboard.Key key : keys) {
                // custom key background
                if (key.codes[0] == Keyboard.KEYCODE_DONE) { // done
                    drawKeyBackground(R.drawable.selector_bg_keyboard_key_blue, canvas, key);
                    drawText(canvas, key, Color.WHITE, mKeyBoard.isShifted());
                } else if (key.codes[0] == Keyboard.KEYCODE_DELETE || key.codes[0] == Keyboard.KEYCODE_MODE_CHANGE) {
                    // Delete、_
                    drawKeyBackground(R.drawable.selector_bg_keyboard_key_white, canvas, key);
                    drawText(canvas, key, Color.DKGRAY, mKeyBoard.isShifted());
                } else if (key.codes[0] == Keyboard.KEYCODE_SHIFT) { // shift
                    if (!getKeyboard().isShifted()) {
                        drawKeyBackground(R.drawable.selector_bg_keyboard_key_white, canvas, key);
                    } else {
                        drawKeyBackground(R.drawable.selector_bg_keyboard_key_blue, canvas, key);
                    }
                    drawText(canvas, key, Color.WHITE, mKeyBoard.isShifted());
                }
            }
        }
    }


    private void drawKeyBackground(int drawableId, Canvas canvas, Keyboard.Key key) {
        Drawable npd = mContext.getResources().getDrawable(drawableId);
        int[] drawableState = key.getCurrentDrawableState();
        if (key.codes[0] != 0) {
            npd.setState(drawableState);
        }
        npd.setBounds(key.x, key.y, key.x + key.width, key.y + key.height);
        npd.draw(canvas);
    }


    private CharSequence adjustCase(boolean isShifted, CharSequence label) {
        if (isShifted && label != null && label.length() < 3 && Character.isLowerCase(label.charAt(0))) {
            label = label.toString().toUpperCase();
        }
        return label;
    }

    private void drawText(Canvas canvas, Keyboard.Key key, int color, boolean isShifted) {
        Rect bounds = new Rect();
        Paint paint = new Paint();
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setAntiAlias(true);

        // custom key text color
        paint.setColor(color);

        if (key.label != null) {
            CharSequence label = adjustCase(isShifted, key.label.toString());

            Field field;

            if (label.length() > 1 && key.codes.length < 2) {
                int labelTextSize = 0;
                try {
                    field = KeyboardView.class.getDeclaredField("mLabelTextSize");
                    field.setAccessible(true);
                    labelTextSize = (int) field.get(this);
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                paint.setTextSize(labelTextSize);
                paint.setTypeface(Typeface.DEFAULT_BOLD);
            } else {
                int keyTextSize = 0;
                try {
                    field = KeyboardView.class.getDeclaredField("mLabelTextSize");
                    field.setAccessible(true);
                    keyTextSize = (int) field.get(this);
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                paint.setTextSize(keyTextSize);
                paint.setTypeface(Typeface.DEFAULT);
            }

            paint.getTextBounds(label.toString(), 0, label.toString().length(), bounds);

//            if (key.codes[0] == 95) {
//                canvas.drawText(label.toString(), key.x + (key.width / 2),
//                        (key.y + key.height / 2) + bounds.height() * 4, paint);
//
//            } else {
            canvas.drawText(label.toString(), key.x + (key.width/2), (key.y + key.height/2) + bounds.height()/2, paint);
//            }
        } else if (key.icon != null) {
            key.icon.setBounds(key.x + (key.width - key.icon.getIntrinsicWidth())/2, key.y + (key.height - key.icon
                    .getIntrinsicHeight())/2, key.x + (key.width - key.icon.getIntrinsicWidth())/2 + key.icon
                    .getIntrinsicWidth(), key.y + (key.height - key.icon.getIntrinsicHeight())/2 + key.icon
                    .getIntrinsicHeight());
            key.icon.draw(canvas);
        }

    }
}