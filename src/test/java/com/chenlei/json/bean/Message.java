
package com.chenlei.json.bean;
import java.util.List;
public class Message
{
    private String requestData;

    private List<ResponseData> responseData;

    public void setRequestData(String requestData){
        this.requestData = requestData;
    }
    public String getRequestData(){
        return this.requestData;
    }
    public void setResponseData(List<ResponseData> responseData){
        this.responseData = responseData;
    }
    public List<ResponseData> getResponseData(){
        return this.responseData;
    }
}

