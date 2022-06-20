package com.mttbrt.digres.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_item")
public class Item {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Integer id;

  @Column(name = "name", nullable = false, length = 100)
  private String name;

  @Column(name = "description", length = 500)
  private String description;

  @Column(name = "price", nullable = false)
  private Double price;

  @Column(name = "image", length = 200)
  private String image;

  @Column(name = "visible")
  private Boolean visible;

  @ManyToOne(fetch = FetchType.EAGER, optional = false)
  @JoinColumn(name = "category_id", nullable = false)
  private Category category;

  @JsonIgnore
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "item")
  private Set<ItemWithinOrder> itemsWithinOrder;

  @JsonIgnore
  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "tbl_item_with_icon",
      joinColumns = @JoinColumn(name = "item_id"),
      inverseJoinColumns = @JoinColumn(name = "icon_id"))
  private Set<Icon> icons;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public Boolean getVisible() {
    return visible;
  }

  public void setVisible(Boolean visible) {
    this.visible = visible;
  }

  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  public Set<ItemWithinOrder> getItemsWithinOrder() {
    return itemsWithinOrder;
  }

  public void setItemsWithinOrder(Set<ItemWithinOrder> itemsWithinOrder) {
    this.itemsWithinOrder = itemsWithinOrder;
  }

  public Set<Icon> getIcons() {
    return icons;
  }

  public void setIcons(Set<Icon> icons) {
    this.icons = icons;
  }
}