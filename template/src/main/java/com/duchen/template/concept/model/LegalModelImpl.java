package com.duchen.template.concept.model;

import com.duchen.template.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

public class LegalModelImpl implements LegalModel {

    @Override
    public boolean check() {
        return false;
    }

    public static boolean checkList(List<? extends LegalModel> list) {
        if (list == null) {
            return false;
        }
        for (int i = 0; i < list.size(); i++) {
            if (!list.get(i).check()) {
                return false;
            }
        }
        return true;
    }

    public static void removeInvalideItem(List<? extends LegalModel> list) {
        if (list == null) {
            return;
        }
        List<LegalModel> removed = new ArrayList<LegalModel>();
        for (LegalModel model : list) {
            if (!model.check()) {
                removed.add(model);
            }
        }
        list.removeAll(removed);
    }

    public static <T> void removeNullData(List<T> list) {
        if (list == null) return;
        List<T> removed = new ArrayList<T>();
        for (T dto : list) {
            if (dto == null) {
                removed.add(dto);
            }
        }
        list.removeAll(removed);
    }

    public static void logout(Class<?> cls) {
        LogUtil.w(LogUtil.TAG_MODEL_CHECK, cls.getName() + " legal model data");
    }
}
