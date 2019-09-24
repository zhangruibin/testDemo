package com.zhrb.entity;

import lombok.Data;

/**
 * @ClassName Province
 * @Description TODO
 * @Author Administrator
 * @Date 2019/9/24 10:41
 * @Version
 */
@Data
public class Province {
    private String id;

    private String name;

    public Province(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
