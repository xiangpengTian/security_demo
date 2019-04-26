package com.txp.security.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.txp.security.model.entity.User;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author txp
 * @since 2019-04-25
 */
public interface UserService extends IService<User> {

    User checkUser(String username);

    int insertUser(User user);
}
