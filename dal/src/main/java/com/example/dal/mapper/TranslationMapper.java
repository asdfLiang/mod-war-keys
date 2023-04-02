package com.example.dal.mapper;

import com.example.dal.entity.TranslationDO;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @since 2023/3/25 12:22
 * @author by liangzj
 */
@Mapper
public interface TranslationMapper {

    /**
     * 插入一批翻译数据
     *
     * @param list
     * @return
     */
    @Insert({
        "<script>",
        " INSERT INTO transaction(txt, translation) ",
        " VALUES (#{txt}, #{translation})",
        " ON CONFLICT(txt) DO UPDATE translation = #{translation}",
        "</script>"
    })
    Integer insertOrUpdate(List<TranslationDO> list);
}
