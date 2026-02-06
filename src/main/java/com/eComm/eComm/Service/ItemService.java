package com.eComm.eComm.Service;

import com.eComm.eComm.io.ItemRequest;
import com.eComm.eComm.io.ItemResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ItemService
{
   ItemResponse add(ItemRequest request, MultipartFile file);

   List<ItemResponse> fetchItems();

   void deleteItem(String itemId);
}
