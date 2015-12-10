package com.m1racle.yuedong.dao;

import com.m1racle.yuedong.entity.User;

/**
 * User Dao Interface
 * @author sczyh30
 */
public interface UserDao {

    void saveUserInfo(final User user);

    void updateUserInfo(final User user, int id);

    User getUserInfo();

    void removeUserInfo();

}
