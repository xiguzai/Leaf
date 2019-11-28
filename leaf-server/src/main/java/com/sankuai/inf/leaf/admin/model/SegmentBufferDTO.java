package com.sankuai.inf.leaf.admin.model;

import com.sankuai.inf.leaf.server.model.SegmentBufferView;

import java.io.Serializable;

public class SegmentBufferDTO extends SegmentBufferView implements Serializable {
    private static final long serialVersionUID = -2493379580657015104L;

    public String getNextReady() {
        return String.valueOf(super.isNextReady());
    }

    public String getInitOk() {
        return String.valueOf(super.isInitOk());
    }

}
