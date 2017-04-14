package com.estsoft.rfe.service;

import com.estsoft.rfe.utils.Utils;
import com.estsoft.rfe.vo.FileMetaInfoVo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by joonho on 2017-04-12.
 */
@Service
public class MainService {

    public List<FileMetaInfoVo> dir( String uri){
        return Utils.dir(uri);
    }

}
