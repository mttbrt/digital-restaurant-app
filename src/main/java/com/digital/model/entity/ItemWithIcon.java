package com.digital.model.entity;

import com.digital.model.entity.pk.ItemWithIconPK;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_item_with_icon")
public class ItemWithIcon {

  @EmbeddedId
  private ItemWithIconPK id;

  @MapsId("itemId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "item_id", nullable = false)
  private Item item;

  @MapsId("iconId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "icon_id", nullable = false)
  private Icon icon;

  public ItemWithIconPK getId() {
    return id;
  }

  public void setId(ItemWithIconPK id) {
    this.id = id;
  }

  public Item getItem() {
    return item;
  }

  public void setItem(Item item) {
    this.item = item;
  }

  public Icon getIcon() {
    return icon;
  }

  public void setIcon(Icon icon) {
    this.icon = icon;
  }

}