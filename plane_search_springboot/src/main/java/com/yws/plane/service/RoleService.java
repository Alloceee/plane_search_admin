package com.yws.plane.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yws.plane.entity.Role;
import com.yws.plane.vo.RolePermissionsVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yewenshu123
 * @since 2020-01-06
 */
public interface RoleService extends IService<Role> {

    List<RolePermissionsVO> getRoleByRoleName(String roleName);

}
