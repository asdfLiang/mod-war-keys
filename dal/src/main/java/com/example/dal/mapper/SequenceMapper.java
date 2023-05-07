package com.example.dal.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

/**
 * @since 2023/5/7 11:46
 * @author by liangzj
 */
@Mapper
public interface SequenceMapper {
    String CMD_HOT_KEY = "cmd_hot_key";

    @Update({"<script> UPDATE sqlite_sequence SET seq = 0 WHERE name =#{name} </script>"})
    void resetSequence(String name);
}
