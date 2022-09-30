package com.example.loginregister.controller;

import com.example.loginregister.model.ImageItem;
import com.example.loginregister.service.ImageItemService;
import com.example.loginregister.utils.Result;
import com.example.loginregister.utils.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Optional;
import java.util.UUID;

@RestController
public class ImageItemController {
    private static final int buffersize = 1024 * 1024;
    @Value("${upload.file.url}")
    String destpath;
    @Autowired
    ImageItemService imageItemService;

    @GetMapping("/download/{id}")
    public void downloadImage(@PathVariable Long id, HttpServletResponse response) throws IOException {
        Optional<ImageItem> optionalImageItem = imageItemService.findOne(id);
        if(optionalImageItem.isPresent()) {
            ImageItem imageItem = optionalImageItem.get();
            File file = new File(imageItem.getPath());

            response.reset();
            // response.setContentType("application/octet-stream");
            response.setContentLength((int) file.length());

            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
            OutputStream outputStream = response.getOutputStream();
            byte [] bytes = new byte[buffersize];

            int result = bufferedInputStream.read(bytes);
            while (result != -1) {
                outputStream.write(bytes, 0, result);
                result = bufferedInputStream.read(bytes);
            }

            outputStream.flush();
            outputStream.close();
            bufferedInputStream.close();
        }
    }

    @PostMapping("/upload")
    public Result<ImageItem> uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        String filename = file.getOriginalFilename();
        String filepath = destpath + filename + UUID.randomUUID().toString().substring(0, 16);
        File dest = new File(filepath);

        file.transferTo(dest);
        ImageItem imageItem = new ImageItem(null, filename, filepath);
        imageItem = imageItemService.insertOne(imageItem);

        return Result.Ok("upload ok", imageItem);
    }

    @DeleteMapping("/images/{id}")
    public Result<Status> deleteOne(@PathVariable Long id) {
        Optional<ImageItem> optionalImageItem = imageItemService.findOne(id);
        optionalImageItem.ifPresent(imageItem -> {
            imageItemService.deleteOne(id);
            File file = new File(imageItem.getPath());
            file.delete();
        });

        return Result.Ok("delete ok", Status.Ok);
    }
}
