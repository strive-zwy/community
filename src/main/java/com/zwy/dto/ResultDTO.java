package com.zwy.dto;

import com.zwy.exception.CustomizeErrorCode;
import com.zwy.exception.CustomizeException;
import lombok.Data;

/**
 * @Author ：zwy
 * @Date ：2021/1/28 16:28
 * @Version ：1.0
 * @Description ：TODO
 **/
@Data
public class ResultDTO {

    private Integer code;
    private String message;

    public static ResultDTO errorOf(Integer code,String message){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(code);
        resultDTO.setMessage(message);
        return resultDTO;
    }

    public static ResultDTO errorOf(CustomizeErrorCode sysError) {
        return errorOf(sysError.getCode(),sysError.getMessage());
    }

    public static ResultDTO errorOf(CustomizeException ex) {
        return errorOf(ex.getCode(),ex.getMessage());
    }

    public static ResultDTO okOf() {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("成功");
        return resultDTO;
    }
}
