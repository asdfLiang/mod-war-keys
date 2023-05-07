package com.example.back.manager;

import com.example.dal.entity.LoadRecordDO;
import com.example.dal.mapper.LoadRecordMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @since 2023/5/7 9:12
 * @author by liangzj
 */
@Component
public class LoadRecordManager {
    @Autowired private LoadRecordMapper loadRecordMapper;

    public Integer refresh(String pathname) {
        LoadRecordDO recordDO = new LoadRecordDO();
        recordDO.setPathname(pathname);

        return loadRecordMapper.insertOrUpdate(recordDO);
    }

    public LoadRecordDO latest() {
        return loadRecordMapper.selectOne();
    }
}
