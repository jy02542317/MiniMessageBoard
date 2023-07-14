package learning.java.minimessageboard.Controllers;

import jakarta.servlet.http.HttpServletRequest;
import learning.java.minimessageboard.Entities.TBFileEntity;
import learning.java.minimessageboard.Services.FileServices;
import learning.java.minimessageboard.Services.MessageServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/File")
public class FileController {
    @Autowired
    private FileServices fileServices;

    @Autowired
    private MessageServices messageServices;

    @ResponseBody
    @PostMapping(value = "/uploadFile")
    public List<TBFileEntity> uploadFile(@RequestParam("file") MultipartFile file,@RequestParam("message_id") Long message_id) {
        List<TBFileEntity> list =new ArrayList<>();
        //!file.isEmpty()判断文件的内容是否为空
        if (!file.isEmpty()) {

            // 获取文件名
            String fileName = file.getOriginalFilename();
            // 获取文件的后缀名

            String extension = fileName.substring(fileName.lastIndexOf("."));
            // 文件上传后的路径
            String filePath = "C:\\uploadFile\\";

            String newFileName=(new Date()).toGMTString();

            File dest = new File(filePath + newFileName+"."+extension);
            // 检测是否存在目录
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            try {
                file.transferTo(dest);
                TBFileEntity tbFileEntity=new TBFileEntity();
                tbFileEntity.setFilename(filePath + newFileName+"."+extension);
                tbFileEntity.setExtension(extension);
                tbFileEntity.setTbMessageEntity(messageServices.findMessageById(message_id));
                fileServices.saveFileInfo(tbFileEntity);
                log.info("文件上传成功！");
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
                log.error("文件上传失败！", e);
            }
        } else {
            log.info("文件上传失败！");
        }
        return list;

    }

}
