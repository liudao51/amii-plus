package com.amii.plus.api.service;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.amii.plus.api.dao.MoodThreadDao;
import com.amii.plus.api.entity.MoodThreadEntity;

@Service
public class MoodThreadService
{
    @Autowired
    private MoodThreadDao moodThreadDao;

    /**
     * TODO: 取得帖子详情
     *
     * @param id
     *
     * @return
     */
    public MoodThreadEntity oneMoodThread (Integer id)
    {
        return moodThreadDao.oneMoodThread(id);
    }

    /**
     * TODO: 取得帖子列表
     *
     * @param page
     * @param pageSize
     *
     * @return
     */
    public Map<String, Object> listMoodThread (Integer page, Integer pageSize)
    {
        return moodThreadDao.listMoodThread(page, pageSize);
    }
}
