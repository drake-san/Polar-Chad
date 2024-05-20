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

    public String getUrl() {
        return url;
    }
    
}
