package com.muyi.courage.auth.repository;


import com.muyi.courage.auth.po.SysUserPO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;


@Mapper
@Repository
public interface SysUserMapper {

	int selectCountByUserName(String userName);

	SysUserPO selectByPrimaryKey(String username);
}
