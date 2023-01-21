package com.juejin.usercenter.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 用户
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User implements Serializable {
    /**
     * 用户 id
     */
    @TableId(value = "userID")
    private String userid;

    /**
     * 用户昵称
     */
    @TableField(value = "nickName")
    private String nickname;

    /**
     * 用户头像
     */
    @TableField(value = "userAvatar")
    private String useravatar;

    /**
     * 密码
     */
    @TableField(value = "userPassword")
    private String userpassword;

    /**
     * 创作等级
     */
    @TableField(value = "creationLevel")
    private String creationlevel;

    /**
     * 简介
     */
    @TableField(value = "introduction")
    private String introduction;

    /**
     * 点赞数
     */
    @TableField(value = "likesNumber")
    private Integer likesnumber;

    /**
     * 阅读量
     */
    @TableField(value = "readingQuantity")
    private Integer readingquantity;

    /**
     * 用户状态 0-正常 1-冻结
     */
    @TableField(value = "userStatus")
    private Integer userstatus;

    /**
     * 用户权限 0-用户 1-管理员
     */
    @TableField(value = "userRole")
    private Integer userrole;

    /**
     * 创建时间
     */
    @TableField(value = "currentTime")
    private Date currenttime;

    /**
     * 更新时间
     */
    @TableField(value = "updateTime")
    private Date updatetime;

    /**
     * 逻辑删除 0-正常 1-删除
     */
    @TableField(value = "isDelete")
    private Integer isdelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        User other = (User) that;
        return (this.getUserid() == null ? other.getUserid() == null : this.getUserid().equals(other.getUserid()))
            && (this.getNickname() == null ? other.getNickname() == null : this.getNickname().equals(other.getNickname()))
            && (this.getUseravatar() == null ? other.getUseravatar() == null : this.getUseravatar().equals(other.getUseravatar()))
            && (this.getUserpassword() == null ? other.getUserpassword() == null : this.getUserpassword().equals(other.getUserpassword()))
            && (this.getCreationlevel() == null ? other.getCreationlevel() == null : this.getCreationlevel().equals(other.getCreationlevel()))
            && (this.getIntroduction() == null ? other.getIntroduction() == null : this.getIntroduction().equals(other.getIntroduction()))
            && (this.getLikesnumber() == null ? other.getLikesnumber() == null : this.getLikesnumber().equals(other.getLikesnumber()))
            && (this.getReadingquantity() == null ? other.getReadingquantity() == null : this.getReadingquantity().equals(other.getReadingquantity()))
            && (this.getUserstatus() == null ? other.getUserstatus() == null : this.getUserstatus().equals(other.getUserstatus()))
            && (this.getUserrole() == null ? other.getUserrole() == null : this.getUserrole().equals(other.getUserrole()))
            && (this.getCurrenttime() == null ? other.getCurrenttime() == null : this.getCurrenttime().equals(other.getCurrenttime()))
            && (this.getUpdatetime() == null ? other.getUpdatetime() == null : this.getUpdatetime().equals(other.getUpdatetime()))
            && (this.getIsdelete() == null ? other.getIsdelete() == null : this.getIsdelete().equals(other.getIsdelete()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getUserid() == null) ? 0 : getUserid().hashCode());
        result = prime * result + ((getNickname() == null) ? 0 : getNickname().hashCode());
        result = prime * result + ((getUseravatar() == null) ? 0 : getUseravatar().hashCode());
        result = prime * result + ((getUserpassword() == null) ? 0 : getUserpassword().hashCode());
        result = prime * result + ((getCreationlevel() == null) ? 0 : getCreationlevel().hashCode());
        result = prime * result + ((getIntroduction() == null) ? 0 : getIntroduction().hashCode());
        result = prime * result + ((getLikesnumber() == null) ? 0 : getLikesnumber().hashCode());
        result = prime * result + ((getReadingquantity() == null) ? 0 : getReadingquantity().hashCode());
        result = prime * result + ((getUserstatus() == null) ? 0 : getUserstatus().hashCode());
        result = prime * result + ((getUserrole() == null) ? 0 : getUserrole().hashCode());
        result = prime * result + ((getCurrenttime() == null) ? 0 : getCurrenttime().hashCode());
        result = prime * result + ((getUpdatetime() == null) ? 0 : getUpdatetime().hashCode());
        result = prime * result + ((getIsdelete() == null) ? 0 : getIsdelete().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", userid=").append(userid);
        sb.append(", nickname=").append(nickname);
        sb.append(", useravatar=").append(useravatar);
        sb.append(", userpassword=").append(userpassword);
        sb.append(", creationlevel=").append(creationlevel);
        sb.append(", introduction=").append(introduction);
        sb.append(", likesnumber=").append(likesnumber);
        sb.append(", readingquantity=").append(readingquantity);
        sb.append(", userstatus=").append(userstatus);
        sb.append(", userrole=").append(userrole);
        sb.append(", currenttime=").append(currenttime);
        sb.append(", updatetime=").append(updatetime);
        sb.append(", isdelete=").append(isdelete);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}