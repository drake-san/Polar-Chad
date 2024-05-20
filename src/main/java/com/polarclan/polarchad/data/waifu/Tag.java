package com.polarclan.polarchad.data.waifu;

public class Tag {
    private int tagId;
    private String name;
    private String description;
    private boolean isNsfw;

    public int getTagId() {
        return tagId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isNsfw() {
        return isNsfw;
    }
}
