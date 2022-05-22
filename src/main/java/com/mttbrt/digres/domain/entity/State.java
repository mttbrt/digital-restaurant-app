package com.mttbrt.digres.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_state")
public class State {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Integer id;

  @Column(name = "title", nullable = false, length = 100)
  private String title;

  @JsonIgnore
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "state")
  private Set<ItemWithinOrder> itemsWithinOrder;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Set<ItemWithinOrder> getItemsWithinOrder() {
    return itemsWithinOrder;
  }

  public void setItemsWithinOrder(Set<ItemWithinOrder> itemsWithinOrder) {
    this.itemsWithinOrder = itemsWithinOrder;
  }
}