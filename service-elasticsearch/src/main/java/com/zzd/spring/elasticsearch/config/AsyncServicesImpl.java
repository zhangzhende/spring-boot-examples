package com.zzd.spring.elasticsearch.config;

import com.njxnet.platform.interfaceplatform.dao.CaseDao;
import com.njxnet.platform.interfaceplatform.entity.Case;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description 说明类的用途
 * @ClassName AsyncServiceImpl
 * @Author zzd
 * @Date 2019/12/4 16:18
 * @Version 1.0
 **/
@Service
public class AsyncServicesImpl implements AsyncService {
    private static final Logger LOG = LoggerFactory.getLogger(AsyncServicesImpl.class);
    @Resource
    private CaseDao caseDao;
    @Async("taskExecutor")
    @Override
    public void executeAsync() {
        LOG.info("线程任务"+Thread.currentThread().getName());
        List<Case> cases = caseDao.queryAllByLimit(0, 2);
        try {
//            Thread.currentThread().wait(10000);
            Thread.sleep(10000);
            LOG.info("线程任务"+Thread.currentThread().getName()+"结束！！！");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
