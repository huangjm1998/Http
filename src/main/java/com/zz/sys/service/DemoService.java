package com.zz.sys.service;

import com.zz.sys.pojo.BaoWen;

/**
 * @author hjm
 */
public interface DemoService {
    /**
     * @param baoWen 报文对象，存入mongodb数据库
     */
    void addDemo(BaoWen baoWen);
}
