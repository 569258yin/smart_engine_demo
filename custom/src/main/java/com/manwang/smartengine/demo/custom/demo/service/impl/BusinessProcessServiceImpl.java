package com.manwang.smartengine.demo.custom.demo.service.impl;

import com.manwang.smartengine.demo.custom.demo.service.BusinessProcessService;
import com.manwang.smartengine.demo.custom.demo.process.BusinessProcess;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class BusinessProcessServiceImpl implements BusinessProcessService {

    private static final Map<String, BusinessProcess> DB = new HashMap<>();

    @Override
    public void addBusinessProcess(BusinessProcess businessProcess) {
        DB.put(businessProcess.getId(), businessProcess);
    }

    @Override
    public void updateBusinessProcess(BusinessProcess businessProcess) {
        DB.put(businessProcess.getId(), businessProcess);
    }

    @Override
    public BusinessProcess findById(Long id) {
        return DB.get(id);
    }
}
