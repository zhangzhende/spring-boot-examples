package com.zzd.service.arcsoft.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.codec.Base64;
import cn.hutool.core.collection.CollectionUtil;
import com.arcsoft.face.FaceInfo;
import com.arcsoft.face.toolkit.ImageFactory;
import com.arcsoft.face.toolkit.ImageInfo;
import com.zzd.service.arcsoft.entity.TUserFace;
import com.zzd.service.arcsoft.enums.ErrorCodeEnum;
import com.zzd.service.arcsoft.model.PictureDTO;
import com.zzd.service.arcsoft.model.SearchDTO;
import com.zzd.service.arcsoft.model.dto.AddFaceDTO;
import com.zzd.service.arcsoft.model.dto.FaceSearchResDto;
import com.zzd.service.arcsoft.model.dto.FaceUserInfo;
import com.zzd.service.arcsoft.model.dto.ProcessInfo;
import com.zzd.service.arcsoft.service.FaceEngineService;
import com.zzd.service.arcsoft.service.TUserFaceService;
import com.zzd.service.arcsoft.service.impl.FaceEngineServiceImpl;
import com.zzd.spring.common.entity.AjaxResult;
import com.zzd.spring.common.utils.AjaxResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

/**
 * @Description 说明类的用途
 * @ClassName FaceController
 * @Author zzd
 * @Date 2019/11/28 11:18
 * @Version 1.0
 **/
@RestController
@RequestMapping(value = "/face")
public class FaceController {

    @Autowired
    private FaceEngineServiceImpl faceEngineService;

    @Autowired
    private TUserFaceService tUserFaceService;

    /**
     * 人脸添加
     * @param param
     * @return
     */
    @RequestMapping(value = "/addFace")
    public AjaxResult<String> addFace(@RequestBody AddFaceDTO param) {
        byte[] decode = Base64.decode(base64Process(param.getPicture()));
        ImageInfo image = ImageFactory.getRGBData(decode);
        try {
//            特征提取
            byte[] bytes = faceEngineService.extractFaceFeature(image);
            if (bytes == null) {
                return new AjaxResult<String>(false, ErrorCodeEnum.NO_FACE_DETECTED.getCode().toString(), ErrorCodeEnum.NO_FACE_DETECTED.getDescription(), null);
            }
            TUserFace tUserFace = new TUserFace();
            tUserFace.setName(param.getName());
            tUserFace.setGroupId(param.getGroupId());
            tUserFace.setFaceFeature(bytes);
            tUserFace.setFaceId(UUID.randomUUID().toString().replaceAll("-", ""));
//            人脸特征入库
            tUserFaceService.insert(tUserFace);
            return new AjaxResult<String>(true, "ok", "0", null);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new AjaxResult<String>(false, "error", "500", null);
    }

    /**
     * 人脸识别，匹配，查找
     * @param param
     * @return
     * @throws IOException
     * @throws InterruptedException
     * @throws ExecutionException
     */
    @RequestMapping(value = "/search")
    public AjaxResult<FaceSearchResDto> searchFace(@RequestBody SearchDTO param) throws IOException, InterruptedException, ExecutionException {

            byte[] decode=Base64.decode(base64Process(param.getPicture()));
        BufferedImage bufImage = ImageIO.read(new ByteArrayInputStream(decode));
        ImageInfo imageInfo = ImageFactory.bufferedImage2ImageInfo(bufImage);

        byte[] bytes=faceEngineService.extractFaceFeature(imageInfo);
        if(null==bytes){
            return new AjaxResult<FaceSearchResDto>(false, ErrorCodeEnum.NO_FACE_DETECTED.getCode().toString(), ErrorCodeEnum.NO_FACE_DETECTED.getDescription(), null);
        }
        List<FaceUserInfo> userFaceInfoList = faceEngineService.compareFaceFeature(bytes, param.getGroupId());
        if(CollectionUtil.isNotEmpty(userFaceInfoList)){
                FaceUserInfo faceUserInfo = userFaceInfoList.get(0);
                FaceSearchResDto faceSearchResDto = new FaceSearchResDto();
                BeanUtil.copyProperties(faceUserInfo, faceSearchResDto);
                List<ProcessInfo> processInfoList = faceEngineService.process(imageInfo);
                if (CollectionUtil.isNotEmpty(processInfoList)) {
                    //人脸检测
                    List<FaceInfo> faceInfoList = faceEngineService.detectFaces(imageInfo);
                    int left = faceInfoList.get(0).getRect().getLeft();
                    int top = faceInfoList.get(0).getRect().getTop();
                    int width = faceInfoList.get(0).getRect().getRight() - left;
                    int height = faceInfoList.get(0).getRect().getBottom() - top;

                    Graphics2D graphics2D = bufImage.createGraphics();
                    graphics2D.setColor(Color.RED);//红色
                    BasicStroke stroke = new BasicStroke(5f);
                    graphics2D.setStroke(stroke);
                    graphics2D.drawRect(left, top, width, height);
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    ImageIO.write(bufImage, "jpg", outputStream);
                    byte[] bytes1 = outputStream.toByteArray();
                    faceSearchResDto.setImage("data:image/jpeg;base64," + Base64Utils.encodeToString(bytes1));
                    faceSearchResDto.setAge(processInfoList.get(0).getAge());
                    faceSearchResDto.setGender(processInfoList.get(0).getGender().equals(1) ? "女" : "男");

                }
                return new AjaxResult<FaceSearchResDto>(true, "ok", "0", faceSearchResDto);
        }
        return new AjaxResult<FaceSearchResDto>(false, "error", "500", null);

    }

    @RequestMapping(value = "/detectFace")
    public List<FaceInfo> detectFace(@RequestBody PictureDTO param)  {
        byte[] decode = Base64.decode(base64Process(param.getPicture()));
        InputStream inputStream = new ByteArrayInputStream(decode);
        ImageInfo imageInfo = ImageFactory.getRGBData(inputStream);

        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        List<FaceInfo> faceInfoList = faceEngineService.detectFaces(imageInfo);

        return faceInfoList;
    }








    private String base64Process(String base64Str) {
        if (!StringUtils.isEmpty(base64Str)) {
            String photoBase64 = base64Str.substring(0, 30).toLowerCase();
            int indexOf = photoBase64.indexOf("base64,");
            if (indexOf > 0) {
                base64Str = base64Str.substring(indexOf + 7);
            }

            return base64Str;
        } else {
            return "";
        }
    }
}
