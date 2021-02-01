package com.zwy.service;

import com.zwy.dto.NotificationDTO;
import com.zwy.dto.PageDTO;
import com.zwy.enums.NotificationTypeEnum;
import com.zwy.mapper.CommentMapper;
import com.zwy.mapper.NotificationMapper;
import com.zwy.mapper.QuestionMapper;
import com.zwy.mapper.UserMapper;
import com.zwy.model.Notification;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author ：zwy
 * @Date ：2021/2/1 15:49
 * @Version ：1.0
 * @Description ：TODO
 **/
@Service
public class NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private CommentMapper commentMapper;


    public PageDTO<NotificationDTO> list(Integer page, Integer size, Long id) {
        Integer offset = size * (page - 1);
        Integer totleCount;
        List<Notification> list = notificationMapper.listByCreator(id,offset,size);
        totleCount = notificationMapper.countByCreator(id);
        List<NotificationDTO> notificationDTOS = new ArrayList<>();
        for (Notification n : list){
            NotificationDTO d = new NotificationDTO();
            BeanUtils.copyProperties(n,d);
            d.setNotifierName(userMapper.findById(n.getNotifier()).getName());
            if (n.getType() == 1){
                d.setOuterTile(questionMapper.findById(n.getOuterId()).getTitle());
            }else{
                d.setOuterId(commentMapper.selectByPrimaryKey(n.getOuterId()).getParentId());
                d.setOuterTile(commentMapper.selectByPrimaryKey(n.getOuterId()).getContent());
            }
            d.setType(NotificationTypeEnum.nameOfType(n.getType()));
            notificationDTOS.add(d);
        }
        PageDTO<NotificationDTO> pageDTO = new PageDTO<>();
        pageDTO.setList(notificationDTOS);
        pageDTO.setPagination(totleCount,page,size);
        return pageDTO;
    }

    public void read(Long nId) {
        notificationMapper.read(nId);
    }

    public Integer countUnRead(Long id) {
        return notificationMapper.countUnRead(id);
    }

    public Notification findById(Long nId) {
        return notificationMapper.selectByPrimaryKey(nId);
    }
}
