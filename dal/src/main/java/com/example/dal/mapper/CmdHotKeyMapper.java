package com.example.dal.mapper;

import com.example.dal.entity.CmdHotKeyDO;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 指令热键记录表
 *
 * @since 2023/3/20 22:15
 * @author by liangzj
 */
@Mapper
public interface CmdHotKeyMapper {
    Integer insertBatch(List<CmdHotKeyDO> list);
}
