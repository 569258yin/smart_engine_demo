package com.manwang.smartengine.demo.database.service;


import com.manwang.smartengine.demo.database.process.BusinessProcess;

public interface BusinessProcessService {

    void addBusinessProcess(BusinessProcess businessProcess);
    
    void updateBusinessProcess(BusinessProcess businessProcess);

    BusinessProcess findById(Long id);
}
