package com.fuda.dc.lhtz.web.controller;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller("uploadController")
public class UploadController {

    /**
     * 通过Spring的autowired注解获取spring默认配置的request
     */
    @Autowired
    private HttpServletRequest request;

    @RequestMapping("/uploadview")
    public String uploadView() {
        return "upload";
    }

    @RequestMapping("/upload")
    public String upload(@RequestParam(value = "file", required = false) MultipartFile file,
            HttpServletRequest request, ModelMap modelMap) {
        boolean isSaved = saveFile(file);
        if (isSaved) {
            modelMap.addAttribute("fileUrl", request.getContextPath() + "/img/" + file.getOriginalFilename());
            return "result";
        } else {
            return "result";
        }
    }

    @RequestMapping("/uploads")
    public String uploads(@RequestParam(value = "file", required = false) MultipartFile[] files,
            HttpServletRequest request, ModelMap modelMap) {
        if (files != null && files.length > 0) {
            for (MultipartFile file : files) {
                saveFile(file);
            }
        }

        modelMap.addAttribute("fileUrl", request.getContextPath() + "/img/" + "");
        return "result";
    }

    private boolean saveFile(MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                String realPath = request.getSession().getServletContext().getRealPath("img");
                File dstFile = new File(realPath, file.getOriginalFilename());
                file.transferTo(dstFile);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
