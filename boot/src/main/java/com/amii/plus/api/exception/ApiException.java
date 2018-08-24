package com.amii.plus.api.exception;

public class ApiException extends BaseRuntimeException
{
    private static final long serialVersionUID = -7958719093860400633L;

    // 原有的重写父类的构造函数
    public ApiException ()
    {
        super();
    }

    public ApiException (String message)
    {
        super(message);
    }

    public ApiException (Throwable cause)
    {
        super(cause);
    }

    public ApiException (String message, Throwable cause)
    {
        super(message, cause);
    }

    public ApiException (Integer code)
    {
        super(code);
    }

    public ApiException (String message, Integer code)
    {
        super(message, code);
    }

    public ApiException (String message, Integer code, Throwable cause)
    {
        super(message, code, cause);
    }
}
