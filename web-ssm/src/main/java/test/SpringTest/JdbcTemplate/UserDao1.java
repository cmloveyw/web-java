package test.SpringTest.JdbcTemplate;


import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * @类名: UserDao.java
 * @描述: 目标对象
 * @创建人: CM
 * @创建时间: 2019-1-8
 */
public class UserDao1 {
    //Ioc容器，注入
    private JdbcTemplate jdbcTemplate;

    public List<User> queryAll() {
        String sql = "select id,user_name,user_phone from t_user";
        List<Map<String, Object>> mapList = jdbcTemplate.queryForList(sql);
        System.out.println(mapList);
        return null;
    }

    public List<User> query() {
        String sql = "select id,user_name,user_phone from t_user";
        return jdbcTemplate.query(sql,
                //行处理
                (resultSet, i) -> {
                    User user = new User();
                    user.setId(resultSet.getInt("id"));
                    user.setUserName(resultSet.getString("user_name"));
                    user.setUserPhone(resultSet.getString("user_phone"));
                    return user;
                }
        );
    }

    public void save() throws Exception {
        String sql = "insert into t_user(user_name,user_phone) value('cm','123456')";
        jdbcTemplate.update(sql);
    }


    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
