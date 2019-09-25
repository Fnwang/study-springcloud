package com.b2c.cache.controller;

import com.b2c.cache.service.VideoMaterialService;
import com.b2c.cache.util.FileToolUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

@RestController
public class VController {

    Logger logger = LoggerFactory.getLogger (this.getClass ());

    @Autowired
    VideoMaterialService videoMaterialService ;

    /**
     * 播放加密MP4
     * @param response
     * @throws
     */
    @PostMapping("/play/{id}")
    @ResponseBody
    public void playMp4(HttpServletResponse response,
                        @PathVariable(value = "id",required = false) String id
    )  {
        String fileName = "";
        if("1".equals (id)) {
            fileName = "abe7a4e6-1f14-4ed5-b674-ed70eb927f08-888.mp4";
        }else{

            fileName = "f50e4600-f02c-4c4c-9c48-b9dfee0d5288-abc.wmv";
        }
        String tempFilePath = null;//SystemConstant.VIDEO_PATH_TEMP +  fileName;
        try {
            //解密
            tempFilePath = videoMaterialService.decryptVideo(fileName);
            System.out.println ("tempFilePath==>"+tempFilePath);
            //解密过后的临时文件路径
            FileInputStream inputStream = new FileInputStream(tempFilePath);
            byte[] data = FileToolUtil.inputStreamToByte(inputStream);

            String diskfilename = "tv.wmv";
            response.setContentType("video/mp4");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + diskfilename + "\"" );
            System.out.println("data.length ====》》》》》" + data.length);
            response.setContentLength(data.length);
            response.setHeader("Content-Range", "" + Integer.valueOf(data.length-1));
            response.setHeader("Accept-Ranges", "bytes");
            response.setHeader("Etag", "W/\"9767057-1323779115364\"");

            OutputStream os = response.getOutputStream();

            os.write(data);
            //先声明的流后关掉！
            os.flush();
            os.close();
            inputStream.close();

        }catch (Exception e){
            logger.error("错误信息=》》"+e.getMessage ());
        }finally {
            VideoMaterialService.deleteVideoFile(new File (tempFilePath));
            logger.info ("已经删掉临时文件");
        }

    }
}
