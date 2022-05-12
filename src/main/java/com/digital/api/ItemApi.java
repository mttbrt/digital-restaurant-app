package com.digital.api;

import com.digital.domain.entity.Item;
import com.digital.repository.IItemRepository;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/")
public class ItemApi {

  private final IItemRepository itemRepository;

  @Autowired
  public ItemApi(IItemRepository itemRepository) {
    this.itemRepository = itemRepository;
  }

  @RolesAllowed("ADMIN")
  @GetMapping("/items")
  public List<Item> getAllItems() {
    return itemRepository.findAll();
  }

}
