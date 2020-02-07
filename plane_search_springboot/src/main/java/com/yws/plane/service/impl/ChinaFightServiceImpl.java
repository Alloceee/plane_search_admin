package com.yws.plane.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yws.plane.entity.ChinaFight;
import com.yws.plane.mapper.ChinaFightMapper;
import com.yws.plane.service.ChinaFightService;
import com.yws.plane.vo.FightCompanyPlaneVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yewenshu123
 * @since 2019-12-23
 */
@Service
public class ChinaFightServiceImpl extends ServiceImpl<ChinaFightMapper, ChinaFight> implements ChinaFightService {

    @Override
    public Page<FightCompanyPlaneVO> page(Page<FightCompanyPlaneVO> page, Wrapper<FightCompanyPlaneVO> wrapper) {
        return page.setRecords(this.baseMapper.getChinaFightCompanyPlane(page, wrapper));
    }

    @Override
    public List<FightCompanyPlaneVO> allOptions() {
        return this.baseMapper.allOptions();
    }

    @Override
    public FightCompanyPlaneVO getChinaFightCompanyPlane(Long fight_id) {
        return baseMapper.getChinaFightCompanyPlaneOne(fight_id);
    }
}
