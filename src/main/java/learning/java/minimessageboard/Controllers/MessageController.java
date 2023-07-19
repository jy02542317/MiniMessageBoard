package learning.java.minimessageboard.Controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import learning.java.minimessageboard.Entities.TBFileEntity;
import learning.java.minimessageboard.Entities.TbMessageEntity;
import learning.java.minimessageboard.Services.FileServices;
import learning.java.minimessageboard.Services.MessageServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/createMessage")
    public TbMessageEntity createMessage(@RequestParam("file") MultipartFile[] files, @Valid @RequestParam String message, @Valid @RequestParam String title, @Valid @RequestParam Long roomId) {

        TbMessageEntity result = new TbMessageEntity();
        result.setMessage(message);
        result.setTitle(title);


        messageServices.saveMessage(result);
        if (null != files && files.length > 0) {
            List<TBFileEntity> list = new ArrayList();
            fileServices.SaveFile(list, files, result);
        }
        return messageServices.findMessageById(result.getId());
    }

    @GetMapping("/findAll/{page}")
    public Page<TbMessageEntity> findAll(@PathVariable int page, @RequestParam String key, @RequestParam String sortBy, @RequestParam int size, @RequestParam boolean desc,@RequestParam int roomId) {
        Sort sort;
        if (desc)
            sort = Sort.by(Sort.Direction.DESC, sortBy);
        else
            sort = Sort.by(Sort.Direction.ASC, sortBy);
        PageRequest pageRequest = PageRequest.of(page - 1, size, sort);
        return messageServices.findAll(pageRequest,key,roomId);
    }

    @GetMapping("/findById/{messageId}")
    public TbMessageEntity findById(@Valid @PathVariable Long messageId) {
        return messageServices.findMessageById(messageId);
    }

    @PutMapping("/updateMessage/{messageId}")
    public ResponseEntity<TbMessageEntity> updateMessage(@Valid @PathVariable Long messageId, @RequestBody TbMessageEntity tbMessageEntity) {
        TbMessageEntity tbMessage = messageServices.findMessageById(messageId);
        tbMessage.setMessage(tbMessageEntity.getMessage());
        tbMessage.setTitle(tbMessageEntity.getTitle());
        return new ResponseEntity<>(messageServices.saveMessage(tbMessage), HttpStatus.OK);
    }

    @DeleteMapping("/DeleteMessage/{messageId}")
    public void DeleteMessageById(@PathVariable Long messageId){
        messageServices.deleteMessageById(messageId);
    }


    @GetMapping("/download/downloadFile/{messageId}")
    public void downloadByMessageId(HttpServletRequest request, HttpServletResponse response, @PathVariable Long messageId) throws IOException {
        fileServices.downloadPathFileByMessageId(request, response, messageId);
    }
}
