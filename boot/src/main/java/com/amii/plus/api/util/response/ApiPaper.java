/**
 * TODO: 前端分页实体
 *
 * @author jewel.liu
 * @since 1.0, Sep 10, 2018
 */
package com.amii.plus.api.util.response;

import com.fasterxml.jackson.annotation.JsonProperty;


public class ApiPaper
{
    // 当前页
    @JsonProperty("page")
    private Integer page;

    // 前一页
    @JsonProperty("pre_page")
    private Integer prePage;

    // 下一页
    @JsonProperty("next_page")
    private Integer nextPage;

    // 每页数量
    @JsonProperty("page_size")
    private Integer pageSize;

    // 总页数
    @JsonProperty("page_count")
    private Integer pageCount;

    // 总记录数
    @JsonProperty("result_count")
    private Long resultCount;

    public ApiPaper ()
    {
    }

    public ApiPaper (Integer page, Integer prePage, Integer nextPage, Integer pageSize, Integer pageCount, Long resultCount)
    {
        this.page = page;
        this.prePage = prePage;
        this.nextPage = nextPage;
        this.pageSize = pageSize;
        this.pageCount = pageCount;
        this.resultCount = resultCount;
    }

    public Integer getPage ()
    {
        return page;
    }

    public void setPage (Integer page)
    {
        this.page = page;
    }

    public Integer getPrePage ()
    {
        return prePage;
    }

    public void setPrePage (Integer prePage)
    {
        this.prePage = prePage;
    }

    public Integer getNextPage ()
    {
        return nextPage;
    }

    public void setNextPage (Integer nextPage)
    {
        this.nextPage = nextPage;
    }

    public Integer getPageSize ()
    {
        return pageSize;
    }

    public void setPageSize (Integer pageSize)
    {
        this.pageSize = pageSize;
    }

    public Integer getPageCount ()
    {
        return pageCount;
    }

    public void setPageCount (Integer pageCount)
    {
        this.pageCount = pageCount;
    }

    public Long getResultCount ()
    {
        return resultCount;
    }

    public void setResultCount (Long resultCount)
    {
        this.resultCount = resultCount;
    }
}
