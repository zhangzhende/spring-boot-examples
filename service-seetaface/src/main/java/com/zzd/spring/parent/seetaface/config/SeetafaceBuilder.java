package com.zzd.spring.parent.seetaface.config;


import com.seetaface2.model.SeetaImageData;
import com.zzd.spring.parent.seetaface.dao.TFaceIndexDao;
import com.zzd.spring.parent.seetaface.dao.TUserFacesDao;
import com.zzd.spring.parent.seetaface.entity.TFaceIndex;
import com.zzd.spring.parent.seetaface.entity.TUserFaces;
import com.seetaface2.SeetaFace2JNI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Properties;

/**
 * @Author Sugar
 * @Version 2019/4/22 14:28
 */
@Component
public class SeetafaceBuilder {

    @Resource
    private TUserFacesDao tUserFacesDao;
    @Resource
    private TFaceIndexDao tFaceIndexDao;
    public static SeetafaceBuilder seetafaceBuilder;
    @PostConstruct
    public void myinit(){
        seetafaceBuilder=this;
        seetafaceBuilder.tUserFacesDao=this.tUserFacesDao;
        seetafaceBuilder.tFaceIndexDao=this.tFaceIndexDao;
        logger.info("myinit ready");
    }
    private static Logger logger = LoggerFactory.getLogger(SeetafaceBuilder.class);
    private volatile static SeetaFace2JNI seeta = null;


    public enum FacedbStatus {
        READY, LOADING, OK, INACTIV;
    }

    private volatile static FacedbStatus face_db_status = FacedbStatus.READY;

    public static SeetaFace2JNI build() {
        if (seeta == null) {
            synchronized (SeetafaceBuilder.class) {
                if (seeta != null) {
                    return seeta;
                }
                init( );
            }
        }
        return seeta;
    }

    /**
     * 建立人脸库索引
     */
    public static void buildIndex() {
        synchronized (SeetafaceBuilder.class) {
            while (face_db_status == FacedbStatus.LOADING || face_db_status == FacedbStatus.READY) {
                //等待之前的任务初始化完成
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
            face_db_status = FacedbStatus.READY;
            new Thread(() -> {
                seeta.clear();
                loadFaceDb();
            }).start();
        }
    }

    /**
     * 返回人脸数据库状态
     *
     * @return
     */
    public static FacedbStatus getFaceDbStatus() {
        return face_db_status;
    }

    private static void init() {
        Properties prop = getConfig();
        String separator = System.getProperty("path.separator");
        String sysLib = System.getProperty("java.library.path");
        if (sysLib.endsWith(separator)) {
            System.setProperty("java.library.path", sysLib + prop.getProperty("libs.path", ""));
        } else {
            System.setProperty("java.library.path", sysLib + separator + prop.getProperty("libs.path", ""));
        }
        try {//使java.library.path生效
            Field sysPathsField = ClassLoader.class.getDeclaredField("sys_paths");
            sysPathsField.setAccessible(true);
            sysPathsField.set(null, null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        String[] libs = prop.getProperty("libs", "").split(",");
        for (String lib : libs) {
            logger.debug("load library: {}", lib);
            System.loadLibrary(lib);
        }
        String bindata = prop.getProperty("bindata.dir");
        logger.debug("bindata dir: {}", bindata);
        seeta = new SeetaFace2JNI();
        seeta.initModel(bindata);
        logger.info("Seetaface init completed!!!");

//        数据库数据加载到内存
//        loadFaceDb();

        face_db_status = FacedbStatus.OK;
    }

    /**
     * 加载人脸库
     */
    public static void loadFaceDb() {

        logger.info("load face data...");
        seetafaceBuilder.tFaceIndexDao.clearAll();
        int pageNo = 0, pageSize = 100;
        while (true) {
            List<TUserFaces> list = seetafaceBuilder.tUserFacesDao.queryAllByLimit(pageNo * pageSize, pageSize);
            if (list == null) {
                break;
            }
            list.forEach(face -> {
                try {
                    register(face.getKeyCode(), face);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            if (list.size() < pageSize) {
                break;
            }
            pageNo++;

        }
        face_db_status = FacedbStatus.OK;
    }

    /**
     * 将历史注册过的所有人脸重新加载到内存库中
     *
     * @param key  人脸照片唯一标识
     * @param face 人脸照片
     * @return
     * @throws IOException
     */
    private static TFaceIndex register(String key, TUserFaces face) {
        SeetaImageData imageData = new SeetaImageData(face.getWidth(), face.getHeight(), face.getChannel());
        imageData.data = face.getImageData();
        int index = seeta.register(imageData);
        if (index < 0) {
            logger.info("Register face fail: key={}, index={}", key, index);
            return null;
        }
        TFaceIndex faceIndex = new TFaceIndex();
        faceIndex.setKeyCode(key);
        faceIndex.setFaceIndex(index);
        seetafaceBuilder.tFaceIndexDao.insert(faceIndex);
        logger.info("Register face success: key={}, index={}", key, index);
        return faceIndex;
    }

    private static Properties getConfig() {
        Properties properties = new Properties();
        String location = "classpath:/seetaface.properties";
        try (InputStream is = new DefaultResourceLoader().getResource(location).getInputStream()) {
            properties.load(is);
            logger.debug("seetaface config: {}", properties.toString());
        } catch (IOException ex) {
            logger.error("Could not load property file:" + location, ex);
        }
        return properties;
    }
}
