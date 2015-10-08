package com.fuda.dc.lhtz.web.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DownloadCrontroller {

    public static final String IMG_PATH = "img";

    @Autowired
    private HttpServletRequest request;

    @RequestMapping("/download")
    public ModelAndView download() {
        String path = request.getSession().getServletContext().getRealPath(IMG_PATH);
        File dir = new File(path);
        //ModelAndView mv = new ModelAndView("download", "files", dir.list());
        ModelAndView mv = new ModelAndView("download", "fileNames", dir.list());
        return mv;
    }

    @RequestMapping("/getfile")
    public void downloadFile(@RequestParam("name") String name, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        if (!checkFileSafe(name) || name.length() == 0) {
            return;
        }

        OutputStream os = new BufferedOutputStream(response.getOutputStream());
        try {
            String fileName = request.getSession().getServletContext().getRealPath(IMG_PATH) + File.separator + name;
            response.reset();
            response.setHeader("Content-Disposition", "attachment; filename="
                    + new String(name.getBytes("UTF-8"), "ISO-8859-1"));
            response.setContentType("application/octet-stream;charset=UTF-8");
            os.write(FileUtils.readFileToByteArray(new File(fileName)));
            os.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private boolean checkFileSafe(String filePath) {
        return !filePath.contains("../");
    }

}
