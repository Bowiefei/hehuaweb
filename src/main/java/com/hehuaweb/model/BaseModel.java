package com.hehuaweb.model;

import java.io.Serializable;
import java.util.Date;

import com.hehuaweb.util.CalendarUtils;

/**
 * @author mixueqiang
 * @since Mar 8, 2014
 */
public class BaseModel implements Serializable {
  private static final long serialVersionUID = -6363055536956583470L;

  protected long id;
  protected String name;
  protected int status;
  protected long createTime;
  protected long updateTime;

  public boolean isFinished() {
    return status == 100;
  }

  public boolean isDeleted() {
    return status == -1;
  }

  public boolean isEnabled() {
    return status == 1;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public long getCreateTime() {
    return createTime;
  }

  public void setCreateTime(long createTime) {
    this.createTime = createTime;
  }

  public long getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(long updateTime) {
    this.updateTime = updateTime;
  }

  public Date getCreateDate() {
    return CalendarUtils.getDate(createTime);
  }

  public Date getUpdateDate() {
    return CalendarUtils.getDate(updateTime);
  }

  @Override
  public String toString() {
    return "BaseModel [id=" + id + ", name=" + name + ", status=" + status + ", createTime=" + createTime
        + ", updateTime=" + updateTime + "]";
  }

}
