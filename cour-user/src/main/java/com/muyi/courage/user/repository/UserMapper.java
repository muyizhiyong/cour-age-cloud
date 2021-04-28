package com.muyi.courage.user.repository;


import com.muyi.courage.user.domain.UserDO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


@Mapper
@Repository
public interface UserMapper {

    UserDO selectByPrimaryKey(String username);

    UserDO selectByName(String name);

    @Insert({
            "insert into SYS_USER (USER_NAME, NAME, PASSWORD, STATUS)",
            "values (#{userName,jdbcType=VARCHAR}, #{name,jdbcType=VARCHAR}, " +
                    "#{password,jdbcType=VARCHAR}, #{status,jdbcType=NUMERIC})"
    })
    int insert(UserDO record);
}
