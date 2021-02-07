package com.zwy.dto;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

/**
 * @Author ：zwy
 * @Date ：2021/2/6 21:14
 * @Version ：1.0
 * @Description ：TODO
 **/
@Data
public class HotTagDTO implements Comparable{
    private String name;
    private Integer priority;

    @Override
    public int compareTo(@NotNull Object o) {
        return this.getPriority() - ((HotTagDTO)o).getPriority();
    }
}
