
package com.chenlei.json.bean;

import java.util.List;
public class Data
{
    private String apiCode;

    private String apiJsonSchema;

    private String apiName;

    private int apiProtocol;

    private int apiStatus;

    private String apiUrl;

    private int appID;

    private String appName;

    private String createTime;

    private String createUser;

    private String desc;

    private String errorCode;

    private boolean follow;

    private String jsonStr;

    private List<Message> message;

    private String modifyTime;

    private String modifyUser;

    private int requestMethod;

    public void setApiCode(String apiCode){
        this.apiCode = apiCode;
    }
    public String getApiCode(){
        return this.apiCode;
    }
    public void setApiJsonSchema(String apiJsonSchema){
        this.apiJsonSchema = apiJsonSchema;
    }
    public String getApiJsonSchema(){
        return this.apiJsonSchema;
    }
    public void setApiName(String apiName){
        this.apiName = apiName;
    }
    public String getApiName(){
        return this.apiName;
    }
    public void setApiProtocol(int apiProtocol){
        this.apiProtocol = apiProtocol;
    }
    public int getApiProtocol(){
        return this.apiProtocol;
    }
    public void setApiStatus(int apiStatus){
        this.apiStatus = apiStatus;
    }
    public int getApiStatus(){
        return this.apiStatus;
    }
    public void setApiUrl(String apiUrl){
        this.apiUrl = apiUrl;
    }
    public String getApiUrl(){
        return this.apiUrl;
    }
    public void setAppID(int appID){
        this.appID = appID;
    }
    public int getAppID(){
        return this.appID;
    }
    public void setAppName(String appName){
        this.appName = appName;
    }
    public String getAppName(){
        return this.appName;
    }
    public void setCreateTime(String createTime){
        this.createTime = createTime;
    }
    public String getCreateTime(){
        return this.createTime;
    }
    public void setCreateUser(String createUser){
        this.createUser = createUser;
    }
    public String getCreateUser(){
        return this.createUser;
    }
    public void setDesc(String desc){
        this.desc = desc;
    }
    public String getDesc(){
        return this.desc;
    }
    public void setErrorCode(String errorCode){
        this.errorCode = errorCode;
    }
    public String getErrorCode(){
        return this.errorCode;
    }
    public void setFollow(boolean follow){
        this.follow = follow;
    }
    public boolean getFollow(){
        return this.follow;
    }
    public void setJsonStr(String jsonStr){
        this.jsonStr = jsonStr;
    }
    public String getJsonStr(){
        return this.jsonStr;
    }
    public void setMessage(List<Message> message){
        this.message = message;
    }
    public List<Message> getMessage(){
        return this.message;
    }
    public void setModifyTime(String modifyTime){
        this.modifyTime = modifyTime;
    }
    public String getModifyTime(){
        return this.modifyTime;
    }
    public void setModifyUser(String modifyUser){
        this.modifyUser = modifyUser;
    }
    public String getModifyUser(){
        return this.modifyUser;
    }
    public void setRequestMethod(int requestMethod){
        this.requestMethod = requestMethod;
    }
    public int getRequestMethod(){
        return this.requestMethod;
    }
}

