package com.primetrade_ai.assignment.Controller;


import com.primetrade_ai.assignment.Payload.Response.MessageResponse;
import com.primetrade_ai.assignment.Security.Services.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/upload")
@CrossOrigin(origins = "*")
public class FileUploadController {

    @Autowired
    CloudinaryService cloudinaryService;

    @PostMapping
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            
            String url = cloudinaryService.uploadFile(file);

            
            Map<String, String> response = new HashMap<>();
            response.put("url", url);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Upload failed: " + e.getMessage()));
        }
    }
}