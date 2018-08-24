package com.amii.plus.api.entity;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "mood_thread")
public class MoodThreadEntity
{
    @Id
    @GeneratedValue
    @Column(name = "id")
    @JsonProperty("id")
    private Integer id;

    @Column(name = "uid")
    @JsonProperty("uid")
    private Integer uid;

    @Column(name = "content")
    @JsonProperty("content")
    private String content;

    @Column(name = "media_count")
    @JsonProperty("media_count")
    private Integer mediaCount;

    @Column(name = "product_count")
    @JsonProperty("product_count")
    private Integer productCount;

    @Column(name = "collection_count")
    @JsonProperty("collection_count")
    private Integer collectionCount;

    @Column(name = "like_count")
    @JsonProperty("like_count")
    private Integer likeCount;

    @Column(name = "created_at")
    @JsonProperty("created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    @JsonProperty("updated_at")
    private Timestamp updatedAt;

    @Column(name = "deleted_at")
    @JsonProperty("deleted_at")
    private Timestamp deletedAt;

    public Integer getId ()
    {
        return this.id;
    }

    public void setId (Integer id)
    {
        this.id = id;
    }

    public Integer getUid ()
    {
        return this.uid;
    }

    public void setUid (Integer uid)
    {
        this.uid = uid;
    }

    public String getContent ()
    {
        return this.content;
    }

    public void setContent (String content)
    {
        this.content = content;
    }

    public Integer getMediaCount ()
    {
        return this.mediaCount;
    }

    public void setMediaCount (Integer mediaCount)
    {
        this.mediaCount = mediaCount;
    }

    public Integer getProductCount ()
    {
        return this.productCount;
    }

    public void setProductCount (Integer productCount)
    {
        this.productCount = productCount;
    }

    public Integer getCollectionCount ()
    {
        return this.collectionCount;
    }

    public void setCollectionCount (Integer collectionCount)
    {
        this.collectionCount = collectionCount;
    }

    public Integer getLikeCount ()
    {
        return this.likeCount;
    }

    public void setLikeCount (Integer likeCount)
    {
        this.likeCount = likeCount;
    }

    public Timestamp getCreatedAt ()
    {
        return this.createdAt;
    }

    public void setCreatedAt (Timestamp createdAt)
    {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt ()
    {
        return this.updatedAt;
    }

    public void setUpdatedAt (Timestamp updatedAt)
    {
        this.updatedAt = updatedAt;
    }

    public Timestamp getDeletedAt ()
    {
        return this.deletedAt;
    }

    public void setDeletedAt (Timestamp deletedAt)
    {
        this.deletedAt = deletedAt;
    }
}