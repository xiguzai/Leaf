package com.sankuai.inf.leaf.admin.service;

import com.sankuai.inf.leaf.admin.dao.LeafAllocDAO;
import com.sankuai.inf.leaf.admin.model.LeafAllocDO;
import com.sankuai.inf.leaf.segment.SegmentIDGenImpl;
import com.sankuai.inf.leaf.segment.model.Segment;
import com.sankuai.inf.leaf.segment.model.SegmentBuffer;
import com.sankuai.inf.leaf.server.service.SegmentService;
import org.perf4j.StopWatch;
import org.perf4j.slf4j.Slf4JStopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class LeafAllocService {

    private Logger logger = LoggerFactory.getLogger(LeafAllocService.class);

    @Autowired
    private LeafAllocDAO leafAllocDAO;

    @Autowired
    private SegmentService segmentService;

    private SegmentIDGenImpl idGen;
    private Method updateCacheFromDb;

    public List<LeafAllocDO> listAll() {
        return leafAllocDAO.listAll();
    }

    public String create(LeafAllocDO leafAllocDO) {
        String bizTag = leafAllocDO.getBizTag();
        int j = leafAllocDAO.countByBizTag(bizTag);
        if (j > 0) {
            return bizTag + " already exists";
        }
        leafAllocDAO.insert(leafAllocDO);
        updateCacheFromDb();
        return null;
    }

    public String update(LeafAllocDO leafAllocDO) {
        String bizTag = leafAllocDO.getBizTag();
        int j = leafAllocDAO.countByBizTag(bizTag);
        if (j == 0) {
            return bizTag + " not exists";
        }
        leafAllocDAO.updateByBizTag(leafAllocDO);
        return null;
    }

    public void delete(String bizTag) {
        leafAllocDAO.deleteByBizTag(bizTag);
        updateCacheFromDb();
    }

    private void updateCacheFromDb() {
        if (idGen == null) {
            idGen = segmentService.getIdGen();
        }
        if (updateCacheFromDb == null) {
            try {
                updateCacheFromDb = idGen.getClass().getDeclaredMethod("updateCacheFromDb");
                updateCacheFromDb.setAccessible(true);
            } catch (NoSuchMethodException e) {
                logger.error(e.getMessage(), e);
            }
        }
        try {
            updateCacheFromDb.invoke(idGen);
        } catch (IllegalAccessException | InvocationTargetException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
