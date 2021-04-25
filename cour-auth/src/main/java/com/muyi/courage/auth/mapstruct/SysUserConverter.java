package com.muyi.courage.auth.mapstruct;

import com.muyi.courage.auth.domain.SysUserDO;
import com.muyi.courage.auth.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SysUserConverter {

	SysUserConverter INSTANCE = Mappers.getMapper(SysUserConverter.class);

	@Mappings({
			@Mapping(target = "password", ignore = true)
	})
	UserDTO domain2dto(SysUserDO domain);


	@Mappings({
			@Mapping(target = "status", ignore = true)
	})
    SysUserDO dto2do(UserDTO domain);


}

