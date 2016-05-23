package com.cc.xsl.coolweather.model;

/**
 * Created by xushuailong on 2016/5/23.
 */
public class County {
    private int id;
    private String countyName;
    private String countyCode;
    private int cityId;

    public int getId() {
        return id;
    }

    public int getCityId() {
        return cityId;
    }

    public String getCountyCode() {
        return countyCode;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public void setCountyCode(String countyCode) {
        this.countyCode = countyCode;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }
    @Override
    public String toString() {
        return "County:[id:" + id + ",countyName:"+countyName + ",countyCode:"+countyCode+",cityId:"+cityId+"]";
    }
}
