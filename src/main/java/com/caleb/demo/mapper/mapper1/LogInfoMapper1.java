package com.caleb.demo.mapper.mapper1;


import com.caleb.demo.entity.LogInfo;

import java.util.List;

public interface LogInfoMapper1 {
    int insertBatchByOn(List<LogInfo> logInfos);

    int insert(LogInfo logInfo);
}
