package com.ezen.boot_JPA.handler;


import lombok.extern.slf4j.Slf4j;

import java.io.File;

@Slf4j
public class FileDeleteHandler {

    private final String BASE_PATH = "D:\\_myProject\\_java\\_fileUpload\\";

    public int deleteFile(String saveDir, String uuid, String imageFileName){
        boolean isDel = false;
        File fileUuid = new File(uuid);
        File fileSaveDir = new File(saveDir);
        File removeFile = new File(BASE_PATH+fileSaveDir+File.separator+fileUuid+"_"+imageFileName);
        File removeThFile = new File(BASE_PATH+fileSaveDir+File.separator+fileUuid+"_th_"+imageFileName);

        if(removeFile.exists() || removeThFile.exists()) {
            isDel = removeFile.delete(); // 원래파일 삭제
            log.info(">>> removeFile !! > {} ", isDel);
            if(isDel) {
                isDel = removeThFile.delete();
                log.info(">>> removeThFile !! > {} ", isDel);
            }
        }

        return isDel ? 1 : 0 ;
    }


}
