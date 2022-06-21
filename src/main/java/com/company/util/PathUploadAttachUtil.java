package com.company.util;

import org.springframework.beans.factory.annotation.Value;

import java.util.Calendar;

public class PathUploadAttachUtil {
    @Value("${attach.upload.folder")
    private String uploadFolder;
    @Value("${server.domain.name}")
    private String domainName;

    public String getYMDString() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int day = Calendar.getInstance().get(Calendar.DATE);

        return year + "/" + month + "/" + day; // 2022/04/23
    }

    public String getUploadFolder() {
        return uploadFolder;
    }

    public String getDomainName() {
        return domainName;
    }

    public String getOpenURL(String id) {
        return domainName + "/attach/open_general/" + id;
    }

    public String getDownloadURL(String id) {
        return domainName +"/attach/download/"+ id;
    }
}
