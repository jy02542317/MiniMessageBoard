package learning.java.minimessageboard.Services;

import learning.java.minimessageboard.Entities.TBFileEntity;
import learning.java.minimessageboard.Entities.TbMessageEntity;
import learning.java.minimessageboard.Repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MessageServices {
    @Autowired
    private MessageRepository messageRepository;

    public TbMessageEntity saveMessage(TbMessageEntity tbMessageEntity){
        return messageRepository.save(tbMessageEntity);
    }

    public TbMessageEntity findMessageById(Long id){
        return messageRepository.findById(id).orElse(new TbMessageEntity());
    }

    public List<TbMessageEntity> findAll() {
        return messageRepository.findAll();
    }
}
