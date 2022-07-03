package com.caleb.demo.service;

import com.caleb.demo.entity.LogInfo;

import java.util.List;

/**
 * @author Caleb_赵康乐
 * @create 2022-06-25 10:41
 * @description :
 */
public interface ILogInfoService {

    public int insertBatchByOn(List<LogInfo> logInfos);

    public int insert(LogInfo logInfo);
}
