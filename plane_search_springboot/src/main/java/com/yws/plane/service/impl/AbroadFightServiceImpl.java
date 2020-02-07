package com.yws.plane.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yws.plane.entity.AbroadFight;
import com.yws.plane.mapper.AbroadFightMapper;
import com.yws.plane.service.AbroadFightService;
import com.yws.plane.vo.FightCompanyPlaneVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yewenshu123
 * @since 2019-12-23
 */
@Service
public class AbroadFightServiceImpl extends ServiceImpl<AbroadFightMapper, AbroadFight> implements AbroadFightService {

    @Override
    public Page<FightCompanyPlaneVO> page(Page<FightCompanyPlaneVO> page, Wrapper<FightCompanyPlaneVO> wrapper) {
        return page.setRecords(this.baseMapper.page(page,wrapper));
    }
    @Override
    public List<FightCompanyPlaneVO> allOptions() {
        return this.baseMapper.allOptions();
    }

    @Override
    public FightCompanyPlaneVO getAbroadFightCompanyPlane(Long fight_id) {
        return baseMapper.getAbroadFightCompanyPlaneOne(fight_id);
    }

}
