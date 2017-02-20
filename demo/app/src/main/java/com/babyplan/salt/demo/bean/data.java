package com.babyplan.salt.demo.bean;

/**
 * Created by Salt on 2017/2/20.
 */

public class data
{
    private String name;
    private int imageId;
    public data(String name,int imageId){
        this.name=name;
        this.imageId=imageId;
    }
    public String getName(){
        return name;
    }
    public int getImageId(){
        return imageId;
    }
}
