package com.company.apppcmarket.controller;

import com.company.apppcmarket.model.Result;
import com.company.apppcmarket.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/api/attachment")
public class AttachmentController {

    @Autowired
    private AttachmentService attachmentService;

    @PostMapping("/upload")
    public HttpEntity<?> uploadFile(MultipartHttpServletRequest request) throws IOException {
        Result result = attachmentService.uploadFile(request);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(result);
    }

    @GetMapping("/download/{id}")
    public void downloadFileById(@PathVariable Integer id, HttpServletResponse response) throws IOException {
        attachmentService.downloadFileById(id, response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editFile(@PathVariable Integer id, MultipartHttpServletRequest request) throws IOException {
        Result result = attachmentService.editFile(id,request);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.CONFLICT).body(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFile(@PathVariable Integer id) {
        Result result = attachmentService.deleteFile(id);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.NO_CONTENT : HttpStatus.CONFLICT).body(result);
    }


}
