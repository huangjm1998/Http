package com.zz.sys.daolpml;

import com.zz.sys.dao.SysDemoDao;
import com.zz.sys.pojo.BaoWen;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author hjm
 */
@Component
public class SysDemoDaoImpl implements SysDemoDao {
    @Resource
    private MongoTemplate mongoTemplate;

    @Override
    public void saveDemo(BaoWen baoWen) {
        mongoTemplate.save(baoWen);
    }
}
