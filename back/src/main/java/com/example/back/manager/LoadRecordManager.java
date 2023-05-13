package com.example.back.manager;

import com.example.dal.entity.LoadRecordDO;
import com.example.dal.mapper.LoadRecordMapper;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @since 2023/5/7 9:12
 * @author by liangzj
 */
@Component
public class LoadRecordManager {
    @Autowired private LoadRecordMapper loadRecordMapper;

    public Integer refresh(String pathname) {
        if (StringUtils.isBlank(pathname)) {
            return 0;
        }

        LoadRecordDO recordDO = new LoadRecordDO();
        recordDO.setPathname(pathname);

        return loadRecordMapper.insertOrUpdate(recordDO);
    }

    public String latestPathname() {
        return Optional.ofNullable(loadRecordMapper.selectOne())
                .map(LoadRecordDO::getPathname)
                .orElse(null);
    }
}
