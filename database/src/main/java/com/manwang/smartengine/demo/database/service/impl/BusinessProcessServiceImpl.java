package com.manwang.smartengine.demo.database.service.impl;

import com.manwang.smartengine.demo.database.process.BusinessProcess;
import com.manwang.smartengine.demo.database.service.BusinessProcessService;
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
