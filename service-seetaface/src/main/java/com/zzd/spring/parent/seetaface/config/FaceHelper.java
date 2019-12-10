package com.zzd.spring.parent.seetaface.config;


import com.seetaface2.SeetaFace2JNI;
import com.seetaface2.model.FaceLandmark;
import com.seetaface2.model.RecognizeResult;
import com.seetaface2.model.SeetaImageData;
import com.seetaface2.model.SeetaRect;
import com.zzd.spring.parent.seetaface.config.model.*;
import com.zzd.spring.parent.seetaface.dao.TUserFacesDao;
import com.zzd.spring.parent.seetaface.entity.TUserFaces;
import com.zzd.spring.parent.seetaface.utils.ImageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @Author Sugar
 * @Version 2019/4/22 15:56
 */
public class FaceHelper {

    private static Logger logger = LoggerFactory.getLogger(FaceHelper.class);

    private static int CROP_SIZE = 256 * 256 * 3;

    private static SeetaFace2JNI seeta = SeetafaceBuilder.build();

    /**
     * 人脸比对
     *
     * @param img1
     * @param img2
     * @return 相似度
     * @throws IOException
     */
    public  static float compare(File img1, File img2) throws IOException {
        BufferedImage image1 = ImageIO.read(img1);
        BufferedImage image2 = ImageIO.read(img2);
        return compare(image1, image2);
    }

    /**
     * 人脸比对
     *
     * @param img1
     * @param img2
     * @return 相似度
     */
    public static  float compare(byte[] img1, byte[] img2) throws IOException {
        BufferedImage image1 = ImageIO.read(new ByteArrayInputStream(img1));
        BufferedImage image2 = ImageIO.read(new ByteArrayInputStream(img2));
        return compare(image1, image2);
    }

    /**
     * 人脸比对
     *
     * @param image1
     * @param image2
     * @return 相似度
     */
    public static float compare(BufferedImage image1, BufferedImage image2) {
        if (image1 == null || image2 == null) {
            return 0;
        }
        SeetaImageData imageData1 = new SeetaImageData(image1.getWidth(), image1.getHeight(), 3);
        imageData1.data = ImageUtils.getMatrixBGR(image1);

        SeetaImageData imageData2 = new SeetaImageData(image2.getWidth(), image2.getHeight(), 3);
        imageData2.data = ImageUtils.getMatrixBGR(image2);

        return seeta.compare(imageData1, imageData2);
    }

    /**
     * 注册人脸(会对人脸进行裁剪)
     *
     * @param key 人脸照片唯一标识
     * @param img 人脸照片
     * @return
     * @throws IOException
     */
    public static  TUserFaces register(String key, byte[] img) throws IOException {
        BufferedImage image = ImageIO.read(new ByteArrayInputStream(img));
        //先对人脸进行裁剪
        SeetaImageData imageData = new SeetaImageData(image.getWidth(), image.getHeight(), 3);
        imageData.data = ImageUtils.getMatrixBGR(image);
        byte[] bytes = seeta.crop(imageData);

        if (bytes == null || bytes.length != CROP_SIZE) {
            logger.info("register face fail: key={}, error=no valid face", key);
            return null;
        }
        imageData = new SeetaImageData(256, 256, 3);
        imageData.data = bytes;
        int index = seeta.register(imageData);
        if (index < 0) {
            logger.info("register face fail: key={}, index={}", key, index);
            return null;
        }
        TUserFaces face = new TUserFaces();
        face.setKeyCode(key);
        face.setImageData(imageData.data);
        face.setWidth(imageData.width);
        face.setHeight(imageData.height);
        face.setChannel(imageData.channels);
        face.setFaceIndex(index);
//        tUserFacesDao.insert(face);
        logger.info("Register face success: key={}, index={}", key, index);
        return face;
    }

    /**
     * 注册人脸(不裁剪图片)
     *
     * @param key 人脸照片唯一标识
     * @param image 人脸照片
     * @return
     * @throws IOException
     */
    public static TUserFaces register(String key, BufferedImage image) throws IOException {
        SeetaImageData imageData = new SeetaImageData(image.getWidth(), image.getHeight(), 3);
        imageData.data = ImageUtils.getMatrixBGR(image);
        int index = seeta.register(imageData);
        if (index < 0) {
            logger.info("register face fail: key={}, index={}", key, index);
            return null;
        }
        TUserFaces face = new TUserFaces();
        face.setKeyCode(key);
        face.setImageData(imageData.data);
        face.setWidth(imageData.width);
        face.setHeight(imageData.height);
        face.setChannel(imageData.channels);
        face.setFaceIndex(index);
//        tUserFacesDao.insert(face);
        logger.info("Register face success: key={}, index={}", key, index);
        return face;
    }

    /**
     * 搜索人脸
     *
     * @param img 人脸照片
     * @return
     * @throws IOException
     */
    public static  Result search(byte[] img) throws IOException {
        BufferedImage image = ImageIO.read(new ByteArrayInputStream(img));
        return search(image);
    }

    /**
     * 搜索人脸
     *
     * @param image 人脸照片
     * @return
     * @throws IOException
     */
    public  static Result search(BufferedImage image) {
        if (image == null) {
            return null;
        }
        SeetaImageData imageData = new SeetaImageData(image.getWidth(), image.getHeight(), 3);
        imageData.data = ImageUtils.getMatrixBGR(image);
        RecognizeResult rr = seeta.recognize(imageData);

        if (rr == null || rr.index == -1) {
            return null;
        }
        Result result = new Result(rr);
        result.setIndex(rr.index);
        return result;
    }

    /**
     * 人脸提取（裁剪）
     *
     * @param img
     * @return return cropped face
     */
    public static BufferedImage crop(byte[] img) throws IOException {
        BufferedImage image = ImageIO.read(new ByteArrayInputStream(img));
        return crop(image);
    }

    /**
     * 人脸提取（裁剪）
     *
     * @param image
     * @return return cropped face
     */
    public static BufferedImage crop(BufferedImage image) {
        if (image == null) {
            return null;
        }
        SeetaImageData imageData = new SeetaImageData(image.getWidth(), image.getHeight(), 3);
        imageData.data = ImageUtils.getMatrixBGR(image);
        byte[] bytes = seeta.crop(imageData);
        if (bytes == null || bytes.length != CROP_SIZE) {
            return null;
        }
        return ImageUtils.bgrToBufferedImage(bytes, 256,256);
    }

    /**
     * 人脸识别
     *
     * @param img
     * @return
     */
    public static SeetaRect[] detect(byte[] img) throws IOException {
        BufferedImage image = ImageIO.read(new ByteArrayInputStream(img));
        return detect(image);
    }

    /**
     * 人脸识别
     *
     * @param image
     * @return
     */
    public static SeetaRect[] detect(BufferedImage image) {
        if (image == null) {
            return null;
        }
        SeetaImageData imageData = new SeetaImageData(image.getWidth(), image.getHeight(), 3);
        imageData.data = ImageUtils.getMatrixBGR(image);
        return seeta.detect(imageData);
    }

    /**
     * 人脸特征识别
     *
     * @param image
     * @return
     */
    public static FaceLandmark detectLandmark(BufferedImage image) {
        if (image == null) {
            return null;
        }
        SeetaImageData imageData = new SeetaImageData(image.getWidth(), image.getHeight(), 3);
        imageData.data = ImageUtils.getMatrixBGR(image);
        SeetaRect[] rects = seeta.detect(imageData);
        if (rects == null) {
            return null;
        }
        FaceLandmark faces = new FaceLandmark();
        faces.rects = rects;
        faces.points = seeta.detect(imageData, rects);
        return faces;
    }

    /**
     * 删除已注册的人脸
     * @param list
     */
    public static void removeRegister(List<String> list) {
//        tUserFacesDao.deleteFaceImgs(keys);//删除数据库的人脸
        SeetafaceBuilder.buildIndex();//重新建立索引
    }

    /**
     * 清除内存中人脸库数据
     */
    public static void clear() {
        seeta.clear();
    }
}
