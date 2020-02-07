package com.yws.plane.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yws.plane.entity.Role;
import com.yws.plane.vo.RolePermissionsVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yewenshu123
 * @since 2020-01-06
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    @Select("SELECT tb_role.*,tb_permissions.* FROM tb_role,tb_permissions" +
            "WHERE tb_role.permission_id=tb_permissions.id" +
            "AND tb_role.role_name=#{roleName}")
    List<RolePermissionsVO> getRoleByRoleName(String roleName);
}
