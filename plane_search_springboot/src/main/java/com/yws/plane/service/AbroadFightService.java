package com.yws.plane.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yws.plane.entity.AbroadFight;
import com.yws.plane.vo.FightCompanyPlaneVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yewenshu123
 * @since 2019-12-23
 */
public interface AbroadFightService extends IService<AbroadFight> {
    Page<FightCompanyPlaneVO> page(Page<FightCompanyPlaneVO> page, Wrapper<FightCompanyPlaneVO> wrapper);
    List<FightCompanyPlaneVO> allOptions();
    FightCompanyPlaneVO getAbroadFightCompanyPlane(@Param("fight_id")Long fight_id);
}
