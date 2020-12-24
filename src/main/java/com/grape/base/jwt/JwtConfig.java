package com.grape.base.jwt;

import com.grape.base.config.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

/**
 * @author duwenlei
 **/
@Component
public class JwtConfig {

    @Autowired
    private ConfigService configService;

    /**
     * 获取 Token 前缀
     *
     * @return
     */
    public String getTokenPrefix() {
        return configService.getAqoValue("aqo.jwt.token.prefix", "");
    }

    /**
     * 获取 token header
     *
     * @return
     */
    public String getTokenHeader() {
        return configService.getAqoValue("aqo.jwt.token.header", "Authorization");
    }

    /**
     * 获取 Token 过期时间
     * 单位
     * "s":秒
     * "m":分
     * "h":时
     * "d":日
     * "M":月
     * "y":年
     * <p>
     * 默认 10 天
     *
     * @return
     */
    public Date getTokenExpire() {
        String tokenExpireStr = configService.getAqoValue("aqo.jwt.token.expire", "864000s");
        Date expireDate = null;
        Calendar calendar = Calendar.getInstance();
        try {
            // 时间
            String time = tokenExpireStr.substring(0, tokenExpireStr.length() - 1);
            // 时间单位
            String unit = tokenExpireStr.substring(tokenExpireStr.length() - 1);
            int calendarField = getUnit2CalendarField(unit);
            calendar.add(calendarField, Integer.parseInt(time));
            expireDate = calendar.getTime();
        } catch (Exception e) {
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            expireDate = calendar.getTime();
        }
        return expireDate;
    }

    private int getUnit2CalendarField(String unit) {
        switch (unit) {
            // 分
            case "m":
                return Calendar.MINUTE;
            // 时
            case "h":
                return Calendar.HOUR_OF_DAY;
            // 日
            case "d":
                return Calendar.DAY_OF_MONTH;
            // 月
            case "M":
                return Calendar.MONTH;
            // 年
            case "y":
                return Calendar.YEAR;
            // 秒
            case "s":
            default:
                return Calendar.SECOND;
        }
    }
}
