package com.nowcoder.community.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.File;

/**
 * skhtmltopdf的配置类
 * spring启动时，由于这个类带有@Configuration，会认为这是个配置类，就会先加载（实例化）这个配置类
 * 然后自动调用init方法，原因是有@PostConstruct
 */
@Configuration
public class WkConfig {

    private static final Logger logger = LoggerFactory.getLogger(WkConfig.class);

    @Value("${wk.image.storage}")
    private String wkImageStorage;

    @PostConstruct
    public void init() {
        // 创建WK图片目录
        File file = new File(wkImageStorage);
        if(!file.exists()) {
            file.mkdir();
            logger.info("创建WK图片目录：" + wkImageStorage);
        }
    }

}
