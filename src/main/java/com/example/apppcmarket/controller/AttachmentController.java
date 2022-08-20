package com.example.apppcmarket.controller;

import com.example.apppcmarket.model.Result;
import com.example.apppcmarket.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/api/attachment")
public class AttachmentController {

    @Autowired
    AttachmentService attachmentService;

    @PostMapping("/upload")
    public HttpEntity<?> uploadFile(MultipartHttpServletRequest request) throws IOException {
        Result result = attachmentService.uploadFile(request);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(result);
    }

    @GetMapping("/download/{id}")
    public void downloadFileById(@PathVariable Integer id, HttpServletResponse response) throws IOException {
        attachmentService.downloadFileById(id, response);
    }
}
