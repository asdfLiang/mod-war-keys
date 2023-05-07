package com.example.back.service.impl;

import com.example.back.manager.LoadRecordManager;
import com.example.back.service.RecordService;
import com.example.dal.entity.LoadRecordDO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @since 2023/5/7 9:56
 * @author by liangzj
 */
@Service
public class RecordServiceImpl implements RecordService {
    @Autowired private LoadRecordManager loadRecordManager;

    @Override
    public String latestPathname() {
        LoadRecordDO recordDO = loadRecordManager.latest();
        return Optional.ofNullable(recordDO).map(LoadRecordDO::getPathname).orElse(null);
    }
}
