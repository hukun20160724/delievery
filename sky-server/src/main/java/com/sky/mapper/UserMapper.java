package com.sky.mapper;

import com.sky.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * ClassName: UserMapper
 * Package: com.sky.mapper
 * Description:
 *
 * @Author Kun Hu
 * @Create 6/7/24 20:53
 * @Version 1.0
 */
@Mapper
public interface UserMapper {
    
    @Select("select * from user where openid=#{openId}")
    User getByOpenId(String openId);

    void insert(User user);
}
