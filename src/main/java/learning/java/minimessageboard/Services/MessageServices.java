package learning.java.minimessageboard.Services;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import learning.java.minimessageboard.Entities.TbMessageEntity;
import learning.java.minimessageboard.Repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;


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

    public List<TbMessageEntity> findAll() {
        return messageRepository.findAll();
    }


    /***
     *
     * @param key 搜索关键词
     * @param pages 第几页
     * @param rows 每页几条
     * @param desc 按照升序还是降序排列
     * @param sortBy 按照哪个字段排序
     * @return
     */

    /*public Page<TbMessageEntity> findBrandSearch(String key, Integer pages, Integer rows,
                                                 boolean desc, String sortBy) {
        ArrayList<String> strings = new ArrayList<>();
        PageRequest page;
        if (desc) {
            if (sortBy.equals("id"))
                strings.add("id");
            if (sortBy.equals("letter"))
                strings.add("letter");

            Sort sort = Sort.by(Sort.Direction.ASC,"id","title" );
            page =  PageRequest.of(pages - 1, rows, sort);

        } else
            page = PageRequest.of(pages - 1, rows);

        Page<TbMessageEntity> list = messageRepository.findAll(new Specification<TbMessageEntity>() {

            @Override
            public jakarta.persistence.criteria.Predicate toPredicate(Root<TbMessageEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

                ArrayList<Predicate> list = new ArrayList<>();

                Predicate id = criteriaBuilder.like(root.get("id").as(String.class), key);
                Predicate name = criteriaBuilder.like(root.get("name").as(String.class), "%" + key + "%");
                Predicate letter = criteriaBuilder.like(root.get("letter").as(String.class), "%" + key + "%");


                list.add(id);
                list.add(name);
                list.add(letter);



                Predicate[] array = new Predicate[list.size()];
                Predicate[] predicates = list.toArray(array);

                return criteriaBuilder.or(predicates);
            }
        }, page);
        return list;
    }*/
/*}*/


}
