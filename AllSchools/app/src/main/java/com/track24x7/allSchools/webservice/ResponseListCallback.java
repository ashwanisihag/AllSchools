package com.track24x7.allSchools.webservice;


import com.track24x7.allSchools.pojo.ResponseListPOJO;

/**
 * Programmed by Sihag.
 */

public interface ResponseListCallback<T> {
    public void onGetMsg(ResponseListPOJO<T> responseListPOJO);
}
