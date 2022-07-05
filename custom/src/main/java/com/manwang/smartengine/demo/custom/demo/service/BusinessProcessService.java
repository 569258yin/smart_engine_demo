package com.manwang.smartengine.demo.custom.demo.service;

import com.manwang.smartengine.demo.custom.demo.process.BusinessProcess;

public interface BusinessProcessService {

    void addBusinessProcess(BusinessProcess businessProcess);

    void updateBusinessProcess(BusinessProcess businessProcess);

    BusinessProcess findById(Long id);
}
