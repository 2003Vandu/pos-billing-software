package com.eComm.eComm.Service;

import com.eComm.eComm.io.CategoryRequest;
import com.eComm.eComm.io.CategoryResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CategoryService
{
    CategoryResponse add(CategoryRequest request , MultipartFile file);

    List<CategoryResponse> read();

    void delete(String categoryid);
}
