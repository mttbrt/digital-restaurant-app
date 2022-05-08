package com.digital.model.entity.pk;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import org.hibernate.Hibernate;

@Embeddable
public class UserAuthorityPK implements Serializable {

  private static final long serialVersionUID = 3037403282397395186L;
  @Column(name = "user_id", nullable = false)
  private Integer userId;

  @Column(name = "authority_id", nullable = false)
  private Integer authorityId;

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public Integer getAuthorityId() {
    return authorityId;
  }

  public void setAuthorityId(Integer authorityId) {
    this.authorityId = authorityId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    UserAuthorityPK entity = (UserAuthorityPK) o;
    return Objects.equals(this.authorityId, entity.authorityId) &&
        Objects.equals(this.userId, entity.userId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(authorityId, userId);
  }

}