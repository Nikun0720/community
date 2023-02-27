package com.nowcoder.community.service;

import com.nowcoder.community.dao.MessageMapper;
import com.nowcoder.community.entity.Message;
import com.nowcoder.community.util.SensitiveFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private SensitiveFilter sensitiveFilter;

    /**
     * 分页查询当前用户的会话列表，针对每个会话只返回一条最新的私信
     * @param userId
     * @param offset
     * @param limit
     * @return
     */
    public List<Message> findConversations(int userId, int offset, int limit) {
        return messageMapper.selectConversations(userId, offset, limit);
    }

    /**
     * 查询当前用户的会话数量
     * @param userId
     * @return
     */
    public int findConversationCount(int userId) {
        return messageMapper.selectConversationCount(userId);
    }

    /**
     * 分页查询某个会话所包含的私信列表
     * @param conversationId
     * @param offset
     * @param limit
     * @return
     */
    public List<Message> findLetters(String conversationId, int offset, int limit) {
        return messageMapper.selectLetters(conversationId, offset, limit);
    }

    /**
     * 查询某个会话所包含的私信数量
     * @param conversationId
     * @return
     */
    public int findLetterCount(String conversationId) {
        return messageMapper.selectLetterCount(conversationId);
    }

    /**
     * 查询未读私信的数量
     * @param userId
     * @param conversationId（动态sql，传了就拼在sql中，不传就不拼）
     * @return
     */
    public int findLetterUnreadCount(int userId, String conversationId) {
        return messageMapper.selectLetterUnreadCount(userId, conversationId);
    }

    /**
     * 发送消息
     * @param message
     * @return
     */
    public int addMessage(Message message) {
        // 放行文本内容中的html标签
        message.setContent(HtmlUtils.htmlEscape(message.getContent()));
        // 过滤敏感词
        message.setContent(sensitiveFilter.filter(message.getContent()));

        return messageMapper.insertMessage(message);
    }

    /**
     * 将消息设为已读
     * @param ids
     * @return
     */
    public int readMessage(List<Integer> ids) {
        return messageMapper.updateStatus(ids, 1);
    }

    /**
     * 查询最新的通知（系统通知）
     * @param userId
     * @param topic
     * @return
     */
    public Message findLatestNotice(int userId, String topic){
        return messageMapper.selectLatestNotice(userId, topic);
    }

    /**
     * 查询通知（系统通知）的数量
     * @param userId
     * @param topic
     * @return
     */
    public int findNoticeCount(int userId, String topic) {
        return messageMapper.selectNoticeCount(userId, topic);
    }

    /**
     * 查询未读通知（系统通知）的数量
     * @param userId
     * @param topic
     * @return
     */
    public int findNoticeUnreadCount(int userId, String topic) {
        return messageMapper.selectNoticeUnreadCount(userId, topic);
    }

    /**
     * 查询某个主题所包含的通知列表
     * @param userId
     * @param topic
     * @param offset
     * @param limit
     * @return
     */
    public List<Message> findNotices(int userId, String topic, int offset, int limit) {
        return messageMapper.selectNotices(userId, topic, offset, limit);
    }

}
