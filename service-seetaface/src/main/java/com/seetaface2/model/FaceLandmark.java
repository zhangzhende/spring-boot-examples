package com.seetaface2.model;

public class FaceLandmark {
    public SeetaRect[] rects;
    public SeetaPointF[] points;

    public SeetaRect[] getRects() {
        return rects;
    }

    public void setRects(SeetaRect[] rects) {
        this.rects = rects;
    }

    public SeetaPointF[] getPoints() {
        return points;
    }

    public void setPoints(SeetaPointF[] points) {
        this.points = points;
    }
}
