package com.yws.plane.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yws.plane.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yewenshu123
 * @since 2020-01-02
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
