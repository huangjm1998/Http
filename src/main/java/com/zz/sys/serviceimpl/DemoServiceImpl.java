package com.zz.sys.serviceimpl;

import com.zz.sys.dao.SysDemoDao;
import com.zz.sys.pojo.BaoWen;
import com.zz.sys.service.DemoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author hjm
 */
@Service
public class DemoServiceImpl implements DemoService {
    @Resource
    private SysDemoDao demoDao;

    @Override
    public void addDemo(BaoWen baoWen) {
        demoDao.saveDemo(baoWen);
    }
}
