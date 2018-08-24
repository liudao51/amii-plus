package com.amii.plus.api.dispatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.amii.plus.api.constant.ApiConstant;
import com.amii.plus.api.entity.MoodThreadEntity;
import com.amii.plus.api.exception.AppException;
import com.amii.plus.api.service.MoodThreadService;
import com.amii.plus.api.util.ArrayToolkit;
import com.amii.plus.api.util.ConfigToolkit;
import com.amii.plus.api.util.StringToolkit;
import com.amii.plus.api.util.response.ApiPaper;
import com.github.pagehelper.PageInfo;

@Component
public class MoodThreadServiceDispatch extends ServiceDispatch
{
    @Autowired
    private MoodThreadService service;

    /**
     * TODO: 根据方法名调用不同的方法
     *
     * @param methodName 方法名
     * @param params     方法参数
     *
     * @return Object
     *
     * @throws AppException
     */
    @Override
    public Object requestMethod (String methodName, Map<String, Object> params) throws AppException
    {
        Object data = null;

        // 调用detailMoodThread方法
        if ("detailMoodThread".equals(methodName)) {
            data = this.detailMoodThread(params);
        }
        // 调用listMoodThread方法
        else if ("listMoodThread".equals(methodName)) {
            data = this.listMoodThread(params);
        }

        // 没有匹配到方法
        else {
            throw new AppException(ApiConstant.Message.CODE_11101, 11101);
        }

        return data;
    }

    /**
     * TODO: detailMoodThread方法
     *
     * @param params
     *
     * @return Object
     */
    private Object detailMoodThread (Map<String, Object> params)
    {
        Map<String, Object> data = new HashMap<String, Object>();

        // id参数
        Integer id = 0;
        try {
            id = Integer.parseInt(params.get("id").toString().trim());
        } catch (Exception e) {
            throw new AppException("id" + ApiConstant.Message.CODE_14999, 14999, e);
        }
        if (id <= 0) {
            throw new AppException("id" + ApiConstant.Message.CODE_14999, 14999);
        }

        // fields参数
        String fields = "";

        // 允许取得的列
        String[] enableFields = new String[]{"id", "uid", "content", "media_count", "product_count", "collection_count", "like_count", "created_at"};
        String[] requestFields = null;

        try {
            fields = params.get("fields").toString();
            requestFields = fields.split(",");
            if (StringToolkit.isEmpty(fields) || !ArrayToolkit.isArrayContainsArray(enableFields, requestFields)) {
                throw new AppException("fields" + ApiConstant.Message.CODE_14999, 14999);
            }
        } catch (Exception e) {
            throw new AppException("fields" + ApiConstant.Message.CODE_14999, 14999, e);
        }

        // 调用服务
        MoodThreadEntity moodThreadEntity = this.service.oneMoodThread(id);

        // 过滤/映射字段
        if (null != moodThreadEntity) {
            Map<String, Object> moodThreadItem = new HashMap<String, Object>();

            if (ArrayToolkit.isArrayContainsValue(requestFields, "id")) {
                moodThreadItem.put("id", moodThreadEntity.getId());
            }
            if (ArrayToolkit.isArrayContainsValue(requestFields, "uid")) {
                moodThreadItem.put("uid", moodThreadEntity.getUid());
            }
            if (ArrayToolkit.isArrayContainsValue(requestFields, "content")) {
                moodThreadItem.put("content", moodThreadEntity.getContent());
            }
            if (ArrayToolkit.isArrayContainsValue(requestFields, "media_count")) {
                moodThreadItem.put("media_count", moodThreadEntity.getMediaCount());
            }
            if (ArrayToolkit.isArrayContainsValue(requestFields, "product_count")) {
                moodThreadItem.put("product_count", moodThreadEntity.getProductCount());
            }
            if (ArrayToolkit.isArrayContainsValue(requestFields, "collection_count")) {
                moodThreadItem.put("collection_count", moodThreadEntity.getCollectionCount());
            }
            if (ArrayToolkit.isArrayContainsValue(requestFields, "like_count")) {
                moodThreadItem.put("like_count", moodThreadEntity.getLikeCount());
            }
            if (ArrayToolkit.isArrayContainsValue(requestFields, "created_at")) {
                moodThreadItem.put("created_at", moodThreadEntity.getCreatedAt());
            }

            data.put("mood_thread", moodThreadItem);
        }

        return data;
    }

    /**
     * TODO: listMoodThread方法
     *
     * @param params
     *
     * @return Object
     */
    private Object listMoodThread (Map<String, Object> params)
    {
        Map<String, Object> data = new HashMap<String, Object>();

        // page参数
        Integer page = 0;
        try {
            // 默认值为1
            page = (null == params.get("page")) ? 1 : Integer.parseInt(params.get("page").toString().trim());
            if (page < 0) {
                throw new AppException("page" + ApiConstant.Message.CODE_14999, 14999);
            }
        } catch (Exception e) {
            throw new AppException("page" + ApiConstant.Message.CODE_14999, 14999, e);
        }

        // page_size参数
        Integer pageSize = 0;
        try {
            // 默认值为10
            pageSize = (null == params.get("page_size")) ? Integer.parseInt(ConfigToolkit.getProperty("app_pageSize"))
                : Integer.parseInt(params.get("page_size").toString().trim());
            if (pageSize <= 0) {
                throw new AppException("page_size" + ApiConstant.Message.CODE_14999, 14999);
            }
        } catch (Exception e) {
            throw new AppException("page_size" + ApiConstant.Message.CODE_14999, 14999, e);
        }

        // fields参数
        String fields = "";

        // 允许取得的列
        String[] enableFields = new String[]{"id", "uid", "content", "media_count", "product_count", "collection_count", "like_count", "created_at"};
        String[] requestFields = null;

        try {
            fields = params.get("fields").toString();
            requestFields = fields.split(",");
            if (StringToolkit.isEmpty(fields) || !ArrayToolkit.isArrayContainsArray(enableFields, requestFields)) {
                throw new AppException("fields" + ApiConstant.Message.CODE_14999, 14999);
            }
        } catch (Exception e) {
            throw new AppException("fields" + ApiConstant.Message.CODE_14999, 14999, e);
        }

        // 调用服务
        Map<String, Object> moodThreadsAndPageInfo = this.service.listMoodThread(page, pageSize);

        // 过滤/映射字段
        if (null != moodThreadsAndPageInfo) {
            List<MoodThreadEntity> moodThreadEntityList = (List<MoodThreadEntity>) moodThreadsAndPageInfo.get("moodThreadEntityList");
            List<Map> moodThreadList = new ArrayList<Map>();
            Map<String, Object> moodThreadItem = null;
            MoodThreadEntity moodThreadEntity = null;
            for (int i = 0; i < moodThreadEntityList.size(); i++) {
                moodThreadEntity = moodThreadEntityList.get(i);
                moodThreadItem = new HashMap<String, Object>();

                if (ArrayToolkit.isArrayContainsValue(requestFields, "id")) {
                    moodThreadItem.put("id", moodThreadEntity.getId());
                }
                if (ArrayToolkit.isArrayContainsValue(requestFields, "uid")) {
                    moodThreadItem.put("uid", moodThreadEntity.getUid());
                }
                if (ArrayToolkit.isArrayContainsValue(requestFields, "content")) {
                    moodThreadItem.put("content", moodThreadEntity.getContent());
                }
                if (ArrayToolkit.isArrayContainsValue(requestFields, "media_count")) {
                    moodThreadItem.put("media_count", moodThreadEntity.getMediaCount());
                }
                if (ArrayToolkit.isArrayContainsValue(requestFields, "product_count")) {
                    moodThreadItem.put("product_count", moodThreadEntity.getProductCount());
                }
                if (ArrayToolkit.isArrayContainsValue(requestFields, "collection_count")) {
                    moodThreadItem.put("collection_count", moodThreadEntity.getCollectionCount());
                }
                if (ArrayToolkit.isArrayContainsValue(requestFields, "like_count")) {
                    moodThreadItem.put("like_count", moodThreadEntity.getLikeCount());
                }
                if (ArrayToolkit.isArrayContainsValue(requestFields, "created_at")) {
                    moodThreadItem.put("created_at", moodThreadEntity.getCreatedAt());
                }

                moodThreadList.add(moodThreadItem);
            }
            data.put("mood_threads", moodThreadList);

            PageInfo<MoodThreadEntity> pagerInfo = (PageInfo<MoodThreadEntity>) moodThreadsAndPageInfo.get("pageInfo");
            Integer apiPagerPage = pagerInfo.getPageNum();
            Integer apiPagerPrePage = (0 == pagerInfo.getPrePage()) ? 1 : pagerInfo.getPrePage();
            Integer apiPagerNextPage = (0 == pagerInfo.getNextPage()) ? pagerInfo.getPages() : pagerInfo.getNextPage();
            Integer apiPagerPageSize = pagerInfo.getPageSize();
            Integer apiPagerPageCount = pagerInfo.getPages();
            Long apiPagerResultCount = pagerInfo.getTotal();
            ApiPaper apiPager = new ApiPaper(apiPagerPage, apiPagerPrePage, apiPagerNextPage, apiPagerPageSize, apiPagerPageCount,
                                             apiPagerResultCount);
            data.put("pager", apiPager);
        }

        return data;
    }
}