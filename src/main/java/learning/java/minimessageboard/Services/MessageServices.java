package learning.java.minimessageboard.Services;

import jakarta.persistence.criteria.*;
import learning.java.minimessageboard.Entities.TbMessageEntity;
import learning.java.minimessageboard.Repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class MessageServices {
    @Autowired
    private MessageRepository messageRepository;

    public TbMessageEntity saveMessage(TbMessageEntity tbMessageEntity) {
        return messageRepository.save(tbMessageEntity);
    }

    public TbMessageEntity findMessageById(Long id) {
        return messageRepository.findById(id).orElse(new TbMessageEntity());
    }

    public Page<TbMessageEntity> findAll(Pageable pageable, String key, int roomId) {
        Specification<TbMessageEntity> sp = (root, query, cb) -> {
            Path<Object> message = root.get("message");
            Path<Object> roomID = root.get("roomId");
            Predicate predicate1 = cb.like(message.as(String.class), "%" + key + "%");
            Predicate predicate2 = cb.equal(roomID.as(Integer.class), roomId);
            return cb.and(predicate1, predicate2);
        };
        return messageRepository.findAll(sp, pageable);
    }

    public void deleteMessageById(Long id) {
        messageRepository.deleteById(id);
    }

}
