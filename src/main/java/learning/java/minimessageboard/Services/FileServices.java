package learning.java.minimessageboard.Services;

import learning.java.minimessageboard.Entities.TBFileEntity;
import learning.java.minimessageboard.Entities.TbMessageEntity;
import learning.java.minimessageboard.Repository.FileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@Transactional
@Slf4j
public class FileServices {
    @Autowired
    private FileRepository fileRepository;

    public TBFileEntity saveFileInfo(TBFileEntity tbFileEntity){
        return fileRepository.save(tbFileEntity);
    }

    public void SaveFile(List<TBFileEntity> list, MultipartFile[] files, TbMessageEntity tbMessageEntity){
        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                // 获取文件名
                String fileName = file.getOriginalFilename();
                // 获取文件的后缀名

                String extension = fileName.substring(fileName.lastIndexOf("."));
                // 文件上传后的路径
                String filePath = "C:\\uploadFile\\";

                String newFileName = "PIC" + java.util.UUID.randomUUID();

                File dest = new File(filePath + newFileName + extension);
                // 检测是否存在目录
                if (!dest.getParentFile().exists()) {
                    dest.getParentFile().mkdirs();
                }
                try {
                    file.transferTo(dest);
                    TBFileEntity tbFileEntity = new TBFileEntity();
                    tbFileEntity.setFilename(filePath + newFileName + extension);
                    tbFileEntity.setExtension(extension);
                    tbFileEntity.setTbMessageEntity(tbMessageEntity);
                    saveFileInfo(tbFileEntity);
                    list.add(tbFileEntity);
                    log.info("文件上传成功！");
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                    log.error("文件上传失败！", e);
                }
            }
        }
    }
}
