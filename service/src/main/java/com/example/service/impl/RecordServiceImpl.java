package com.example.service.impl;

import com.example.service.RecordService;
import com.example.service.manager.LoadRecordManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @since 2023/5/7 9:56
 * @author by liangzj
 */
@Service
public class RecordServiceImpl implements RecordService {
    @Autowired private LoadRecordManager loadRecordManager;

    @Override
    public String latestPathname() {
        return loadRecordManager.latestPathname();
    }
}
