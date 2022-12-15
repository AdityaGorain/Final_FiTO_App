package com.example.fito.ui.food.edamamapi.image_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RootImageModel {

    @SerializedName("LARGE")
    @Expose()
    private LargeImage largeImage;

    @SerializedName("SMALL")
    @Expose()
    private SmallImage smallImage;

    @SerializedName("REGULAR")
    @Expose()
    private RegularImage regularImage;

    @SerializedName("THUMBNAIL")
    @Expose()
    private Thumbnail thumbnail;

    public RootImageModel(LargeImage largeImage, SmallImage smallImage, RegularImage regularImage, Thumbnail thumbnail) {
        this.largeImage = largeImage;
        this.smallImage = smallImage;
        this.regularImage = regularImage;
        this.thumbnail = thumbnail;
    }

    public LargeImage getLargeImage() {
        return largeImage;
    }

    public SmallImage getSmallImage() {
        return smallImage;
    }

    public RegularImage getRegularImage() {
        return regularImage;
    }

    public Thumbnail getThumbnail() {
        return thumbnail;
    }
}
