package com.chenhu.learning.bo;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

/**
 * @author 陈虎
 */
@Data
public class UserBO {
    private String name;
    private String email;
    @JSONField(name = "position_name")
    private String positionName;
}
