package com.digital.model.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_item_within_order")
public class ItemWithinOrder {

  @EmbeddedId
  private ItemWithinOrderPK id;

  @MapsId("orderId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "order_id", nullable = false)
  private Order order;

  @MapsId("itemId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "item_id", nullable = false)
  private Item item;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "state_id", nullable = false)
  private State state;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "fulfilled_by")
  private User fulfilledBy;

  public ItemWithinOrderPK getId() {
    return id;
  }

  public void setId(ItemWithinOrderPK id) {
    this.id = id;
  }

  public Order getOrder() {
    return order;
  }

  public void setOrder(Order order) {
    this.order = order;
  }

  public Item getItem() {
    return item;
  }

  public void setItem(Item item) {
    this.item = item;
  }

  public State getState() {
    return state;
  }

  public void setState(State state) {
    this.state = state;
  }

  public User getFulfilledBy() {
    return fulfilledBy;
  }

  public void setFulfilledBy(User fulfilledBy) {
    this.fulfilledBy = fulfilledBy;
  }

}