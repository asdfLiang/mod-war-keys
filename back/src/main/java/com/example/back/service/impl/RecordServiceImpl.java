package com.example.back.service.impl;

import com.example.back.manager.LoadRecordManager;
import com.example.back.service.RecordService;

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
