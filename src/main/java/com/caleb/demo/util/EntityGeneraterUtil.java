package com.caleb.demo.util;


import com.caleb.demo.entity.LogInfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author Caleb_赵康乐
 * @create 2022-06-23 22:09
 * @description :
 */
public class EntityGeneraterUtil {

    public static List<LogInfo> generate(int count) {
        List<LogInfo> logInfos = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            logInfos.add(new LogInfo(UUID.randomUUID().toString(), 0, UUID.randomUUID().toString(), new Date()));
        }
        return logInfos;
    }
}
