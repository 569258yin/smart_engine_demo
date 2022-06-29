package com.manwang.smartengine.demo.custom.service;

import com.manwang.smartengine.demo.process.BusinessProcess;

public interface BusinessProcessService {

    void addBusinessProcess(BusinessProcess businessProcess);
    
    void updateBusinessProcess(BusinessProcess businessProcess);

    BusinessProcess findById(Long id);
}
