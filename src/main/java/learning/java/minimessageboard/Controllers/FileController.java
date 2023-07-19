package learning.java.minimessageboard.Controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import learning.java.minimessageboard.Entities.TBFileEntity;
import learning.java.minimessageboard.Services.FileServices;
import learning.java.minimessageboard.Services.MessageServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/File")
public class FileController {
    @Autowired
    private FileServices fileServices;

    @Autowired
    private MessageServices messageServices;
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    @PostMapping(value = "/uploadFile")
    public List<TBFileEntity> uploadFile(@RequestParam("file") MultipartFile[] files, @RequestParam("message_id") Long message_id) {
        List<TBFileEntity> list = new ArrayList<>();
        if (null != files && files.length > 0) {
          fileServices.SaveFile(list,files,messageServices.findMessageById(message_id));
        } else {
            log.info("文件上传失败！");
        }
        return list;
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/download/downloadFile")
    public void download(HttpServletRequest request, HttpServletResponse response,@RequestParam("id") Long id) throws IOException {
        //下载
         fileServices.downloadPathFile(request, response,id);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deleteFile/{id}")
    public void deleteFileById(@PathVariable Long id) throws IOException {
        fileServices.deleteFileById(id);
    }
}
