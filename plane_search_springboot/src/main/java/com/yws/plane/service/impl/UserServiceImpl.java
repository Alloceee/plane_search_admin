package com.yws.plane.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yws.plane.entity.User;
import com.yws.plane.mapper.UserMapper;
import com.yws.plane.service.UserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yewenshu123
 * @since 2020-01-02
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
