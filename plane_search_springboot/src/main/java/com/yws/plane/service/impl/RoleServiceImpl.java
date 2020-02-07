package com.yws.plane.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yws.plane.entity.Role;
import com.yws.plane.mapper.RoleMapper;
import com.yws.plane.service.RoleService;
import com.yws.plane.vo.RolePermissionsVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yewenshu123
 * @since 2020-01-06
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Override
    public List<RolePermissionsVO> getRoleByRoleName(String roleName) {
        return baseMapper.getRoleByRoleName(roleName);
    }
}
