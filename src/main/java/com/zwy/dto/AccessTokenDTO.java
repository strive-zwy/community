package com.zwy.dto;

import lombok.Data;

/**
 * @Author ：zwy
 * @Date ：2021/1/19 13:06
 * @Version ：1.0
 * @Description ：TODO
 **/
@Data
public class AccessTokenDTO {

    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;

}
