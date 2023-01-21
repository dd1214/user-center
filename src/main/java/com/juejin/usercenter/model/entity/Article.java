package com.juejin.usercenter.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName article
 */
@TableName(value ="article")
@Data
public class Article implements Serializable {
    /**
     * 文章id
     */
    @TableId(value = "articleID")
    private String articleid;

    /**
     * 创建人
     */
    @TableField(value = "createUser")
    private String createuser;

    /**
     * 快照
     */
    @TableField(value = "snapshoot")
    private String snapshoot;

    /**
     * 文章题目
     */
    @TableField(value = "title")
    private String title;

    /**
     * 预览
     */
    @TableField(value = "preview")
    private String preview;

    /**
     * 文章内容
     */
    @TableField(value = "content")
    private String content;

    /**
     * 标签
     */
    @TableField(value = "label")
    private String label;

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
     * 评论数
     */
    @TableField(value = "commentCount")
    private Integer commentcount;

    /**
     * 分类
     */
    @TableField(value = "category")
    private String category;

    /**
     * 文章状态 0-未发布 1-正常
     */
    @TableField(value = "articleStatus")
    private Integer articlestatus;

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
     * 逻辑删除 0-正常 1-异常
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
        Article other = (Article) that;
        return (this.getArticleid() == null ? other.getArticleid() == null : this.getArticleid().equals(other.getArticleid()))
            && (this.getCreateuser() == null ? other.getCreateuser() == null : this.getCreateuser().equals(other.getCreateuser()))
            && (this.getSnapshoot() == null ? other.getSnapshoot() == null : this.getSnapshoot().equals(other.getSnapshoot()))
            && (this.getTitle() == null ? other.getTitle() == null : this.getTitle().equals(other.getTitle()))
            && (this.getPreview() == null ? other.getPreview() == null : this.getPreview().equals(other.getPreview()))
            && (this.getContent() == null ? other.getContent() == null : this.getContent().equals(other.getContent()))
            && (this.getLabel() == null ? other.getLabel() == null : this.getLabel().equals(other.getLabel()))
            && (this.getLikesnumber() == null ? other.getLikesnumber() == null : this.getLikesnumber().equals(other.getLikesnumber()))
            && (this.getReadingquantity() == null ? other.getReadingquantity() == null : this.getReadingquantity().equals(other.getReadingquantity()))
            && (this.getCommentcount() == null ? other.getCommentcount() == null : this.getCommentcount().equals(other.getCommentcount()))
            && (this.getCategory() == null ? other.getCategory() == null : this.getCategory().equals(other.getCategory()))
            && (this.getArticlestatus() == null ? other.getArticlestatus() == null : this.getArticlestatus().equals(other.getArticlestatus()))
            && (this.getCurrenttime() == null ? other.getCurrenttime() == null : this.getCurrenttime().equals(other.getCurrenttime()))
            && (this.getUpdatetime() == null ? other.getUpdatetime() == null : this.getUpdatetime().equals(other.getUpdatetime()))
            && (this.getIsdelete() == null ? other.getIsdelete() == null : this.getIsdelete().equals(other.getIsdelete()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getArticleid() == null) ? 0 : getArticleid().hashCode());
        result = prime * result + ((getCreateuser() == null) ? 0 : getCreateuser().hashCode());
        result = prime * result + ((getSnapshoot() == null) ? 0 : getSnapshoot().hashCode());
        result = prime * result + ((getTitle() == null) ? 0 : getTitle().hashCode());
        result = prime * result + ((getPreview() == null) ? 0 : getPreview().hashCode());
        result = prime * result + ((getContent() == null) ? 0 : getContent().hashCode());
        result = prime * result + ((getLabel() == null) ? 0 : getLabel().hashCode());
        result = prime * result + ((getLikesnumber() == null) ? 0 : getLikesnumber().hashCode());
        result = prime * result + ((getReadingquantity() == null) ? 0 : getReadingquantity().hashCode());
        result = prime * result + ((getCommentcount() == null) ? 0 : getCommentcount().hashCode());
        result = prime * result + ((getCategory() == null) ? 0 : getCategory().hashCode());
        result = prime * result + ((getArticlestatus() == null) ? 0 : getArticlestatus().hashCode());
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
        sb.append(", articleid=").append(articleid);
        sb.append(", createuser=").append(createuser);
        sb.append(", snapshoot=").append(snapshoot);
        sb.append(", title=").append(title);
        sb.append(", preview=").append(preview);
        sb.append(", content=").append(content);
        sb.append(", label=").append(label);
        sb.append(", likesnumber=").append(likesnumber);
        sb.append(", readingquantity=").append(readingquantity);
        sb.append(", commentcount=").append(commentcount);
        sb.append(", category=").append(category);
        sb.append(", articlestatus=").append(articlestatus);
        sb.append(", currenttime=").append(currenttime);
        sb.append(", updatetime=").append(updatetime);
        sb.append(", isdelete=").append(isdelete);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}