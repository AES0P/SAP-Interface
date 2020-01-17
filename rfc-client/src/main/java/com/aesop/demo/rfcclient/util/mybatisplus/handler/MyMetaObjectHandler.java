package com.aesop.demo.rfcclient.util.mybatisplus.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;


/**
 * mp自动填充
 */
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
//        log.info("Start auto-fill fields on insert....");
        //FOR FeedbackLog ENTITY
        this.setFieldValByName("userId", "88888888", metaObject);
        this.setFieldValByName("userName", "Aesop", metaObject);
        this.setFieldValByName("timestamp", System.currentTimeMillis(), metaObject);

//        log.info("Filling is complete ....");
    }

    @Override
    public void updateFill(MetaObject metaObject) {
//        log.info("Start auto-fill fields on update....");
//        this.setFieldValByName("birthday", new Date(), metaObject);
        //this.setUpdateFieldValByName("operator", "Tom", metaObject);//@since 快照：3.0.7.2-SNAPSHOT， @since 正式版暂未发布3.0.7
//        log.info("Filling is complete ....");
    }
}
