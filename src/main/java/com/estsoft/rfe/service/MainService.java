package com.estsoft.rfe.service;

import com.estsoft.rfe.utils.Utils;
import com.estsoft.rfe.vo.FileMetaInfoVo;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by joonho on 2017-04-12.
 */
@Service
public class MainService {

    public List<FileMetaInfoVo> dir( String uri){
        return Utils.dir(uri);
    }

    public boolean rename(String dir, String oldName, String newName){
        if( Files.exists( Paths.get(dir + "\\" + newName)) )
            return false;

        return Utils.move(dir, oldName, newName);
    }
}
