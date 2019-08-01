package com.simple.base.componet.exeption;

public class MyException extends  Exception{
    private static final long serialVersionUID = -436749678657916414L;
    private  String message;
    public MyException(String message){
        super(message);
        this.message =message;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
