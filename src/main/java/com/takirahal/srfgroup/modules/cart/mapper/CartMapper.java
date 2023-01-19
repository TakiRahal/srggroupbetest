package com.takirahal.srfgroup.modules.cart.mapper;

import com.takirahal.srfgroup.mapper.EntityMapper;
import com.takirahal.srfgroup.modules.cart.dto.CartDTO;
import com.takirahal.srfgroup.modules.cart.entities.Cart;
import com.takirahal.srfgroup.modules.offer.mapper.SellOfferMapper;
import com.takirahal.srfgroup.modules.user.mapper.UserMapper;
import org.mapstruct.*;

import java.util.Set;

@Mapper(componentModel = "spring", uses = {UserMapper.class, SellOfferMapper.class, OrderMapper.class})
public interface CartMapper extends EntityMapper<CartDTO, Cart> {
    @Named("idSet")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "orders", ignore = true)
    @Mapping(target = "id", source = "id")
    Set<CartDTO> toDtoIdSet(Set<Cart> cart);
}
