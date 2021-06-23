package com.zwy.schedule;

import com.zwy.cache.HotTagCache;
import com.zwy.mapper.QuestionMapper;
import com.zwy.model.Question;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author ：zwy
 * @Date ：2021/2/6 19:53
 * @Version ：1.0
 * @Description ：TODO
 **/
@Component
@Slf4j
public class HotTagTasks {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-HH:mm:ss");

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private HotTagCache hotTagCache;

    @Scheduled(fixedRate = 1000 * 60 * 60 * 3) //每三小时更新一次
    public void hotTagSchedile() {
        int offset = 0;
        int size = 5;
        log.info("hotTagSchedile start {}",dateFormat.format(new Date()));
        List<Question> list = new ArrayList<>();
        Map<String,Integer> priorities = new HashMap<>();
        while (offset == 0 || list.size() == size ){
            list = questionMapper.list(offset,size);
            for (Question q : list){
                String[] tags = StringUtils.split(q.getTag(),"、");
                for (String tag : tags){
                    Integer priority = priorities.get(tag);
                    if (priority != null){
                        priorities.put(tag,priority + 5 + q.getCommentCount());
                    }else{
                        priorities.put(tag,5 + q.getCommentCount());
                    }
                }
                offset += size;
            }
            hotTagCache.updateTags(priorities);
        }
        log.info("hotTagSchedile stop {}",dateFormat.format(new Date()));
    }
}
