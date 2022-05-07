package com.digital.model.key;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import org.hibernate.Hibernate;

@Embeddable
public class ItemWithIconPK implements Serializable {

  private static final long serialVersionUID = -2046436366344868638L;
  @Column(name = "item_id", nullable = false)
  private Integer itemId;

  @Column(name = "icon_id", nullable = false)
  private Integer iconId;

  public Integer getItemId() {
    return itemId;
  }

  public void setItemId(Integer itemId) {
    this.itemId = itemId;
  }

  public Integer getIconId() {
    return iconId;
  }

  public void setIconId(Integer iconId) {
    this.iconId = iconId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    ItemWithIconPK entity = (ItemWithIconPK) o;
    return Objects.equals(this.iconId, entity.iconId) &&
        Objects.equals(this.itemId, entity.itemId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(iconId, itemId);
  }

}