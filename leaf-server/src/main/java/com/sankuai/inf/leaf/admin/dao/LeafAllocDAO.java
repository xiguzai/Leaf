package com.sankuai.inf.leaf.admin.dao;

import com.sankuai.inf.leaf.admin.model.LeafAllocDO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeafAllocDAO {
    int deleteByBizTag(String bizTag);

    int insert(LeafAllocDO record);

    List<LeafAllocDO> listAll();

    int countByBizTag(String bizTag);

    int updateByBizTag(LeafAllocDO leafAllocDO);
}