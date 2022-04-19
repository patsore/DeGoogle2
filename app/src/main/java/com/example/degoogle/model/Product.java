package com.example.degoogle.model;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.PropertyName;

import java.util.ArrayList;

import kotlin.jvm.JvmStatic;

public class Product {
    @PropertyName("name")
    private String name;
    @PropertyName("packageName")
    private String packageName;
    @PropertyName("icon")
    private String icon;
    @PropertyName("version")
    private String version;
    @PropertyName("size")
    private String size;
    @PropertyName("lastUpdate")
    private String lastUpdate;
    @PropertyName("category")
    private String category;
    @PropertyName("developer")
    private String developer;
    @PropertyName("description")
    private String description;
    @PropertyName("downloadUrl")
    private String downloadUrl;
    private String gitLink;
    private ArrayList<String> imageCarousel;


    public Product() {
    }

    public ArrayList<String> getImageCarousel() {
        return imageCarousel;
    }

    public void setImageCarousel(ArrayList<String> imageCarousel) {
        this.imageCarousel = imageCarousel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getGitLink() {
        return gitLink;
    }

    public void setGitLink(String gitLink) {
        this.gitLink = gitLink;
    }
    @BindingAdapter("loadImage")
    @JvmStatic
    public static void loadImage(ImageView view, String imageUrl) {
        Glide.with(view.getContext())
                .load(imageUrl)
                .into(view);
    }
}
