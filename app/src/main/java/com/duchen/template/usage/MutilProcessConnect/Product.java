package com.duchen.template.usage.MutilProcessConnect;

import android.os.Parcel;
import android.os.Parcelable;

public class Product implements Parcelable {

    public String name;
    public int price;

    public Product(String name, int price) {
        this.name = name;
        this.price = price;
    }

    protected Product(Parcel in) {
        name = in.readString();
        price = in.readInt();
    }

    @Override
    public String toString() {
        return name + " ¥" + price;
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(price);
    }

    public void readFromParcel(Parcel dest) {
        name = dest.readString();
        price = dest.readInt();
    }
}
