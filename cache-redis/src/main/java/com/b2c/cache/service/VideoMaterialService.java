package com.b2c.cache.service;

import com.b2c.cache.util.FileToolUtil;
import com.b2c.cache.util.SystemConstant;
import com.b2c.cache.util.VideoEncodeUtil;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.UUID;

@Service
public class VideoMaterialService {

    public static  void main(String[] agrs) throws Exception {
        VideoMaterialService videoMaterialService = new VideoMaterialService();
        videoMaterialService.encryptVideo ("888.mp4");
    }

    /**
     * 删除
     * @param file
     */
    public static boolean deleteVideoFile(File file) {

        if (!file.exists()) {
            return false;
        }
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                //递归删除
                deleteVideoFile(f);
            }
        }
        return file.delete();

    }

    /**
     * 加密视频
     * @param fileName
     */
    public void encryptVideo(String fileName) {
        try {
            //要加密的文件路径
            String fileUrl =  SystemConstant.VIDEO_PATH + fileName;
            //加密后的文件路径
            String fileUrl_temp =  SystemConstant.VIDEO_PATH_ENCRYPT + UUID.randomUUID ()+"-"+fileName;
            //判断是否有加密，加密过无需在加密
            String password = VideoEncodeUtil.readFileLastByte(fileUrl,SystemConstant.VIDEO_ENCRYPT_DECRYPT_KEY.length ());
            //
            if(!SystemConstant.VIDEO_ENCRYPT_DECRYPT_KEY.equals (password)) {
                //不存在就创建
                FileToolUtil.judeDirExists(new File(SystemConstant.VIDEO_PATH_ENCRYPT));
                //拷贝文件进去
                FileToolUtil.copyFile (new File(fileUrl),new File(fileUrl_temp));
                //开始加密
                VideoEncodeUtil.encrypt(fileUrl_temp, SystemConstant.VIDEO_ENCRYPT_DECRYPT_KEY);
            }else {
                //直接拷贝进去
                FileToolUtil.copyFile (new File(fileUrl),new File(fileUrl_temp));
                System.out.println ("文件已经加密，已经拷贝进去");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 解密视频
     * @param fileName
     */
    public String decryptVideo(String fileName) throws Exception {

        File file = new File(SystemConstant.VIDEO_PATH_TEMP);
        FileToolUtil.judeDirExists(file);
        //解密
        return VideoEncodeUtil.decrypt(
                SystemConstant.VIDEO_PATH_ENCRYPT +  fileName,
                SystemConstant.VIDEO_PATH_TEMP +  fileName,
                SystemConstant.VIDEO_ENCRYPT_DECRYPT_KEY.length());
    }
}
