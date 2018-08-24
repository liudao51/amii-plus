package com.amii.plus.api.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import com.amii.plus.api.entity.MoodThreadEntity;

@Mapper
public interface MoodThreadMapper
{
    @Select("SELECT * FROM mood_thread WHERE id = #{id}")
    @Results({
                 @Result(property = "id", column = "id"),
                 @Result(property = "uid", column = "uid"),
                 @Result(property = "content", column = "content"),
                 @Result(property = "mediaCount", column = "media_count"),
                 @Result(property = "productCount", column = "product_count"),
                 @Result(property = "collectionCount", column = "collection_count"),
                 @Result(property = "likeCount", column = "like_count"),
                 @Result(property = "createdAt", column = "created_at"),
                 @Result(property = "updatedAt", column = "updated_at"),
                 @Result(property = "deletedAt", column = "deleted_at")
             })
    public MoodThreadEntity getOne (@Param("id") Integer id);

    @Select("SELECT * FROM mood_thread WHERE deleted_at IS NULL ORDER BY id DESC")
    @Results({
                 @Result(property = "id", column = "id"),
                 @Result(property = "uid", column = "uid"),
                 @Result(property = "content", column = "content"),
                 @Result(property = "mediaCount", column = "media_count"),
                 @Result(property = "productCount", column = "product_count"),
                 @Result(property = "collectionCount", column = "collection_count"),
                 @Result(property = "likeCount", column = "like_count"),
                 @Result(property = "createdAt", column = "created_at"),
                 @Result(property = "updatedAt", column = "updated_at"),
                 @Result(property = "deletedAt", column = "deleted_at")
             })
    public List<MoodThreadEntity> getList ();
}
