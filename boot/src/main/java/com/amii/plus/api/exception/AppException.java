package com.amii.plus.api.exception;

public class AppException extends BaseRuntimeException
{
    private static final long serialVersionUID = 1105950897699594795L;

    // 重写父类的构造函数
    public AppException ()
    {
        super();
    }

    public AppException (String message)
    {
        super(message);
    }

    public AppException (Throwable cause)
    {
        super(cause);
    }

    public AppException (String message, Throwable cause)
    {
        super(message, cause);
    }

    public AppException (Integer code)
    {
        super(code);
    }

    public AppException (String message, Integer code)
    {
        super(message, code);
    }

    public AppException (String message, Integer code, Throwable cause)
    {
        super(message, code, cause);
    }
}
