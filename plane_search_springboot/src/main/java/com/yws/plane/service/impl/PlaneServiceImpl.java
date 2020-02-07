package com.yws.plane.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yws.plane.entity.Plane;
import com.yws.plane.mapper.PlaneMapper;
import com.yws.plane.service.PlaneService;
import com.yws.plane.vo.PlaneCompanyVO;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yewenshu123
 * @since 2019-12-23
 */
@Service
public class PlaneServiceImpl extends ServiceImpl<PlaneMapper, Plane> implements PlaneService {

    @Override
    public Page<PlaneCompanyVO> selectAll(Page<PlaneCompanyVO> page, Wrapper<PlaneCompanyVO> wrapper) {
        return baseMapper.selectAll(page, wrapper);
    }
}
