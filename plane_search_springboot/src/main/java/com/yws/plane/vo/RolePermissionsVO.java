package com.yws.plane.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class RolePermissionsVO implements Serializable {
    private Integer id;
    /**
     * 角色名称
     */
    private String role_name;

    /**
     * 权限名称
     */
    private String permission_name;
}
