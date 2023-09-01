package oa.mingdao.com.utils;

import java.io.Serializable;

public class OSSObj implements Serializable {

    private String fileKey;

    private Long fileSize;

    public String getFileKey() {
        return fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }
}
