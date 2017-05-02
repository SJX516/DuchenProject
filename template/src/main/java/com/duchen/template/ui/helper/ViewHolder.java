package com.duchen.template.ui.helper;

import android.util.SparseArray;
import android.view.View;


public class ViewHolder {

    // I added a generic return type to reduce the casting noise in client code
    @SuppressWarnings("unchecked")
    public static <T extends View> T get(View view, int id) {
        SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
        if (viewHolder == null) {
            viewHolder = new SparseArray<>();
            view.setTag(viewHolder);
        }
        View childView = viewHolder.get(id);
        if (childView == null) {
            childView = view.findViewById(id);
            viewHolder.put(id, childView);
        }
        return (T) childView;
    }

    @SuppressWarnings("unchecked")
    public static <T extends View> T get(View view, int id, int keyForTag) {
        SparseArray<View> viewHolder = (SparseArray<View>) view.getTag(keyForTag);
        if (viewHolder == null) {
            viewHolder = new SparseArray<>();
            view.setTag(keyForTag, viewHolder);
        }
        View childView = viewHolder.get(id);
        if (childView == null) {
            childView = view.findViewById(id);
            viewHolder.put(id, childView);
        }
        return (T) childView;
    }

    @SuppressWarnings("unchecked")
    public static void hold(View view) {
        SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
        if (viewHolder == null) {
            viewHolder = new SparseArray<>();
            view.setTag(viewHolder);
        }
    }

    @SuppressWarnings("unchecked")
    public static void hold(View view, int keyForTag) {
        SparseArray<View> viewHolder = (SparseArray<View>) view.getTag(keyForTag);
        if (viewHolder == null) {
            viewHolder = new SparseArray<>();
            view.setTag(keyForTag, viewHolder);
        }
    }
}
