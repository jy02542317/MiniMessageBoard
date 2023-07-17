package learning.java.minimessageboard.Controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import learning.java.minimessageboard.Entities.TBFileEntity;
import learning.java.minimessageboard.Entities.TbMessageEntity;
import learning.java.minimessageboard.Services.FileServices;
import learning.java.minimessageboard.Services.MessageServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/Message")
public class MessageController {
    @Autowired
    private MessageServices messageServices;

    @Autowired
    private FileServices fileServices;

    @PostMapping("/SaveMessage")
    public TbMessageEntity createAndSaveMessage(@RequestParam("file") MultipartFile[] files, @Valid @RequestParam String message, @Valid @RequestParam String title, @Valid @RequestParam Long roomId) {

        TbMessageEntity result = new TbMessageEntity();
        result.setMessage(message);
        result.setTitle(title);
        result.setRoomId(roomId.intValue());

        messageServices.saveMessage(result);
        if (null != files && files.length > 0) {
            List<TBFileEntity> list = new ArrayList();
            fileServices.SaveFile(list, files, result);
        }
        return messageServices.findMessageById((long) result.getId());
    }

    @GetMapping("/findAll")
    public List<TbMessageEntity> findAll() {
        return messageServices.findAll();
    }

    @GetMapping("/findById")
    public TbMessageEntity findById(@Valid @RequestParam Long messageId) {
        return messageServices.findMessageById(messageId);
    }

    @GetMapping("/download/downloadFile")
    public void downloadByMessageId(HttpServletRequest request, HttpServletResponse response, @RequestParam("id") Long id) throws IOException {
        //下载
        fileServices.downloadPathFileByMessageId(request, response, id);
    }
}
