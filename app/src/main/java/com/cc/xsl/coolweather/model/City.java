package com.cc.xsl.coolweather.model;

/**
 * Created by xushuailong on 2016/5/23.
 */
public class City {
    private int id;
    private String cityName;
    private String cityCode;
    private int provinceId;

    public int getId() {
        return id;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public String getCityCode() {
        return cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    @Override
    public String toString() {
        return "City:[id:" + id + ",cityName:"+cityName + ",cityCode:"+cityCode+",provinceId:"+provinceId+"]";
    }
}
