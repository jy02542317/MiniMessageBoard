package learning.java.minimessageboard.Services;

import learning.java.minimessageboard.Entities.TBFileEntity;
import learning.java.minimessageboard.Repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FileServices {
    @Autowired
    private FileRepository fileRepository;

    public TBFileEntity saveFileInfo(TBFileEntity tbFileEntity){
        return fileRepository.save(tbFileEntity);
    }
}
