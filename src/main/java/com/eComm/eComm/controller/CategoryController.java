package com.eComm.eComm.controller;

import com.eComm.eComm.Service.CategoryService;
import com.eComm.eComm.io.CategoryRequest;
import com.eComm.eComm.io.CategoryResponse;

import org.springframework.boot.json.JsonParseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@RestController
public class CategoryController
{
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {

        this.categoryService = categoryService;
    }


    @PostMapping("/admin/categories")
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponse addcategory(@RequestPart("category") String categoryString ,
                                        @RequestPart("file")MultipartFile file
    )
    {
        ObjectMapper objectMapper = new ObjectMapper();
        CategoryRequest request = null;
        try{
            request = objectMapper.readValue(categoryString, CategoryRequest.class);
            return categoryService.add(request,file);

        } catch (JsonParseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Exception occur while parsing the json"+e);
        }
    }

    @GetMapping("/categories")
    public List<CategoryResponse> featchcategories()
    {
        return  categoryService.read();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/admin/categories/{categoryId}")
    public void remove(@PathVariable String categoryId)
    {
        try{
            categoryService.delete(categoryId);
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
        }

    }
}
