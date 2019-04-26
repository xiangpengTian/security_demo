package com.txp.security.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.txp.security.mapper.UserMapper;
import com.txp.security.model.entity.User;
import com.txp.security.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.txp.security.util.BcryptPassWordUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author txp
 * @since 2019-04-25
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Override
    public User checkUser(String username) {
        Assert.notNull(username,"用户名不能为NULL");
        QueryWrapper<User> mapper = new QueryWrapper<>();
        mapper.eq("username",username);
        return baseMapper.selectOne(mapper);
    }

    @Override
    public int insertUser(User user) {
        String password = user.getPassword();
        user.setPassword(BcryptPassWordUtil.passwordByBcrypt(password));
        return baseMapper.insert(user);
    }
}
