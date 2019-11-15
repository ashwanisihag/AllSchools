package com.track24x7.allSchools.webservice;


import com.track24x7.allSchools.pojo.ResponsePOJO;

/**
 * Programmed by Sihag.
 */

public interface ResponseCallBack<T> {
    public void onGetMsg(ResponsePOJO<T> responsePOJO);
}
