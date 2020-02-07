package com.yws.plane.interceptor;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yws.plane.entity.Manager;
import com.yws.plane.service.ManagerService;
import com.yws.plane.service.RoleService;
import com.yws.plane.vo.RolePermissionsVO;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CustomRealm extends AuthorizingRealm {

    @Autowired
    private ManagerService managerService;
    @Autowired
    private RoleService roleService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取登录用户名
        String name = (String) principalCollection.getPrimaryPrincipal();
        //根据用户名去数据库查询用户信息
        Manager manager = new Manager();
        QueryWrapper<Manager> wrapper = new QueryWrapper<>();
        manager.setUsername(name);
        wrapper.setEntity(manager);
        Manager manager1 = managerService.getOne(wrapper);
        //添加角色和权限
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        String[] roles = manager1.getRole().split(",");
        for (String roleName:roles){
            simpleAuthorizationInfo.addRole(roleName);
            List<RolePermissionsVO> permissionsVOS = roleService.getRoleByRoleName(roleName);
            for(RolePermissionsVO vo:permissionsVOS){
                simpleAuthorizationInfo.addStringPermission(vo.getPermission_name());
            }
        }
        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //加这一步的目的是在Post请求的时候会先进认证，然后在到请求
        if (authenticationToken.getPrincipal() == null) {
            return null;
        }
        //获取用户信息
        String name = authenticationToken.getPrincipal().toString();
        System.out.println(name);
        Manager manager = new Manager();
        QueryWrapper<Manager> wrapper = new QueryWrapper<>();
        manager.setUsername(name);
        wrapper.setEntity(manager);
        Manager manager1 = managerService.getOne(wrapper);
        if (manager1 == null) {
            //这里返回后会报出对应异常
            return null;
        } else {
            //这里验证authenticationToken和simpleAuthenticationInfo的信息
            return new SimpleAuthenticationInfo(name, manager1.getPassword(), getName());
        }
    }
}
