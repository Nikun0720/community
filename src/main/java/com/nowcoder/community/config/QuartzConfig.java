package com.nowcoder.community.config;

import com.nowcoder.community.quartz.AlphaJob;
import com.nowcoder.community.quartz.PostScoreRefreshJob;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

/**
 * Spring Quartz的配置类
 * 这个配置的作用仅仅是在第一次被读取到，它封装的信息被初始化到数据库里
 * （前提是application.properties配置了QuartzProperties的那堆东西，否则还是会读取这个配置类）
 * 以后Quartz是访问数据库去调度任务，而不再访问这个配置文件
 */
@Configuration
public class QuartzConfig {

    /*
      FactoryBean可简化Bean的实例化过程:
      1.通过FactoryBean封装Bean的实例化过程.
      2.将FactoryBean装配到Spring容器里.
      3.将FactoryBean注入给其他的Bean.
      4.该Bean得到的是FactoryBean所管理的对象实例.
     */

    /**
     * 刷新帖子分数任务（配置JobDetail）
     * @return
     */
    @Bean
    public JobDetailFactoryBean postScoreRefreshJobDetail() {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(PostScoreRefreshJob.class);
        factoryBean.setName("postScoreRefreshJob");
        factoryBean.setGroup("communityJobGroup");
        factoryBean.setDurability(true);
        factoryBean.setRequestsRecovery(true);
        return factoryBean;
    }

    /**
     * 配置Trigger(SimpleTriggerFactoryBean, CronTriggerFactoryBean)
     * 上面的SimpleTriggerFactoryBean和CronTriggerFactoryBean，是配置Trigger的两种方式
     * @param postScoreRefreshJobDetail
     * @return
     */
    @Bean
    public SimpleTriggerFactoryBean postScoreRefreshTrigger(JobDetail postScoreRefreshJobDetail) {    // 这里取名postScoreRefreshJobDetail，是因为优先同名的Bean，跟上面的FactoryBean对应上
        SimpleTriggerFactoryBean factoryBean = new SimpleTriggerFactoryBean();
        factoryBean.setJobDetail(postScoreRefreshJobDetail);
        factoryBean.setName("postScoreRefreshTrigger");
        factoryBean.setGroup("communityTriggerGroup");
        factoryBean.setRepeatInterval(1000 * 60 * 5);    // 5分钟执行一次
        factoryBean.setJobDataMap(new JobDataMap());
        return factoryBean;
    }

}
