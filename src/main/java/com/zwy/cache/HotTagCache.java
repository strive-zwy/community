package com.zwy.cache;

import com.zwy.dto.HotTagDTO;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @Author ：zwy
 * @Date ：2021/2/6 20:23
 * @Version ：1.0
 * @Description ：TODO
 **/
@Component
@Data
public class HotTagCache {
    private Map<String, Integer> tags = new HashMap<>();
    private List<String> hots = new ArrayList<>();

    public void updateTags(Map<String, Integer> tags) {
        int max = 5;
        PriorityQueue<HotTagDTO> priorityQueue = new PriorityQueue<>(max);
        tags.forEach((name, priority) -> {
            HotTagDTO hotTagDTO = new HotTagDTO();
            hotTagDTO.setName(name);
            hotTagDTO.setPriority(priority);
            if (priorityQueue.size() < max) {
                priorityQueue.add(hotTagDTO);
            } else {
                HotTagDTO minHot = priorityQueue.peek();
                if (hotTagDTO.compareTo(minHot) > 0) {
                    priorityQueue.poll();
                    priorityQueue.add(hotTagDTO);
                }
            }
        });
        List<String> sortTags = new ArrayList<>();
        HotTagDTO poll = priorityQueue.poll();
        while (poll != null){
            sortTags.add(0,poll.getName());
            poll = priorityQueue.poll();
        }
        hots = sortTags;
        System.out.println(hots);
    }
}
