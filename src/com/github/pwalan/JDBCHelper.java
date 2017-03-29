package com.github.pwalan;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.HashMap;

/**
 * 连接数据库的辅助类
 * @author AlanP
 * @Date 2017/3/29
 */
public class JDBCHelper {
    public static HashMap<String, JdbcTemplate> templateMap
            = new HashMap<String, JdbcTemplate>();

    public static JdbcTemplate createMysqlTemplate(String templateName,
                                                   String url, String username, String password,
                                                   int initialSize, int maxActive) {

        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setInitialSize(initialSize);
        dataSource.setMaxActive(maxActive);
        JdbcTemplate template = new JdbcTemplate(dataSource);
        templateMap.put(templateName, template);
        return template;
    }

    public static JdbcTemplate getJdbcTemplate(String templateName){
        return templateMap.get(templateName);
    }

    public static void main(String[] args){
        JdbcTemplate jdbcTemplate = null;
        try{
            jdbcTemplate=JDBCHelper.createMysqlTemplate("mysql", "jdbc:mysql://localhost/test?useUnicode=true&characterEncoding=utf8",
                    "root","pwalan",5,30);
        }catch(Exception e){
            jdbcTemplate = null;
            e.printStackTrace();
        }
        if(jdbcTemplate!=null){
            int updates=jdbcTemplate.update("insert into tb_content"
                            +" (title,url) value(?,?)",
                    "test","http://test.com");
            if(updates==1){
                System.out.println("mysql插入成功");
            }
        }
    }
}
