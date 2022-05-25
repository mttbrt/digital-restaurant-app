package com.mttbrt.digres.api;

import com.mttbrt.digres.domain.entity.Item;
import com.mttbrt.digres.repository.ItemRepository;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/")
public class ItemApi {

  private final ItemRepository itemRepository;

  public ItemApi(ItemRepository itemRepository) {
    this.itemRepository = itemRepository;
  }

  @RolesAllowed("ADMIN")
  @GetMapping("/items")
  public List<Item> getAllItems() {
    return itemRepository.findAll();
  }

}
