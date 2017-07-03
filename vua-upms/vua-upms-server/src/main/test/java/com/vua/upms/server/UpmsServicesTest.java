package com.vua.upms.server;


import com.vua.upms.dao.model.UpmsSystemExample;
import com.vua.upms.rpc.api.UpmsSystemService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by admin on 2017/7/3.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "classpath:applicationContext-dubbo-consumer.xml",
        "classpath:applicationContext.xml"
})
public class UpmsServicesTest {

    @Autowired
    UpmsSystemService upmsSystemService;

    @Test
    public void index() {
        //int cout = upmsSystemService.countByExample(new UpmsSystemExample());

        UpmsSystemExample upmsSystemExample = new UpmsSystemExample();
        upmsSystemExample.createCriteria()
                .andNameEqualTo("zheng-upms-server");
        int count = upmsSystemService.countByExample(upmsSystemExample);
        System.out.println(count);


    }

}
