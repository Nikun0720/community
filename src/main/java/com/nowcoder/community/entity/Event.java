package com.nowcoder.community.entity;

import java.util.HashMap;
import java.util.Map;

public class Event {

    private String topic;       // 事件的主题（类型）

    private int userId;         // 事件的发送者

    private int entityType;     // 事件发生在哪个实体上

    private int entityId;       // 实体id

    private int entityUserId;   // 实体的作者

    private Map<String, Object> data = new HashMap<>();    // 所有其他数据就放在这个map中，方便对数据做扩展

    public String getTopic() {
        return topic;
    }

    public Event setTopic(String topic) {
        this.topic = topic;

        return this;
    }

    public int getUserId() {
        return userId;
    }

    public Event setUserId(int userId) {
        this.userId = userId;

        return this;
    }

    public int getEntityType() {
        return entityType;
    }

    public Event setEntityType(int entityType) {
        this.entityType = entityType;

        return this;
    }

    public int getEntityId() {
        return entityId;
    }

    public Event setEntityId(int entityId) {
        this.entityId = entityId;

        return this;
    }

    public int getEntityUserId() {
        return entityUserId;
    }

    public Event setEntityUserId(int entityUserId) {
        this.entityUserId = entityUserId;

        return this;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public Event setData(String key, Object value) {
        this.data.put(key, value);

        return this;
    }

}
