package com.zz.sys.controller;

import com.zz.common.vo.JsonResult;
import com.zz.sys.pojo.BaoWen;
import com.zz.sys.service.DemoService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author hjm
 */
@RestController
public class InitRest {
    @Resource
    private DemoService demoService;

    @RequestMapping(value = "/saveDemoTest")
    public JsonResult doSaveDemo(@RequestBody Map b) {
        String str = (String) b.get("str");
        String type = (String) b.get("type");
        String date = (String) b.get("date");
        System.out.println(str);
        System.out.println(type);
        BaoWen wen = new BaoWen();
        wen.setStr(str);
        wen.setType(type);
        wen.setDate(date);
        demoService.addDemo(wen);
        return new JsonResult();
    }
}
