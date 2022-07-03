package com.caleb.demo.service.impl;


import com.caleb.demo.entity.LogInfo;
import com.caleb.demo.mapper.mapper1.LogInfoMapper1;
import com.caleb.demo.service.ILogInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
public class LogInfoService1Impl implements ILogInfoService {

    @Autowired
    private LogInfoMapper1 logInfoMapper1;

    public int insertBatchByOn(List<LogInfo> logInfos) {
        return logInfoMapper1.insertBatchByOn(logInfos);
    }

    public int insert(LogInfo logInfo) {
        return logInfoMapper1.insert(logInfo);
    }

}
