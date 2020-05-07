package com.zz.sys.dao;

import com.zz.sys.pojo.BaoWen;
import org.springframework.stereotype.Component;

/**
 * @author hjm
 */
@Component
public interface SysDemoDao {
    /**
     * 保存报文
     */
    void saveDemo(BaoWen baoWen);

}

