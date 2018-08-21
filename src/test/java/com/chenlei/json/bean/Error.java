
package com.chenlei.json.bean;
public class Error
{
    private int returnCode;

    private String returnMessage;

    private String returnUserMessage;

    public void setReturnCode(int returnCode){
        this.returnCode = returnCode;
    }
    public int getReturnCode(){
        return this.returnCode;
    }
    public void setReturnMessage(String returnMessage){
        this.returnMessage = returnMessage;
    }
    public String getReturnMessage(){
        return this.returnMessage;
    }
    public void setReturnUserMessage(String returnUserMessage){
        this.returnUserMessage = returnUserMessage;
    }
    public String getReturnUserMessage(){
        return this.returnUserMessage;
    }
}

