package com.amii.plus.api.util.response;

import com.amii.plus.api.constant.ApiConstant;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ApiResponseBody
{
    @JsonProperty("status")
    private String status;

    @JsonProperty("code")
    private Integer code;

    @JsonProperty("message")
    private String message;

    @JsonProperty("sub_code")
    private Integer subCode;

    @JsonProperty("sub_message")
    private String subMessage;

    @JsonProperty("method")
    private String method;

    @JsonProperty("sign_type")
    private String signType;

    @JsonProperty("data")
    private Object data;

    public ApiResponseBody ()
    {
        super();
        this.status = ApiConstant.Status.SUCCESS;
        this.code = 0;
        this.message = ApiConstant.Message.CODE_0;
        this.subCode = 0;
        this.subMessage = ApiConstant.Message.CODE_0;
        this.method = "";
        this.signType = ApiConstant.SignType.NONE;
        this.data = null;
    }

    public ApiResponseBody (String status, Integer code, String message, Integer subCode, String subMessage, String method, String signType,
                            Object data)
    {
        super();
        this.status = status;
        this.code = code;
        this.message = message;
        this.subCode = subCode;
        this.subMessage = subMessage;
        this.method = method;
        this.signType = signType;
        this.data = data;
    }

    @JsonIgnore
    public String getStatus ()
    {
        return this.status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    @JsonIgnore
    public Integer getCode ()
    {
        return this.code;
    }

    public void setCode (Integer code)
    {
        this.code = code;
    }

    @JsonIgnore
    public String getMessage ()
    {
        return this.message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    @JsonIgnore
    public Integer getSubCode ()
    {
        return this.subCode;
    }

    public void setSubCode (Integer subCode)
    {
        this.subCode = subCode;
    }

    @JsonIgnore
    public String getSubMessage ()
    {
        return this.subMessage;
    }

    public void setSubMessage (String subMessage)
    {
        this.subMessage = subMessage;
    }

    @JsonIgnore
    public String getMethod ()
    {
        return this.method;
    }

    public void setMethod (String method)
    {
        this.method = method;
    }

    @JsonIgnore
    public String getSignType ()
    {
        return this.signType;
    }

    public void setSignType (String signType)
    {
        this.signType = signType;
    }

    @JsonIgnore
    public Object getData ()
    {
        return this.data;
    }

    public void setData (Object data)
    {
        this.data = data;
    }
}
