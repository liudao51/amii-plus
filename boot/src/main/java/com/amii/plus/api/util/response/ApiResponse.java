package com.amii.plus.api.util.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ApiResponse
{
    @JsonProperty("response")
    private ApiResponseBody response;

    @JsonProperty("sign")
    private String sign;

    public ApiResponse ()
    {
        super();
        this.response = null;
        this.sign = "";
    }

    public ApiResponse (ApiResponseBody response, String sign)
    {
        super();
        this.response = response;
        this.sign = sign;
    }

    @JsonIgnore
    public ApiResponseBody getResponse ()
    {
        return this.response;
    }

    public void setResponse (ApiResponseBody response)
    {
        this.response = response;
    }

    @JsonIgnore
    public String getSign ()
    {
        return this.sign;
    }

    public void setSign (String sign)
    {
        this.sign = sign;
    }
}
