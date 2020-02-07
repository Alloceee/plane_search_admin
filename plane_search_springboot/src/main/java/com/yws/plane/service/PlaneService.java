package com.yws.plane.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yws.plane.entity.Plane;
import com.yws.plane.vo.PlaneCompanyVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yewenshu123
 * @since 2019-12-23
 */
public interface PlaneService extends IService<Plane> {
    Page<PlaneCompanyVO> selectAll(Page<PlaneCompanyVO> page, Wrapper<PlaneCompanyVO> wrapper);
}
