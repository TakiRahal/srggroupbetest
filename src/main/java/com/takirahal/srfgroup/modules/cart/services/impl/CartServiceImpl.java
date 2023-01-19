package com.takirahal.srfgroup.modules.cart.services.impl;

import com.takirahal.srfgroup.exceptions.BadRequestAlertException;
import com.takirahal.srfgroup.exceptions.ResouorceNotFoundException;
import com.takirahal.srfgroup.exceptions.UnauthorizedException;
import com.takirahal.srfgroup.modules.cart.dto.CartDTO;
import com.takirahal.srfgroup.modules.cart.dto.filter.CartFilter;
import com.takirahal.srfgroup.modules.cart.entities.Cart;
import com.takirahal.srfgroup.modules.cart.enums.StatusCart;
import com.takirahal.srfgroup.modules.cart.mapper.CartMapper;
import com.takirahal.srfgroup.modules.cart.models.DetailsCartGlobal;
import com.takirahal.srfgroup.modules.cart.models.DetailsCarts;
import com.takirahal.srfgroup.modules.cart.repositories.CartRepository;
import com.takirahal.srfgroup.modules.cart.services.CartService;
import com.takirahal.srfgroup.modules.offer.entities.SellOffer;
import com.takirahal.srfgroup.modules.offer.repositories.SellOfferRepository;
import com.takirahal.srfgroup.modules.user.dto.filter.UserOfferFilter;
import com.takirahal.srfgroup.modules.user.exceptioins.AccountResourceException;
import com.takirahal.srfgroup.modules.user.mapper.UserMapper;
import com.takirahal.srfgroup.security.UserPrincipal;
import com.takirahal.srfgroup.utils.RequestUtil;
import com.takirahal.srfgroup.utils.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import jakarta.persistence.criteria.Predicate;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    private final Logger log = LoggerFactory.getLogger(CartServiceImpl.class);

    @Autowired
    CartRepository cartRepository;

    @Autowired
    CartMapper cartMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    SellOfferRepository sellOfferRepository;


//    @Value("${cart.tax-delivery}")
//    private Double taxDelivery;

    /**
     *
     * @param cartDTO
     * @return
     */
    @Override
    public CartDTO saveAndUpdate(CartDTO cartDTO) {
        log.debug("Request to save Cart : {}", cartDTO);

        if (cartDTO.getId() != null) {
            throw new BadRequestAlertException(RequestUtil.messageTranslate("common.entity_already_exist"));
        }

        long nbeCart = cartRepository.getCountCartBySellOfferAndUser(cartDTO.getSellOffer().getId());

        UserPrincipal currentUser = SecurityUtils.getCurrentUser()
                .orElseThrow(() -> new AccountResourceException(RequestUtil.messageTranslate("common.account_resource_not_found")));
        cartDTO.setUser(userMapper.toCurrentUserPrincipal(currentUser));

        Cart cart = cartMapper.toEntity(cartDTO);
        cart.setStatus(StatusCart.STANDBY.toString());

        Optional<SellOffer> sellOfferOption = sellOfferRepository.findById(cartDTO.getSellOffer().getId());

        if( sellOfferOption.get().getAmount()==null ){
            throw new BadRequestAlertException(RequestUtil.messageTranslate("details_offer.missing_amount"));
        }

        if( sellOfferOption.get().getAvailable()==null || !sellOfferOption.get().getAvailable() ){
            throw new BadRequestAlertException(RequestUtil.messageTranslate("details_offer.not_available_offer"));
        }

        // Update
        if( nbeCart > 0 ){
            Optional<Cart> cartExist = cartRepository.findBySellOfferAndUser(cart.getSellOffer().getId(), currentUser.getId());
            if(cartExist.isPresent()){
                int quantity = cartExist.get().getQuantity() + cartDTO.getQuantity();
                cart.setId(cartExist.get().getId());
                cart.setQuantity(quantity);

                // Set new total
                if( sellOfferOption.isPresent() ){
                    Double newTotal = sellOfferOption.get().getAmount()*quantity+sellOfferOption.get().getShippingCost();
                    cart.setTotal(newTotal);
                }

                cart = cartRepository.save(cart);
            }
        }
        // First time: Cart
        else{
            if( sellOfferOption.isPresent() ){
                cart.setTotal(sellOfferOption.get().getAmount()*cartDTO.getQuantity()+sellOfferOption.get().getShippingCost());
            }
            cart.setPassedDate(Instant.now());
            cart = cartRepository.save(cart);
        }

        return cartMapper.toDto(cart);
    }

    @Override
    public Page<CartDTO> getCartsByCurrentUser(CartFilter cartFilter, Pageable pageable) {
        log.debug("Request to get all Carts for current user : {}", cartFilter);
        Long useId = SecurityUtils.getIdByCurrentUser();
        UserOfferFilter userOfferFilter = new UserOfferFilter();
        userOfferFilter.setId(useId);
        cartFilter.setStatus(StatusCart.STANDBY.toString());
        cartFilter.setUser(userOfferFilter);
        return findByCriteria(cartFilter, pageable);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Cart : {}", id);

        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new ResouorceNotFoundException(RequestUtil.messageTranslate("common.resource_not_found")));

        Long useId = SecurityUtils.getIdByCurrentUser();
        if (!Objects.equals(useId, cart.getUser().getId())) {
            throw new UnauthorizedException(RequestUtil.messageTranslate("common.unautorize_action"));
        }

        cartRepository.deleteById(id);
    }

    @Override
    public CartDTO updateQuantityCart(CartDTO cartDTO) {
        log.debug("Request to update quantity to Cart : {}", cartDTO.getId());
        Cart cart = cartRepository.findById(cartDTO.getId())
                .orElseThrow(() -> new ResouorceNotFoundException(RequestUtil.messageTranslate("common.resource_not_found")));
        Long useId = SecurityUtils.getIdByCurrentUser();
        if (!Objects.equals(useId, cart.getUser().getId())) {
            throw new UnauthorizedException(RequestUtil.messageTranslate("common.unautorize_action"));
        }
        Double newTotal = cart.getSellOffer().getAmount()*cartDTO.getQuantity()+cart.getSellOffer().getShippingCost();
        cart.setTotal(newTotal);
        cart.setQuantity(cartDTO.getQuantity());
        cart = cartRepository.save(cart);
        return cartMapper.toDto(cart);
    }

    @Override
    public DetailsCarts getDetailsCarts() {
        log.debug("Request to get details Cart ");
        Pageable pageable = PageRequest.of(0, 100);
        CartFilter cartFilter = new CartFilter();
        Page<CartDTO> page = getCartsByCurrentUser(cartFilter, pageable);
        return getDetailsCartsByPage(page);
    }

    @Override
    public DetailsCarts getDetailsCartsByPage(Page<CartDTO> page) {
        log.debug("Request to get details Cart by page ");
        DetailsCarts detailsCarts = new DetailsCarts();
        detailsCarts.setDetailsCartGlobal(new ArrayList<>());
        page.getContent().stream().forEach(cart -> {
            if( !Objects.isNull(cart.getTotal()) ){
                DetailsCartGlobal detailsCartGlobal = new DetailsCartGlobal();
                detailsCartGlobal.setNumberOfProducts(cart.getQuantity());
                detailsCartGlobal.setShippingCost(cart.getSellOffer().getShippingCost());
                detailsCartGlobal.setTotalCarts(cart.getSellOffer().getAmount()*cart.getQuantity()+cart.getSellOffer().getShippingCost());
                detailsCarts.getDetailsCartGlobal().add(detailsCartGlobal);
            }
        });
        Double somme = 0D;
        for (DetailsCartGlobal detailsCartGlobal : detailsCarts.getDetailsCartGlobal()) {
            somme = somme + detailsCartGlobal.getTotalCarts();
        }
        detailsCarts.setTotalGlobalCarts(somme);
        return detailsCarts;
    }

    @Override
    public void updateListCartByStatus(List<CartDTO> content) {
        content.stream().forEach(cartDTO -> {
            cartRepository.findById(cartDTO.getId())
                    .map(
                            cart -> {
                                // Update Status
                                cart.setStatus(StatusCart.PASSED.toString());
                                cartRepository.save(cart);
                                log.debug("Cart update: {}", cart);
                                return cart;
                            }
                    );

        });
    }

    private Page<CartDTO> findByCriteria(CartFilter cartFilter, Pageable page) {
        return cartRepository.findAll(createSpecification(cartFilter), page).map(cartMapper::toDto);
    }

    protected Specification<Cart> createSpecification(CartFilter cartFilter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if( cartFilter.getStatus() != null){
                predicates.add(criteriaBuilder.equal(root.get("status"), cartFilter.getStatus()));
            }

            if ( cartFilter.getUser() != null && cartFilter.getUser().getId() != null ) {
                predicates.add(criteriaBuilder.equal(root.get("user").get("id"), cartFilter.getUser().getId()));
            }

            query.orderBy(criteriaBuilder.desc(root.get("id")));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
