package com.digital.domain.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import org.hibernate.Hibernate;

@Embeddable
public class ItemWithinOrderPK implements Serializable {

  private static final long serialVersionUID = 2435318058190449566L;

  @Column(name = "order_id", nullable = false)
  private Integer orderId;

  @Column(name = "item_id", nullable = false)
  private Integer itemId;

  public Integer getOrderId() {
    return orderId;
  }

  public void setOrderId(Integer orderId) {
    this.orderId = orderId;
  }

  public Integer getItemId() {
    return itemId;
  }

  public void setItemId(Integer itemId) {
    this.itemId = itemId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    ItemWithinOrderPK entity = (ItemWithinOrderPK) o;
    return Objects.equals(this.itemId, entity.itemId) && Objects.equals(this.orderId, entity.orderId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(itemId, orderId);
  }

}