package com.digital.model.entity;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_user")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Integer id;

  @Column(name = "username", nullable = false, length = 100)
  private String username;

  @Column(name = "password", nullable = false, length = 100)
  private String password;

  @JsonIgnore
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "takenBy")
  private Set<Order> orders;

  @JsonIgnore
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "fulfilledBy")
  private Set<ItemWithinOrder> itemsWithinOrder;

  @JsonIgnore
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "tbl_user_authority",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "authority_id"))
  private Set<Authority> authorities;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Set<Order> getOrders() {
    return orders;
  }

  public void setOrders(Set<Order> orders) {
    this.orders = orders;
  }

  public Set<Authority> getAuthorities() {
    return authorities;
  }

  public void setAuthorities(Set<Authority> authorities) {
    this.authorities = authorities;
  }

  public Set<ItemWithinOrder> getItemsWithinOrder() {
    return itemsWithinOrder;
  }

  public void setItemsWithinOrder(Set<ItemWithinOrder> itemsWithinOrder) {
    this.itemsWithinOrder = itemsWithinOrder;
  }
}