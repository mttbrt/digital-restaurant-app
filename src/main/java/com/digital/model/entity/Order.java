package com.digital.model.entity;

import java.time.OffsetDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_order")
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Integer id;

  @Column(name = "creation_ts", nullable = false)
  private OffsetDateTime creationTs;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "taken_by")
  private User takenBy;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "session_id", nullable = false)
  private Session session;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public OffsetDateTime getCreationTs() {
    return creationTs;
  }

  public void setCreationTs(OffsetDateTime creationTs) {
    this.creationTs = creationTs;
  }

  public User getTakenBy() {
    return takenBy;
  }

  public void setTakenBy(User takenBy) {
    this.takenBy = takenBy;
  }

  public Session getSession() {
    return session;
  }

  public void setSession(Session session) {
    this.session = session;
  }

}