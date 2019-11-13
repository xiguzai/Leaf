package com.sankuai.inf.leaf.admin.controller;

import com.sankuai.inf.leaf.admin.model.CreateGroup;
import com.sankuai.inf.leaf.admin.model.LeafAllocDO;
import com.sankuai.inf.leaf.admin.model.Rest;
import com.sankuai.inf.leaf.admin.model.UpdateGroup;
import com.sankuai.inf.leaf.admin.service.LeafAllocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/allocs")
public class AllocController {

    @Autowired
    private LeafAllocService leafAllocService;

    @GetMapping
    public Rest<List<LeafAllocDO>> allocs() {
        List<LeafAllocDO> leafAllocs = leafAllocService.listAll();
        return Rest.success(leafAllocs);
    }

    @PostMapping("/create")
    public Rest<LeafAllocDO> create(@RequestBody @Validated(CreateGroup.class) LeafAllocDO leafAllocBO, BindingResult br) {
        if (br.hasFieldErrors()) {
            FieldError fieldError = br.getFieldErrors().get(0);
            return Rest.failure(fieldError.getDefaultMessage());
        }
        String s = leafAllocService.create(leafAllocBO);
        if (!StringUtils.isEmpty(s)) {
            return Rest.failure(s);
        }
        return Rest.success(leafAllocBO);
    }

    @PostMapping("/update")
    public Rest<LeafAllocDO> update(@RequestBody @Validated(UpdateGroup.class) LeafAllocDO leafAllocBO, BindingResult br) {
        if (br.hasFieldErrors()) {
            FieldError fieldError = br.getFieldErrors().get(0);
            return Rest.failure(fieldError.getDefaultMessage());
        }
        String s = leafAllocService.update(leafAllocBO);
        if (!StringUtils.isEmpty(s)) {
            return Rest.failure(s);
        }
        return Rest.success(leafAllocBO);
    }

    @PostMapping("/delete")
    public Rest<Object> delete(@RequestParam("bizTag") String bizTag) {
        leafAllocService.delete(bizTag);
        return Rest.success(null);
    }
}
