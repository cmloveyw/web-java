package test.SpringTest.JdbcTemplate;


import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * @类名: UserDao.java
 * @描述: 目标对象
 * @创建人: CM
 * @创建时间: 2019-1-8
 */
public class UserDao {
    //Ioc容器，注入
    private DataSource dataSource;


    public void setDataSource(BasicDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<User> queryAll() {
        String sql = "select id,user_name,user_phone from t_user";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<Map<String, Object>> mapList = jdbcTemplate.queryForList(sql);
        System.out.println(mapList);
        return null;
    }

    public List<User> query() {
        String sql = "select id,user_name,user_phone from t_user";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        /*return jdbcTemplate.query(sql, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet resultSet, int i) throws SQLException {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUserName(resultSet.getString("user_name"));
                user.setUserPhone(resultSet.getString("user_phone"));
                return user;
            }
        });*/
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
        //JDBC连接
        //Connection connection = dataSource.getConnection();
        //Statement
        //Statement statement = connection.createStatement();
        String sql = "insert into t_user(user_name,user_phone) value('cm','123456')";
        /*statement.execute(sql);
        statement.close();
        connection.close();*/
        //使用JdbcTemplate,对JDBC操作优化
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.update(sql);
    }


}
