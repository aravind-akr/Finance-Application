package com.aravind.finance.models;

public class CatSubCatModel {

    private String subCategory;
    private String categoryName;
    private int categoryId;

    public CatSubCatModel(String subCategory, String categoryName, int categoryId) {
        this.subCategory = subCategory;
        this.categoryName = categoryName;
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return "CatSubCatModel{" +
                "subCategory='" + subCategory + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", categoryId=" + categoryId +
                '}';
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
