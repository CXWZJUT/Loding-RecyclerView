package com.babyplan.salt.demo.bean;

/**
 * Created by Salt on 2017/2/18.
 */

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class Movie extends BmobObject {
    private String name;//电影名称
    private BmobFile file;//电影文件
    private String url;

    public Movie(){
    }

    public Movie(String name,BmobFile file){
        this.name =name;
        this.file = file;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BmobFile getFile() {
        return file;
    }

    public void setFile(BmobFile file) {
        this.file = file;
    }

    public String geturl() {
        return url;
    }

    public void seturl(String file) {
        this.url = file;
    }
}