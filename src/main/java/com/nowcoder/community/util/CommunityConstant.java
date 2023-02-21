package com.nowcoder.community.util;

public interface CommunityConstant {

    /* 激活账号时使用 */
    // 激活成功
    int ACTIVATION_SUCCESS = 0;

    // 重复激活
    int ACTIVATION_REPEAT = 1;

    // 激活失败
    int ACTIVATION_FAILURE = 2;

    /* 设置登录凭证（ticket）时使用 */
    // 默认状态的登录凭证的超时时间
    int DEFAULT_EXPIRED_SECONDS = 3600 * 12;

    // 记住状态下的登录凭证的超时时间
    int REMEMBER_EXPIRED_SECONDS = 3600 * 24 * 100;

    /* 帖子方面的功能使用 */
    // 实体类型：帖子
    int ENTITY_TYPE_POST = 1;

    // 实体类型：评论
    int ENTITY_TYPE_COMMENT = 2;

}
