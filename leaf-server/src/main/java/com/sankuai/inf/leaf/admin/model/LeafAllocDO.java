package com.sankuai.inf.leaf.admin.model;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class LeafAllocDO implements Serializable {
    private static final long serialVersionUID = 3728061521633489854L;

    @NotBlank(message = "BizTag cannot be empty", groups = {CreateGroup.class, UpdateGroup.class})
    private String bizTag;

    private Long maxId;

    @Min(message = "Step size cannot be less than 1", value = 1L, groups = CreateGroup.class)
    private Integer step;

    private String description;

    private Date updateTime;

    public String getBizTag() {
        return bizTag;
    }

    public void setBizTag(String bizTag) {
        this.bizTag = bizTag;
    }

    public Long getMaxId() {
        return maxId;
    }

    public void setMaxId(Long maxId) {
        this.maxId = maxId;
    }

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LeafAllocDO that = (LeafAllocDO) o;
        return Objects.equals(bizTag, that.bizTag) &&
                Objects.equals(maxId, that.maxId) &&
                Objects.equals(step, that.step) &&
                Objects.equals(description, that.description) &&
                Objects.equals(updateTime, that.updateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bizTag, maxId, step, description, updateTime);
    }
}
