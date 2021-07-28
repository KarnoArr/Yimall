package com.zackyj.Mmall.Config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/***
 * Knife4j 基础自动配置类
 * @since:knife4j 2.0.0
 * @author <a href="mailto:xiaoymin@foxmail.com">xiaoymin@foxmail.com</a>
 * 2019/08/28 21:08
 */
@Configuration
@ComponentScan(
        basePackages = {
                "com.zackyj.Mmall.controller"
        }
)
public class Knife4jAutoConfiguration {


}
