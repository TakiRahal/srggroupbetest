package com.takirahal.srfgroup.modules.cart.mapper;

import com.takirahal.srfgroup.mapper.EntityMapper;
import com.takirahal.srfgroup.modules.cart.dto.OrderDTO;
import com.takirahal.srfgroup.modules.cart.entities.Order;
import com.takirahal.srfgroup.modules.user.mapper.UserMapper;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {UserMapper.class, CartMapper.class})
public interface OrderMapper extends EntityMapper<OrderDTO, Order> {

    @Named("orderWithoutCart")
    // @Mapping(target = "carts", ignore = true)
    @Mapping(target = "carts", source = "carts", qualifiedByName = "idSet")
    OrderDTO toDtoWithoutCarts(Order entity);
}
