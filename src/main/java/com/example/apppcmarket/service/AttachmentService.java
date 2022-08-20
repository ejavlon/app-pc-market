package com.example.apppcmarket.service;

import com.example.apppcmarket.entity.Attachment;
import com.example.apppcmarket.entity.AttachmentContent;
import com.example.apppcmarket.model.Result;
import com.example.apppcmarket.repository.AttachmentContentRepository;
import com.example.apppcmarket.repository.AttachmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;
import java.util.Optional;

@Service
public class AttachmentService {

    @Autowired
    AttachmentRepository attachmentRepository;

    @Autowired
    AttachmentContentRepository attachmentContentRepository;

    public void downloadFileById(Integer id, HttpServletResponse response) throws IOException {
        Optional<Attachment> optionalAttachment = attachmentRepository.findById(id);
        if (optionalAttachment.isPresent()) {
            Attachment attachment = optionalAttachment.get();
            Optional<AttachmentContent> optionalAttachmentContent = attachmentContentRepository.findByAttachment_Id(attachment.getId());
            if (optionalAttachmentContent.isPresent()) {
                AttachmentContent attachmentContent = optionalAttachmentContent.get();
                response.setContentType(attachment.getContentType());
                ServletOutputStream outputStream = response.getOutputStream();
                response.setHeader("Content-Disposition", "attachment; filename=\"" + attachment.getName() + "\"");
                FileCopyUtils.copy(attachmentContent.getBytes(), outputStream);
            }
        }
    }

    public Result uploadFile(MultipartHttpServletRequest request) throws IOException {
        Iterator<String> fileNames = request.getFileNames();
        Result result = new Result();
        if (fileNames.hasNext()) {
            while (fileNames.hasNext()) {
                MultipartFile file = request.getFile(fileNames.next());
                Attachment attachment = new Attachment();
                assert file != null;
                attachment.setContentType(file.getContentType());
                attachment.setName(file.getOriginalFilename());
                attachment.setSize(file.getSize());
                Attachment savedAttachment = attachmentRepository.save(attachment);
                AttachmentContent attachmentContent = new AttachmentContent();
                attachmentContent.setAttachment(savedAttachment);
                attachmentContent.setBytes(file.getBytes());
                attachmentContentRepository.save(attachmentContent);
            }
            result.setMessage("Fayl saqlandi");
            result.setSuccess(true);
        } else {
            result.setSuccess(false);
            result.setMessage("Fayl yo'q");
        }
        return result;
    }
}
