package com.example.back.model;

import lombok.Data;

import java.util.List;

/**
 * 热键分组
 *
 * @since 2023/4/15 1:53
 * @author by liangzj
 */
@Data
public class HotKeyGroup {

    private String groupName;

    private List<RefHotKey> refHotKeys;
}
