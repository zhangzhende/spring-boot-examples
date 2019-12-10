package com.seetaface2.model;

public class SeetaImageData {
    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getChannels() {
        return channels;
    }

    public void setChannels(int channels) {
        this.channels = channels;
    }

    private SeetaImageData() {

    }

    public SeetaImageData(int width, int height, int channels) {
        this.data = new byte[width * height * channels];
        this.width = width;
        this.height = height;
        this.channels = channels;
    }

    public SeetaImageData(int width, int height) {
        this(width, height, 3);
    }

    public byte[] data;
    public int width;
    public int height;
    public int channels;
}
