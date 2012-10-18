/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2012-10-18
 * <修改描述:>
 */
package com.tx.core.log.p6spy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.p6spy.engine.logging.appender.P6Logger;

/**
 * <在p6spy中利用logback统一打印日志，统一日志记录的位置>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2012-10-18]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class P6SpyLogbackLogger implements P6Logger {
    
    private static final Logger logger = LoggerFactory.getLogger("p6spy");
    
    private String lastEntry;
    
    public String getLastEntry() {
        return lastEntry;
    }
    
    public void setLastEntry(String lastEntry) {
        this.lastEntry = lastEntry;
    }
    
    public void logException(Exception e) {
        logger.debug(e.getMessage(), e);
    }
    
    public void logSQL(int connectionId, String now, long elapsed,
            String category, String prepared, String sql) {
        if (!"resultset".equals(category)) {
            logger.debug(trim(sql));
        }
    }
    
    public void logText(String text) {
        logger.debug(text);
        lastEntry = text;
    }
    
    public static String trim(String sql) {
        StringBuilder sb = new StringBuilder(256);
        int i = 0, n = sql.length();
        boolean b = true;
        char c;
        while (i < n) {
            c = sql.charAt(i);
            switch (c) {
                case '"':
                    b = false;
                    sb.append(c);
                    ++i;
                    while (i < n) {
                        c = sql.charAt(i);
                        if (c == '"') {
                            if (i + 1 >= n || sql.charAt(i + 1) != '"') {
                                sb.append(c);
                                ++i;
                                break;
                            }
                            sb.append(c);
                            c = sql.charAt(i);
                        }
                        sb.append(c);
                        ++i;
                    }
                    break;
                case '\'':
                    b = false;
                    sb.append(c);
                    ++i;
                    while (i < n) {
                        c = sql.charAt(i);
                        if (c == '\'') {
                            if (sql.charAt(i + 1) != '\'') {
                                sb.append(c);
                                break;
                            }
                            sb.append(c);
                            ++i;
                            c = sql.charAt(i);
                        }
                        sb.append(c);
                        ++i;
                    }
                    break;
                case ' ':
                    if (!b) {
                        b = true;
                        sb.append(c);
                    }
                    break;
                case '\n':
                case '\r':
                case '\t':
                    if (!b) {
                        b = true;
                        sb.append(' ');
                    }
                    break;
                default:
                    b = false;
                    sb.append(c);
                    break;
            }
            ++i;
        }
        return sb.toString();
    }
}
