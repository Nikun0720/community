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

    // 实体类型：用户
    int ENTITY_TYPE_USER = 3;

    // 帖子类型：普通
    int POST_TYPE_NORMAL = 0;

    // 帖子类型：置顶
    int POST_TYPE_TOP = 1;

    // 帖子状态：正常
    int POST_STATUS_NORMAL = 0;

    // 帖子状态：加精
    int POST_STATUS_WONDERFUL = 1;

    // 帖子状态：拉黑（删除）
    int POST_STATUS_DELETE = 2;

    /* 系统消息方面的常量 */
    // 主题：评论
    String TOPIC_COMMENT = "comment";

    // 主题：点赞
    String TOPIC_LIKE = "like";

    // 主题：关注
    String TOPIC_FOLLOW = "follow";

    //主题：发帖（这个是同步到es服务器上使用的）
    String TOPIC_PUBLISH = "publish";

    //主题：删帖（这个是同步到es服务器上使用的）
    String TOPIC_DELETE = "delete";

    // 系统用户id
    int SYSTEM_USER_ID = 1;

    /* 权限控制方面使用 */
    // 权限：普通用户
    String AUTHORITY_USER = "user";

    // 权限：管理员
    String AUTHORITY_ADMIN = "admin";

    // 权限：版主
    String AUTHORITY_MODERATOR = "moderator";

    /*帖子展示时的排序方法*/
    // 按时间排序（含置顶）
    String POST_ORDER_TIME = "0";

    // 按热度（score）排序（含置顶）
    String POST_ORDER_HOT = "1";

}
