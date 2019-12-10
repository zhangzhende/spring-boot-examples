package com.zzd.spring.parent.seetaface.controller;

import com.seetaface2.model.SeetaRect;
import com.zzd.spring.parent.seetaface.config.FaceHelper;
import com.zzd.spring.parent.seetaface.config.SeetafaceBuilder;
import com.zzd.spring.parent.seetaface.config.model.Result;
import com.zzd.spring.parent.seetaface.dao.TFaceIndexDao;
import com.zzd.spring.parent.seetaface.dao.TUserFacesDao;
import com.zzd.spring.parent.seetaface.entity.TFaceIndex;
import com.zzd.spring.parent.seetaface.entity.TUserFaces;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * @Description 说明类的用途
 * @ClassName FaceController
 * @Author zzd
 * @Date 2019/12/2 13:52
 * @Version 1.0
 **/
@RestController
@RequestMapping(value = "/face")
public class FaceController {


    @Resource
    private TUserFacesDao tUserFacesDao;
    @Resource
    private TFaceIndexDao tFaceIndexDao;

    /**
     * 初始化加载数据到内存
     *
     * @return
     */
    @RequestMapping(value = "/prepare")
    public String prepare() {
        SeetafaceBuilder.loadFaceDb();
        return "ok";
    }

    /**
     * 添加人脸数据
     *
     * @param file
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/add")
    public String addFace(@RequestParam(value = "name") String name,
                          @RequestParam(value = "age") Integer age,
                          @RequestParam(value = "gender") Integer gender,
                          MultipartFile file) throws IOException {
        String key = UUID.randomUUID().toString().replace("-", "");
//        BufferedImage image = ImageIO.read(new ByteArrayInputStream(file.getBytes()));
        TUserFaces register = FaceHelper.register(key, file.getBytes());
        register.setAge(age);
        register.setName(name);
        register.setGender(gender);
        tUserFacesDao.insert(register);
        TFaceIndex tFaceIndex = new TFaceIndex();
        tFaceIndex.setFaceIndex(register.getFaceIndex());
        tFaceIndex.setKeyCode(register.getKeyCode());
        tFaceIndexDao.insert(tFaceIndex);
        return "success";
    }

    /**
     * 查询数据库里最像的人
     *
     * @param file
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/search")
    public String searchFace(MultipartFile file) throws IOException {
        Result search = FaceHelper.search(file.getBytes());
        search.setKey(tUserFacesDao.findKeyByIndex(search.getIndex()));
        return search.toString();
    }

    /**
     * 比较相似度
     *
     * @param file1
     * @param file2
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/compare")
    public String compareFace(MultipartFile file1, MultipartFile file2) throws IOException {
        float compare = FaceHelper.compare(file1.getBytes(), file2.getBytes());
        return compare + "";
    }

    /**
     * 截取人脸位置
     *
     * @param file
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/detect")
    public String detectFace(MultipartFile file) throws IOException {
        SeetaRect[] rects = FaceHelper.detect(file.getBytes());
        StringBuffer stringBuffer = new StringBuffer();
        if (rects != null) {
            for (SeetaRect rect : rects) {
                System.out.println("x=" + rect.getX() + ", y=" + rect.getY() + ", width=" + rect.getWidth() + ", height=" + rect.getHeight());
                stringBuffer.append("x=" + rect.getX() + ", y=" + rect.getY() + ", width=" + rect.getWidth() + ", height=" + rect.getHeight() + "    ");
            }
        }
        return stringBuffer.toString();
    }

    @RequestMapping(value = "/corp")
    public String corpFace(MultipartFile file) throws IOException {
        BufferedImage image = FaceHelper.crop(file.getBytes());
        String path = null;
        if (image != null) {
            path = "D:\\facepic\\corp\\" + UUID.randomUUID().toString().replace("-", "").substring(0, 8) + ".jpg";
            ImageIO.write(image, "jpg", new File(path));
        }
        return path;
    }

    @RequestMapping(value = "/delete")
    public String deleteFace(List<String> list) throws IOException {
        FaceHelper.removeRegister(list);
        return "ok";
    }

}
