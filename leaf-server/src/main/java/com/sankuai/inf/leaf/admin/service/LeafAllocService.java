package com.sankuai.inf.leaf.admin.service;

import com.sankuai.inf.leaf.admin.dao.LeafAllocDAO;
import com.sankuai.inf.leaf.admin.model.LeafAllocDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeafAllocService {

    @Autowired
    private LeafAllocDAO leafAllocDAO;

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
    }
}
