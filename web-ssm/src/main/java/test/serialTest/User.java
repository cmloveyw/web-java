package test.serialTest;

import java.io.Serializable;

/**
 * @version 1.0
 * @类名: User.java
 * @描述:
 * @创建人: CM
 * @创建时间: 2018-12-13
 */
public class User implements Serializable {
    private String userName;
    private String password;
    private String sex;

    public User(String userName, String password, String sex) {
        this.userName = userName;
        this.password = password;
        this.sex = sex;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
