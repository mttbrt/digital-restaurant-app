package com.digital.api;

import com.digital.domain.entity.Item;
import com.digital.domain.entity.User;
import com.digital.repository.ItemRepository;
import com.digital.repository.UserRepository;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/")
public class ItemApi {

  private ItemRepository itemRepository;

  @Autowired
  public ItemApi(ItemRepository itemRepository) {
    this.itemRepository = itemRepository;
  }

  @RolesAllowed("ADMIN")
  @GetMapping("/items")
  public List<Item> getAllItems() {
    return itemRepository.findAll();
  }

}
