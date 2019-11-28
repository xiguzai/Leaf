package com.sankuai.inf.leaf.admin.controller;

import com.sankuai.inf.leaf.admin.model.Rest;
import com.sankuai.inf.leaf.admin.model.SegmentBufferDTO;
import com.sankuai.inf.leaf.segment.SegmentIDGenImpl;
import com.sankuai.inf.leaf.segment.model.SegmentBuffer;
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
    public Rest<List<SegmentBufferDTO>> cache() {
        SegmentIDGenImpl segmentIDGen = segmentService.getIdGen();
        if (segmentIDGen == null) {
            throw new IllegalArgumentException("You should config leaf.segment.enable=true first");
        }
        Map<String, SegmentBuffer> cache = segmentIDGen.getCache();
        Collection<SegmentBuffer> values = cache.values();
        List<SegmentBufferDTO> sbList = new ArrayList<>(values.size());
        for (SegmentBuffer buffer : values) {
            SegmentBufferDTO sb = new SegmentBufferDTO();

            sb.setInitOk(buffer.isInitOk());
            sb.setKey(buffer.getKey());
            sb.setPos(buffer.getCurrentPos());
            sb.setNextReady(buffer.isNextReady());
            sb.setMax0(buffer.getSegments()[0].getMax());
            sb.setValue0(buffer.getSegments()[0].getValue().get());
            sb.setStep0(buffer.getSegments()[0].getStep());

            sb.setMax1(buffer.getSegments()[1].getMax());
            sb.setValue1(buffer.getSegments()[1].getValue().get());
            sb.setStep1(buffer.getSegments()[1].getStep());

            sbList.add(sb);
        }
        return Rest.success(sbList);
    }
}
