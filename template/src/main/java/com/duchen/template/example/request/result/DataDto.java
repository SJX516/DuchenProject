package com.duchen.template.example.request.result;

import com.duchen.template.concept.model.LegalModel;


public class DataDto implements LegalModel {

    public static final int TYPE_YY_ITEM = 1;
    public static final int TYPE_ZZ_ITEM = 2;

    private int type;
    private String title;
    private String description;
    private String imageUrl;

    public int getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public boolean check() {
        return false;
    }
}
