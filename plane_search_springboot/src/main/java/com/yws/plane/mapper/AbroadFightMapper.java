package com.yws.plane.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yws.plane.entity.AbroadFight;
import com.yws.plane.vo.FightCompanyPlaneVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yewenshu123
 * @since 2019-12-23
 */
@Mapper
public interface AbroadFightMapper extends BaseMapper<AbroadFight> {
    @Select("SELECT tb_plane.*,tb_company.*,tb_abroad_fight.* FROM tb_plane,tb_company,tb_abroad_fight" +
            " ${ew.customSqlSegment} AND tb_abroad_fight.plane_id=tb_plane.id AND tb_plane.company_id = tb_company.cid")
    List<FightCompanyPlaneVO> page(Page<FightCompanyPlaneVO> page, @Param(Constants.WRAPPER) Wrapper<FightCompanyPlaneVO> wrapper);

    @Select("SELECT tb_plane.*,tb_company.*,tb_abroad_fight.* FROM tb_plane,tb_company,tb_abroad_fight" +
            " WHERE AND tb_abroad_fight.plane_id=tb_plane.id AND tb_plane.company_id = tb_company.cid")
    List<FightCompanyPlaneVO> allOptions();

    @Select("SELECT tb_plane.*,tb_company.*,tb_abroad_fight.* FROM tb_plane,tb_company,tb_abroad_fight" +
            " WHERE tb_abroad_fight.plane_id=tb_plane.id AND tb_plane.company_id = tb_company.cid " +
            "AND tb_abroad_fight.fid=#{fight_id}")
    FightCompanyPlaneVO getAbroadFightCompanyPlaneOne(@Param("fight_id")Long fight_id);
}
