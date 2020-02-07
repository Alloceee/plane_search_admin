package com.yws.plane.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.yws.plane.entity.Message;
import com.yws.plane.vo.MessageUserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.sql.Wrapper;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yewenshu123
 * @since 2019-12-24
 */
@Mapper
public interface MessageMapper extends BaseMapper<Message> {
    @Select("SELECT m.*,u.username FROM tb_message as m,tb_user as u WHERE m.uid = u.id ")
    List<MessageUserVO> selectMessageList(@Param(Constants.WRAPPER)Wrapper wrapper);
}
