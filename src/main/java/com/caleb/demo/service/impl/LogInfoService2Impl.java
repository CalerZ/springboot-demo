package com.caleb.demo.service.impl;


import com.caleb.demo.entity.LogInfo;
import com.caleb.demo.mapper.mapper2.LogInfoMapper2;
import com.caleb.demo.service.ILogInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
public class LogInfoService2Impl implements ILogInfoService {
    @Autowired
    private LogInfoMapper2 logInfoMapper2;

    public int insertBatchByOn(List<LogInfo> logInfos) {
        return logInfoMapper2.insertBatchByOn(logInfos);
    }

    public int insert(LogInfo logInfo) {
        return logInfoMapper2.insert(logInfo);
    }

}
