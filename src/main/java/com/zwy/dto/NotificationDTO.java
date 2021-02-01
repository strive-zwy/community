package com.zwy.dto;

import com.zwy.model.User;
import lombok.Data;

/**
 * @Author ：zwy
 * @Date ：2021/2/1 14:08
 * @Version ：1.0
 * @Description ：TODO
 **/
@Data
public class NotificationDTO {

    private Long id;
    private Long gmtCreate;
    private Integer status;
    private String notifierName;
    private String outerTile;
    private Long outerId;
    private String type;

}
