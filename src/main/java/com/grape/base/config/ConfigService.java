package com.grape.base.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Properties;


/**
 * 读配置文件
 *
 * @author duwenlei
 **/
@Component
@Slf4j
public class ConfigService {
    private static final String AQO_CONFIG_FILE_NAME = "aqo.config.file";
    private static final String AQO_CONFIG_FILE_PATH_DEFAULT = "config/aqo.properties";

    @Autowired
    private Environment environment;
    private transient Properties properties;


    public String getValue(String key, String defaultValue) {
        return environment.getProperty(key, defaultValue);
    }

    public String getAqoConfigPath() {
        return getValue(AQO_CONFIG_FILE_NAME, AQO_CONFIG_FILE_PATH_DEFAULT);
    }

    private void initGrapeConfigEnv(String configFile) {
        try {
            if (properties == null) {
                properties = PropertiesLoaderUtils.loadAllProperties(configFile);
            }
        } catch (IOException e) {
            log.error("读取配置文件错误，[{}}={}]，原因：{}", AQO_CONFIG_FILE_NAME, configFile, e.getMessage(), e);
        }
    }

    /**
     * 读取项目自定义配置函数
     *
     * @param key          key
     * @param defaultValue 默认值
     * @return 值
     */
    public String getAqoValue(String key, String defaultValue) {
        if (properties == null) {
            initGrapeConfigEnv(getAqoConfigPath());
        }
        return properties.getProperty(key, defaultValue);
    }

    /**
     * 读取项目自定义配置函数
     *
     * @param key key
     * @return 值
     */
    public String getAqoValue(String key) {
        if (properties == null) {
            initGrapeConfigEnv(getAqoConfigPath());
        }
        return properties.getProperty(key);
    }

}
