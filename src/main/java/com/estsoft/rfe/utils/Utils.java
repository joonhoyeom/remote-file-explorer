package com.estsoft.rfe.utils;

import com.estsoft.rfe.vo.FileMetaInfoVo;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by joonho on 2017-04-11.
 */
public class Utils {

    public static boolean move(String curDir, String oldName, String newName){
        Path oldPath = Paths.get(curDir + "\\" + oldName);

        try {
            Files.move(oldPath, oldPath.resolveSibling(newName));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static void copyRecursive(Path src, Path targ) throws IOException {
        Files.walkFileTree(src, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Path targPath = targ.resolve(src.relativize(file));
                Files.copy(file, targPath);

                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                Path newDir = targ.resolve(src.relativize(dir));
                Files.createDirectory(newDir);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    public static List<FileMetaInfoVo> dir(String uri){

        if(uri == null)
            return null;
        uri = uri.trim();

        Path path = Paths.get(uri);
        //Invalid uri
        if(Files.notExists(path))
            return null;

        ArrayList<FileMetaInfoVo> list = new ArrayList<FileMetaInfoVo>();
        try {
            DirectoryStream<Path> dirStream = Files.newDirectoryStream(path);
            for(Path i : dirStream){
                String name = i.getFileName().toString();
                String time = Files.getLastModifiedTime(i).toString().replace("T", " ").substring(0, 19);
                String fileType = getFileType(i);
                long size = 0;
                if(Files.isRegularFile(i))
                    size = Files.size(i);

                FileMetaInfoVo metaData = new FileMetaInfoVo(name, time, fileType, size);
                list.add(metaData);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return list.isEmpty() ? null : list;
    }

    private static String getFileType(Path p) {
        String fileExtension = null;

        if (Files.isDirectory(p)) {
            return "dir";
        }
        fileExtension = p.toString();
        if (fileExtension.contains(".")) {
            fileExtension = fileExtension.substring(fileExtension.lastIndexOf("."));
            //end with dot(.) or there is no extension
            if(fileExtension.length() <= 1)
                fileExtension = "bin";
            else
                fileExtension = fileExtension.substring(1);
        }else
            return "bin";

        return fileExtension;
    }

    public static boolean saveMultiPartFile(String savePath, MultipartFile file){
        if (file.isEmpty() == false) {
            FileOutputStream fos = null;
            try {
                System.out.println(savePath + "\\" + file.getOriginalFilename());
                byte fileData[] = file.getBytes();
                fos = new FileOutputStream(savePath + "\\" + file.getOriginalFilename());
                fos.write(fileData);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            } finally {
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } else
            return false;
        return true;
    }
}
