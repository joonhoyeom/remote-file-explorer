package com.estsoft.rfe.controller;

import com.estsoft.rfe.annotation.Auth;
import com.estsoft.rfe.annotation.AuthAdmin;
import com.estsoft.rfe.service.MainService;
import com.estsoft.rfe.utils.Utils;
import com.estsoft.rfe.vo.AdminVo;
import com.estsoft.rfe.vo.FileMetaInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by joonho on 2017-04-05.
 */
@Controller
public class MainController {

    @Autowired
    MainService mainService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String main(@AuthAdmin AdminVo adminVo, Model model) {
        if (adminVo == null)
            return "login";

        List<FileMetaInfoVo> list = mainService.dir("C:\\");

        model.addAttribute("currentDirectory", "C:\\");
        model.addAttribute("dirEntryList", list);
        return "main";
    }

    @Auth
    @RequestMapping(value = "/dir", method = RequestMethod.POST)
    public String dir(@RequestParam(value = "path") String path, Model model) {

        if (path.contains("C:\\") == false)
            path = "C:\\";

        List<FileMetaInfoVo> list = mainService.dir(path);
        model.addAttribute("currentDirectory", path);
        model.addAttribute("dirEntryList", list);

        return "main";
    }

    //todo
    @Auth
    @RequestMapping(value = "/ajaxdir", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> ajaxDir(@RequestParam(value = "path") String path, Model model) {

        if (path.contains("C:\\") == false)
            path = "C:\\";

        List<FileMetaInfoVo> list = mainService.dir(path);

        Map<String, Object> map = new HashMap<String, Object>();
        if (list != null) {
            map.put("result", "success");
            map.put("data", list);
        } else
            map.put("result", "error");
        return map;
    }

    @Auth
    @RequestMapping(value = "/file-download", method = RequestMethod.POST)
    public Map<String, Object> fileDownload(@RequestParam(value = "path") String path, HttpServletResponse response, Model model) {

        Map<String, Object> map = new HashMap<String, Object>();

        File file = new File(path);
        if (!file.exists()) {
            System.err.println(path + " : doesn't exist");
            map.put("result", "fail");
            return map;
        }

        String mimeType = URLConnection.guessContentTypeFromName(file.getName());
        if (mimeType == null) {
            System.out.println("mimetype is not detectable, will take default");
            mimeType = "application/octet-stream";
        }

        response.setContentType(mimeType);

        /* "Content-Disposition : inline" will show viewable types [like images/text/pdf/anything viewable by browser] right on browser
            while others(zip e.g) will be directly downloaded [may provide save as popup, based on your browser setting.]*/
        response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));


        /* "Content-Disposition : attachment" will be directly download, may provide save as popup, based on your browser setting*/
        //response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));

        response.setContentLength((int) file.length());

        InputStream inputStream = null;
        try {
            inputStream = new BufferedInputStream(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                    map.put("result", "fail");
                    return map;
                }
            }
            map.put("result", "fail");
            return map;
        }

        //Copy bytes from source to destination(outputstream in this example), closes both streams.
        try {
            FileCopyUtils.copy(inputStream, response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return map;
    }


    @Auth
    @RequestMapping(value = "/file-upload", method = RequestMethod.POST)
    public String fileUpload(@RequestParam(value = "savePath") String savePath, MultipartHttpServletRequest request, Model model) {
        System.out.println(savePath);
        Iterator<String> itr = request.getFileNames();
        MultipartFile mpf = request.getFile(itr.next());

        boolean isSaved = Utils.saveMultiPartFile(savePath, mpf);

        //multipart file save fail
        if (isSaved == false){
            System.out.println("[Error] MultipartFile save fail");
        }

        List<FileMetaInfoVo> list = mainService.dir(savePath);
        model.addAttribute("currentDirectory", savePath);
        model.addAttribute("dirEntryList", list);

        return "main";
    }

    @Auth
    @RequestMapping(value = "/rename", method = RequestMethod.POST)
    public String rename(@RequestParam(value = "cur-dir") String curDir,
                         @RequestParam(value = "old-name") String oldName,
                         @RequestParam(value = "new-name") String newName, Model model) {
        //todo Rename Fail message
        mainService.rename(curDir, oldName, newName);
        List<FileMetaInfoVo> list = mainService.dir(curDir);

        model.addAttribute("currentDirectory", curDir);
        model.addAttribute("dirEntryList", list);

        return "main";
    }

    @Auth
    @RequestMapping(value = "/move", method = RequestMethod.POST)
    public String move(@RequestParam(value = "path") String path, Model model) {

        return "main";
    }

    @Auth
    @RequestMapping(value = "/copy", method = RequestMethod.POST)
    public String copy(@RequestParam(value = "path") String path, Model model) {

        return "main";
    }

}