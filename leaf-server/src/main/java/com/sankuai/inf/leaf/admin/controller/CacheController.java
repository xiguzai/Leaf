package com.sankuai.inf.leaf.admin.controller;

import com.sankuai.inf.leaf.admin.model.Rest;
import com.sankuai.inf.leaf.segment.SegmentIDGenImpl;
import com.sankuai.inf.leaf.segment.model.SegmentBuffer;
import com.sankuai.inf.leaf.server.model.SegmentBufferView;
import com.sankuai.inf.leaf.server.service.SegmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class CacheController {

    @Autowired
    private SegmentService segmentService;

    @RequestMapping("/cache")
    public Rest<List<SegmentBufferView>> cache() {
        SegmentIDGenImpl segmentIDGen = segmentService.getIdGen();
        if (segmentIDGen == null) {
            throw new IllegalArgumentException("You should config leaf.segment.enable=true first");
        }
        Map<String, SegmentBuffer> cache = segmentIDGen.getCache();
        Collection<SegmentBuffer> values = cache.values();
        List<SegmentBufferView> svList = new ArrayList<>(values.size());
        for (SegmentBuffer buffer : values) {
            SegmentBufferView sv = new SegmentBufferView();
            sv.setInitOk(buffer.isInitOk());
            sv.setKey(buffer.getKey());
            sv.setPos(buffer.getCurrentPos());
            sv.setNextReady(buffer.isNextReady());
            sv.setMax0(buffer.getSegments()[0].getMax());
            sv.setValue0(buffer.getSegments()[0].getValue().get());
            sv.setStep0(buffer.getSegments()[0].getStep());

            sv.setMax1(buffer.getSegments()[1].getMax());
            sv.setValue1(buffer.getSegments()[1].getValue().get());
            sv.setStep1(buffer.getSegments()[1].getStep());

            svList.add(sv);
        }
        return Rest.success(svList);
    }
}
