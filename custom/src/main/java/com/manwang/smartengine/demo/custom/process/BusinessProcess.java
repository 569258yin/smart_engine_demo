package com.manwang.smartengine.demo.custom.process;

import com.alibaba.smart.framework.engine.annoation.WorkAround;
import com.alibaba.smart.framework.engine.model.assembly.BaseElement;
import com.alibaba.smart.framework.engine.model.assembly.ExtensionElements;
import com.alibaba.smart.framework.engine.model.assembly.IdBasedElement;
import com.alibaba.smart.framework.engine.model.assembly.ProcessDefinition;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 流程定义
 * 描述一个流程有几个环节,之间的流转关系是什么样子的
 */
@Data
public class BusinessProcess implements ProcessDefinition {

    private String id;
    private String serializedProcessInstance;

    private String version;

    //private String idAndVersion;


    private String name;

    private ExtensionElements extensionElements;

    private List<BaseElement> baseElementList;

    private Map<String, IdBasedElement> idBasedElementMap;

    private Map<String, String> properties;


    @Override
    @WorkAround
    public String getVersion() {
        if (null == version) {
            //compatible for empty version
            version = "1.0.0";
        }
        return this.version;
    }
}
