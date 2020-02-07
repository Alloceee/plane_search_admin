package com.yws.plane.service;

import com.yws.plane.entity.Manager;
import com.yws.plane.entity.User;

public interface TokenService {
    String getUserToken(User user);

    String getAdminToken(Manager manager);
}
