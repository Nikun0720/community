package com.nowcoder.community.util;

/**
 * 为Redis生成key的工具类
 */
public class RedisKeyUtil {

    private static final String SPLIT = ":";

    private static final String PREFIX_ENTITY_LIKE = "like:entity";

    private static final String PREFIX_USER_LIKE = "like:user";

    private static final String PREFIX_FOLLOWEE = "followee";

    private static final String PREFIX_FOLLOWER = "follower";

    private static final String PREFIX_KAPTCHA = "kaptcha";

    private static final String PREFIX_TICKET = "ticket";

    private static final String PREFIX_USER = "user";

    // 用于统计 UV相关数据 的前缀
    private static final String PREFIX_UV = "uv";

    // 用于统计 DAU相关数据 的前缀
    private static final String PREFIX_DAU = "dau";

    // 同于 统计帖子分数 的前缀
    private static final String PREFIX_POST = "post";

    /**
     * 某个实体的赞，存入redis的形式如下
     * like:entity:entityType:entityId -> set(userId)
     * 上面的意思是，key为like:entity:entityType:entityId，用的数据结构是set，set里面存储的是userId
     * @param entityType
     * @param entityId
     * @return
     */
    public static String getEntityLikeKey(int entityType, int entityId) {
        return PREFIX_ENTITY_LIKE + SPLIT + entityType + SPLIT + entityId;
    }

    /**
     * 某个实体的赞，存入redis的形式如下
     * like:user:userId -> int
     * @param userId
     * @return
     */
    public static String getUserLikeKey(int userId) {
        return PREFIX_USER_LIKE + SPLIT + userId;
    }

    /**
     * 某个用户关注的实体
     * followee:userId:entityType -> zset(entityId, now)
     * @param userId
     * @param entityType
     * @return
     */
    public static String getFolloweeKey(int userId, int entityType){
        return PREFIX_FOLLOWEE + SPLIT + userId + SPLIT + entityType;
    }

    /**
     * 某个实体拥有的粉丝
     * follower:entityType:entityId -> zset(userId, now)
     * @param entityType
     * @param entityId
     * @return
     */
    public static String getFollowerKey(int entityType, int entityId){
        return PREFIX_FOLLOWER + SPLIT + entityType + SPLIT + entityId;
    }

    /**
     * 登录验证码
     * 随机给刚登录用户传一个临时凭证（owner），因为此时用户没登录，无法得知他的userId
     * @param owner
     * @return
     */
    public static String getKaptchaKey(String owner) {
        return PREFIX_KAPTCHA + SPLIT + owner;
    }

    /**
     * 登录的凭证
     * @param ticket
     * @return
     */
    public static String getTicketKey(String ticket) {
        return PREFIX_TICKET + SPLIT + ticket;
    }

    /**
     * 用户
     * @param userId
     * @return
     */
    public static String getUserKey(int userId) {
        return PREFIX_USER + SPLIT + userId;
    }

    /**
     * 统计单日uv（独立访客）
     * @param date
     * @return
     */
    public static String getUVKey(String date) {
        return PREFIX_UV + SPLIT + date;
    }

    /**
     * 统计区间UV（独立访客）
     * 区间就是从哪天到哪天
     * @param startDate
     * @param endDate
     * @return
     */
    public static String getUVKey(String startDate, String endDate) {
        return PREFIX_UV + SPLIT + startDate + SPLIT + endDate;
    }

    /**
     * 统计DAU（单日活跃用户）
     * @param date
     * @return
     */
    public static String getDAUKey(String date) {
        return PREFIX_DAU + SPLIT + date;
    }

    /**
     * 统计区间DAU（单日活跃用户）
     * 区间就是从哪天到哪天
     * @param startDate
     * @param endDate
     * @return
     */
    public static String getDAUKey(String startDate, String endDate) {
        return PREFIX_DAU + SPLIT + startDate + SPLIT + endDate;
    }

    /**
     * 返回统计帖子分数的key
     * @return
     */
    public static String getPostScoreKey() {
        return PREFIX_POST + SPLIT + "score";
    }

}
