package com.microshop.userservice.mapper;

import com.microshop.userservice.dto.response.AddressResponse;
import com.microshop.userservice.entity.Address;
import org.mapstruct.Mapper;
@Mapper(componentModel = "spring")
public interface AddressMapper {
    AddressResponse toResponse(Address a);
}

