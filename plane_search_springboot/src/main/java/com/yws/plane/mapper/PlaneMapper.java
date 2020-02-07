package com.yws.plane.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yws.plane.entity.Plane;
import com.yws.plane.vo.PlaneCompanyVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yewenshu123
 * @since 2019-12-23
 */
@Mapper
public interface PlaneMapper extends BaseMapper<Plane> {
    @Select("SELECT tb_plane.*,tb_company.name,tb_company.cid FROM tb_plane,tb_company ${ew.customSqlSegment} AND tb_plane.company_id = tb_company.cid")
    Page<PlaneCompanyVO> selectAll(Page<PlaneCompanyVO> page, @Param(Constants.WRAPPER) Wrapper<PlaneCompanyVO> wrapper);
}
