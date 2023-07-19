package learning.java.minimessageboard.Services;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import learning.java.minimessageboard.Entities.TBFileEntity;
import learning.java.minimessageboard.Entities.TbMessageEntity;
import learning.java.minimessageboard.Repository.FileRepository;
import learning.java.minimessageboard.Repository.MessageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URLEncoder;
import java.util.List;
import java.util.zip.Adler32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
@Transactional
@Slf4j
public class FileServices {
    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private MessageRepository messageRepository;

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

    public void downloadPathFile(HttpServletRequest request, HttpServletResponse response,Long file_id) throws IOException {
        String path=fileRepository.findById(file_id).get().getFilename();
        String filenames = path.substring(path.lastIndexOf("\\")+1);
        String filename = new String(filenames.getBytes("iso8859-1"),"UTF-8");
        File file = new File(path);
        if(file.exists()){
            downloadFile(filename,file,response);
        }
    }

    private void downloadFile(String filename,File file,HttpServletResponse response) throws IOException {
        //设置响应头，控制浏览器下载该文件
        response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
        //读取要下载的文件，保存到文件输入流
        FileInputStream in= new FileInputStream(file);
        //创建输出流
        OutputStream out= response.getOutputStream();
        //缓存区
        byte buffer[] = new byte[1024];
        int len = 0;
        //循环将输入流中的内容读取到缓冲区中
        while((len = in.read(buffer)) > 0){
            out.write(buffer, 0, len);
        }
    }

    public void  downloadPathFileByMessageId(HttpServletRequest request, HttpServletResponse response,Long message_id) throws IOException{
        List<TBFileEntity> fileList=messageRepository.findById(message_id).get().getFileList();
        File zipFile = File.createTempFile("PIC" + java.util.UUID.randomUUID(), ".zip");
        FileOutputStream fot = new FileOutputStream(zipFile);
        // 为任何OutputStream产生校验，第一个参数是制定产生校验和的输出流，第二个参数是指定Checksum的类型 (Adler32(较快)和CRC32两种)
        CheckedOutputStream cos = new CheckedOutputStream(fot, new Adler32());
        // 用于将数据压缩成Zip文件格式
        ZipOutputStream zos = new ZipOutputStream(cos);

        for(TBFileEntity tbFileEntity:fileList){
            File file=new File(tbFileEntity.getFilename());
            InputStream inputStream = new FileInputStream(file);
            zos.putNextEntry(new ZipEntry(file.getName()));
            int bytesRead = 0;
            // 向压缩文件中输出数据
            while ((bytesRead = inputStream.read()) != -1) {
                zos.write(bytesRead);
            }
            inputStream.close();
            // 当前文件写完，写入下一个文件
            zos.closeEntry();
        }
        zos.close();
        downloadFile(zipFile.getName(),zipFile,response);
    }

    public void deleteFileById(Long Id) throws IOException{
        String filePath=fileRepository.findById(Id).get().getFilename();
        File file=new File(filePath);
        fileRepository.deleteById(Id);
        if(file.exists())
            file.delete();
    }
}
