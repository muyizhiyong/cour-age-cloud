package com.muyi.courage.user.mapstruct;

import com.muyi.courage.user.domain.UserDO;
import com.muyi.courage.user.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserConverter {

	UserConverter INSTANCE = Mappers.getMapper(UserConverter.class);

	@Mappings({
			@Mapping(target = "password", ignore = true)
	})
	UserDTO domain2dto(UserDO domain);


	@Mappings({
			@Mapping(target = "status", ignore = true)
	})
	UserDO dto2do(UserDTO domain);


}

