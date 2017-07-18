// IShop.aidl
package com.duchen.template.usage;

// Declare any non-default types here with import statements
import com.duchen.template.usage.MutilProcessConnect.Product;

interface IShop {

     String sell();

     Product buy();

     void setProduct(in Product product);

}
