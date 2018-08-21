
package com.chenlei.json.bean;
public class Root
{
    private Data data;

    private Error error;

    public void setData(Data data){
        this.data = data;
    }
    public Data getData(){
        return this.data;
    }
    public void setError(Error error){
        this.error = error;
    }
    public Error getError(){
        return this.error;
    }
}
