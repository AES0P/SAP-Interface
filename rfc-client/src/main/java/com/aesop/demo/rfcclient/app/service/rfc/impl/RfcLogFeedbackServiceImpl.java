package com.aesop.demo.rfcclient.app.service.rfc.impl;

import com.aesop.demo.rfcclient.app.bean.rfc.entity.RfcLogFeedback;
import com.aesop.demo.rfcclient.app.mapper.rfc.RfcLogFeedbackMapper;
import com.aesop.demo.rfcclient.app.service.rfc.RfcLogFeedbackService;
import com.aesop.demo.rfcclient.util.redis.cache.RedisCacheManager;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Aesop
 * @since 2020-01-07
 */
@Slf4j
@Transactional
@Service("rfcLogFeedbackServiceImpl")
@DS("master")
public class RfcLogFeedbackServiceImpl extends ServiceImpl<RfcLogFeedbackMapper, RfcLogFeedback> implements RfcLogFeedbackService {

    @Autowired
    private RfcLogFeedbackMapper feedbackMapper;

    @Caching(put = {@CachePut(value = RedisCacheManager.FEEDBACK_CACHE_POOL, key = "#feedback.id")},
            evict = {@CacheEvict(value = RedisCacheManager.FEEDBACK_ALL_CACHE_POOL, allEntries = true)})
    @Override
    public RfcLogFeedback insert(RfcLogFeedback feedback) {
        feedbackMapper.insert(feedback);
        return feedback;
    }

    @Caching(
            put = {@CachePut(value = RedisCacheManager.FEEDBACK_CACHE_POOL, key = "#feedback.id")},
            evict = {@CacheEvict(value = RedisCacheManager.FEEDBACK_ALL_CACHE_POOL, allEntries = true)}
    )
    @Override
    public RfcLogFeedback update(RfcLogFeedback feedback) {
        feedbackMapper.updateById(feedback);
        return feedback;
    }

    @Caching(evict = {@CacheEvict(value = RedisCacheManager.FEEDBACK_ALL_CACHE_POOL, allEntries = true)})
    @Override
    public void batchInsertOrUpdate(List<RfcLogFeedback> feedbackList) {
        log.info("\r\nBatch insert/update size: " + feedbackList.size());
        saveOrUpdateBatch(feedbackList);
    }

    @Caching(evict = {
            @CacheEvict(value = RedisCacheManager.FEEDBACK_CACHE_POOL, key = "#id"),
            @CacheEvict(value = RedisCacheManager.FEEDBACK_ALL_CACHE_POOL, allEntries = true)
    })
    @Override
    public void removeByLogId(long id) {
        feedbackMapper.deleteById(id);
    }

    @Caching(evict = {
            @CacheEvict(value = RedisCacheManager.FEEDBACK_CACHE_POOL, key = "#msgId"),
            @CacheEvict(value = RedisCacheManager.FEEDBACK_ALL_CACHE_POOL, allEntries = true)
    })
    @Override
    public void removeByMsgId(String msgId) {
        feedbackMapper.deleteByMsgId(msgId);
    }

    @Caching(evict = {
            @CacheEvict(value = RedisCacheManager.FEEDBACK_CACHE_POOL, key = "#rfcName"),
            @CacheEvict(value = RedisCacheManager.FEEDBACK_ALL_CACHE_POOL, allEntries = true)
    })
    @Override
    public void removeByRfcName(String rfcName) {
        feedbackMapper.deleteByRfcName(rfcName);
    }


    @Override
    @DS("slave_1")
    @Cacheable(value = RedisCacheManager.FEEDBACK_CACHE_POOL, key = "#id")
    public RfcLogFeedback selectLogById(long id) {
        return feedbackMapper.selectById(id);
    }

    @Override
    @DS("slave_2")
    @Cacheable(value = RedisCacheManager.FEEDBACK_ALL_CACHE_POOL, key = "'page:' + #page+ '&size:' + #size", unless = "#result == null")
    public Object listAll(int page, int size) {
        Page<RfcLogFeedback> pageObj = new Page<>(page, size);
        return feedbackMapper.selectPage(pageObj, null);
    }

    @Override
    @DS("slave_2")
    @Cacheable(value = RedisCacheManager.FEEDBACK_ALL_CACHE_POOL, key = "'msgId:' + #msgId ", unless = "#result.total == 0")
    public IPage<RfcLogFeedback> selectLogsByMsgId(Page<RfcLogFeedback> page, String msgId) {
        return feedbackMapper.selectLogsByMsgId(page, msgId);
    }

    @Override
    @DS("slave_2")
    @Cacheable(value = RedisCacheManager.FEEDBACK_ALL_CACHE_POOL, key = "'rfcName:' + #rfcName ", unless = "#result.total==0")
    public IPage<RfcLogFeedback> selectLogsByRFCName(Page<RfcLogFeedback> page, String rfcName) {
        return feedbackMapper.selectUsersByRFCName(page, rfcName);
    }
}
