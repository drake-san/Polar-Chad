package com.polarclan.polarchad.data.waifu;

import java.util.List;

public class Image {

    private String signature;
    private String extension;
    private int imageId;
    private int favorites;
    private String dominantColor;
    private String source;
    private Artist artist;
    private String uploadedAt;
    private String likedAt;
    private boolean isNsfw;
    private int width;
    private int height;
    private int byteSize;
    private String url;
    private String previewUrl;
    private List<Tag> tags;

    public String getSignature() {
        return signature;
    }

    public String getExtension() {
        return extension;
    }

    public int getImageId() {
        return imageId;
    }

    public int getFavorites() {
        return favorites;
    }

    public String getDominantColor() {
        return dominantColor;
    }

    public String getSource() {
        return source;
    }

    public Artist getArtist() {
        return artist;
    }

    public String getUploadedAt() {
        return uploadedAt;
    }

    public String getLikedAt() {
        return likedAt;
    }

    public boolean isNsfw() {
        return isNsfw;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public long getByteSize() {
        return byteSize;
    }

    public String getUrl() {
        return url;
    }

    public String getPreviewUrl() {
        return previewUrl;
    }

    public List<Tag> getTags() {
        return tags;
    }
}
