package com.mmall.service.impl;

import com.google.common.collect.Lists;
import com.mmall.service.IFileService;
import com.mmall.util.FtpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;


/**
 * Created by duanpengyang on 17-8-5.
 */
@Service("iFileService")
public class FileServiceImpl implements IFileService {

    private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);
    public String upload(MultipartFile file,String path){
        String fileName = file.getOriginalFilename();
        String fileExtensionName = fileName.substring(fileName.lastIndexOf(".")+1);
        String uploadFileName = UUID.randomUUID().toString()+"."+fileExtensionName;
        logger.info("开始上传文件，上传文件的文件名：{},上传的路径：{},新文件名：{}",fileName,path,uploadFileName);

        File fileDir = new File(path);
        if (fileDir.exists()){
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }
        File targetFile = new File(path,uploadFileName);
        try {
            file.transferTo(targetFile);
            //文件已经上传成功

            //  将targetFILE 上传到我们的FTP服务器上。
            FtpUtil.uploadFile(Lists.newArrayList(targetFile));
            //文件已经上传到


            //  17-8-5 上传完成后，删除upload下边的文件
            targetFile.delete();
        } catch (IOException e) {
            logger.error("上传文件异常"+e );
            return null;
        }
        return targetFile.getName();

    }

}
