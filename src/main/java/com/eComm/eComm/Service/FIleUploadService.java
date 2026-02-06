package com.eComm.eComm.Service;

import org.springframework.web.multipart.MultipartFile;

public interface FIleUploadService {

    String uploadFile(MultipartFile file);

    boolean deleteFile(String imgUrl);

}
