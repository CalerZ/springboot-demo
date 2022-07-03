package com.caleb.demo.mapper.mapper2;


import com.caleb.demo.entity.LogInfo;

import java.util.List;

public interface LogInfoMapper2 {
    int insert(LogInfo record);

    int insertBatchByOn(List<LogInfo> informSms);
}
