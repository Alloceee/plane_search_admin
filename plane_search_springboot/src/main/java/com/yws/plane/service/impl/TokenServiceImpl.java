package com.yws.plane.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.yws.plane.entity.Manager;
import com.yws.plane.entity.User;
import com.yws.plane.service.TokenService;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService {
    @Override
    public String getUserToken(User user) {
        String token="";
        token= JWT.create().withAudience(String.valueOf(user.getId()))
                .sign(Algorithm.HMAC256(user.getPassword()));
        return token;
    }

    @Override
    public String getAdminToken(Manager manager) {
        String token="";
        token= JWT.create().withAudience(String.valueOf(manager.getId()))
                .sign(Algorithm.HMAC256(manager.getPassword()));
        return token;
    }
}
