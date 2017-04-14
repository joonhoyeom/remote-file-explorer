package com.estsoft.rfe.vo;

/**
 * Created by joonho on 2017-04-12.
 */
public class FileMetaInfoVo {

    private String name;
    private String time;
    private String fileType;
    private long size;

    public FileMetaInfoVo() {}

    public FileMetaInfoVo(String name, String time, String fileType, long size) {
        this.name = name;
        this.time = time;
        this.fileType = fileType;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}
