package com.example.dal.mapper;

import com.example.dal.entity.LoadRecordDO;

import org.apache.ibatis.annotations.*;

/**
 * 指令热键记录表
 *
 * @since 2023/3/20 22:15
 * @author by liangzj
 */
@Mapper
public interface LoadRecordMapper {

    @Insert({
        "<script>",
        " INSERT OR REPLACE INTO load_record(id, pathname) ",
        " values (1, #{pathname})",
        "</script>"
    })
    Integer insertOrUpdate(LoadRecordDO recordDO);

    @Select({"<script> SELECT * FROM load_record WHERE id = 1</script>"})
    LoadRecordDO selectOne();
}
