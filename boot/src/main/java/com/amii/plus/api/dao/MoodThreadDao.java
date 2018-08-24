package com.amii.plus.api.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.amii.plus.api.constant.ApiConstant;
import com.amii.plus.api.entity.MoodThreadEntity;
import com.amii.plus.api.exception.AppException;
import com.amii.plus.api.mapper.MoodThreadMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Repository
public class MoodThreadDao
{
    @Autowired
    private MoodThreadMapper moodThreadMapper;

    /**
     * TODO: 获取帖子详情
     *
     * @param id
     *
     * @return MoodThreadEntity
     */
    public MoodThreadEntity oneMoodThread (Integer id)
    {
        MoodThreadEntity moodThreadEntity = null;

        try {
            moodThreadEntity = moodThreadMapper.getOne(id);
        } catch (Exception e) {
            throw new AppException(ApiConstant.Message.CODE_11103, 11103, e);
        }

        return moodThreadEntity;
    }

    /**
     * TODO: 获取帖子列表
     *
     * @return List
     */
    public Map<String, Object> listMoodThread (Integer page, Integer pageSize)
    {
        Map<String, Object> map = new HashMap<String, Object>();

        try {
            PageHelper.startPage(page, pageSize, true);
            // 查询结果
            List<MoodThreadEntity> moodThreadEntityList = moodThreadMapper.getList();
            map.put("moodThreadEntityList", moodThreadEntityList);

            // 分页信息
            PageInfo<MoodThreadEntity> pageInfo = new PageInfo<MoodThreadEntity>(moodThreadEntityList);
            map.put("pageInfo", pageInfo);
        } catch (Exception e) {
            throw new AppException(ApiConstant.Message.CODE_11103, 11103, e);
        }

        return map;
    }
}
