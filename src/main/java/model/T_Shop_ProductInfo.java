package model;

public class T_Shop_ProductInfo {
    int ProductId;
    String ProductName;
    String Size;
    Float Price;
    String PicPath;

    public String getSize() {
        return Size;
    }

    public void setSize(String size) {
        Size = size;
    }

    String Description;
    int Stock;
    int Selled;

    public int getStock() {
        return Stock;
    }

    public void setStock(int stock) {
        Stock = stock;
    }

    public int getProductId() {
        return ProductId;
    }

    public void setProductId(int productId) {
        ProductId = productId;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }


    public Float getPrice() {
        return Price;
    }

    public void setPrice(Float price) {
        Price = price;
    }

    public String getPicPath() {
        return PicPath;
    }

    public void setPicPath(String picPath) {
        PicPath = picPath;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }



    public int getSelled() {
        return Selled;
    }

    public void setSelled(int selled) {
        Selled = selled;
    }
}
