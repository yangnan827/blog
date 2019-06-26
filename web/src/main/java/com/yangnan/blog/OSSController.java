package com.yangnan.blog;

import com.yangnan.blog.result.Result;
import com.yangnan.blog.result.ResultBuilder;
import com.yangnan.blog.service.OSSService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/oss")
@Slf4j
public class OSSController {
    @Autowired
    private OSSService ossService;

    @PostMapping(path = "/uploadFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Result updateFile(@RequestParam("file") MultipartFile file) {
        return ResultBuilder.execute(() -> ossService.updateFile(file, "{}"));
    }

    @PostMapping(path = "/uploadFiles", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Result updateFiles(@RequestParam("files") List<MultipartFile> files) {
        return ResultBuilder.execute(() -> ossService.updateFiles(files, "{}"));
    }

}
