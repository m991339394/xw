package io.renren.common.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static java.time.LocalDate.now;

/**
 * @function 文件上传
 */
public class FileUpload {

    /**
     * @function  单文件上传
     * @return
     */
    public static String fileOne(MultipartFile file, String saveUrl, String fileType){
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String fileName = UUID.randomUUID() + suffix;
        String newUrl = saveUrl+now()+File.separator+fileName;  // /pro/pic/jpg
        System.out.println("文件路径：" + newUrl);
        String[] type_array = fileType.split(",");
        System.out.println("type  :  "+type_array.length);
        File saveFile = new File(StaticUtil.SAVE_URL_LINUX+newUrl);
        if(!saveFile.getParentFile().exists()){
            saveFile.getParentFile().mkdirs();
        }
        try {
            file.transferTo(saveFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return StaticUtil.STATIC_URL+newUrl;
    }


    /**
     * @function 多文件上传
     * @return
     */
    public static List<String> fileMany(MultipartFile[] files , String saveUrl, String fileType){
        List<String> picUrl = null;
        String newUrl = saveUrl + "\\" + now() + "\\pic\\";
        File saveDir = new File(newUrl);
        if(!saveDir.exists()){
            saveDir.mkdirs();
        }
        for(MultipartFile file : files){
            if(file != null){
                String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
                String fileName = UUID.randomUUID() + suffix;
                String newFileUrl = newUrl+fileName;
                File saveFile = new File(newFileUrl);
                try {
                    file.transferTo(saveFile);
                    picUrl.add(newFileUrl);
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("上传失败");
                }
            }
        }
        return picUrl;
    }

}
