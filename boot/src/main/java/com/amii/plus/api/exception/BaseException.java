package com.amii.plus.api.exception;

public class BaseException extends Exception
{
    private static final long serialVersionUID = -8995789052017539691L;

    private Integer code;

    // 重写父类的构造函数
    public BaseException ()
    {
        super();
    }

    public BaseException (String message)
    {
        super(message);
    }

    public BaseException (Throwable cause)
    {
        super(cause);
    }

    public BaseException (String message, Throwable cause)
    {
        super(message, cause);
    }

    public BaseException (Integer code)
    {
        super();
        this.code = code;
    }

    public BaseException (String message, Integer code)
    {
        super(message);
        this.code = code;
    }

    public BaseException (String message, Integer code, Throwable cause)
    {
        super(message, cause);
        this.code = code;
    }

    public Integer getCode ()
    {
        return this.code;
    }
}
