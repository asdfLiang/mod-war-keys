package com.example.dal.mapper;

import com.example.dal.entity.CmdHotKeyDO;

import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 指令热键记录表
 *
 * @since 2023/3/20 22:15
 * @author by liangzj
 */
@Mapper
public interface CmdHotKeyMapper {

    @Insert({
        "<script>",
        " INSERT INTO cmd_hot_key(row, cmd, cmd_type, hot_key) ",
        " values ",
        " <foreach collection='list' item='item' separator=','>",
        "   (#{item.row}, #{item.cmd}, #{item.cmdType}, #{item.hotKey})",
        " </foreach>",
        "</script>"
    })
    Integer insertBatch(List<CmdHotKeyDO> list);

    @Delete({"<script> DELETE FROM cmd_hot_key </script>"})
    void deleteAll();

    @Select({"<script> SELECT * FROM cmd_hot_key WHERE cmd = #{cmd} </script>"})
    CmdHotKeyDO selectByCmd(@Param("cmd") String cmd);
}
