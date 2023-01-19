package com.takirahal.srfgroup.modules.cart.controllers;

import com.takirahal.srfgroup.modules.cart.dto.CartDTO;
import com.takirahal.srfgroup.modules.cart.dto.filter.CartFilter;
import com.takirahal.srfgroup.modules.cart.models.DetailsCarts;
import com.takirahal.srfgroup.modules.cart.services.CartService;
import com.takirahal.srfgroup.utils.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart/")
public class CartController {

    private final Logger log = LoggerFactory.getLogger(CartController.class);

    @Autowired
    CartService cartService;

    @PostMapping("create")
    public ResponseEntity<CartDTO> createCart(@RequestBody CartDTO cartDTO) {
        log.info("REST request to save/update Cart : {}", cartDTO);
        CartDTO result = cartService.saveAndUpdate(cartDTO);
        return new ResponseEntity<>(result, HeaderUtil.createAlert("cart.message_cart_added_successfully", ""), HttpStatus.CREATED);
    }


    @GetMapping("current-user")
    public ResponseEntity<Page<CartDTO>> getCartsByCurrentUser(CartFilter cartFilter, Pageable pageable) {
        log.info("REST request to get Carts by criteria: {}", cartFilter);
        Page<CartDTO> page = cartService.getCartsByCurrentUser(cartFilter, pageable);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }


    /**
     *
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    public ResponseEntity<Boolean> deleteCart(@PathVariable Long id) {
        log.info("REST request to delete Cart : {}", id);
        cartService.delete(id);
        return new ResponseEntity<>(true, HeaderUtil.createAlert("cart.cart_delete_succefully", id.toString()), HttpStatus.OK);
    }


    /**
     *
     * @param cartDTO
     * @return
     */
    @PutMapping("update-quantity")
    public ResponseEntity<DetailsCarts> updateQuantityCart(@RequestBody CartDTO cartDTO) {
        log.info("REST request to update Cart by quantity : {}", cartDTO);
        cartService.updateQuantityCart(cartDTO);

        DetailsCarts detailsCarts = cartService.getDetailsCarts();
        return new ResponseEntity<>(detailsCarts, HttpStatus.OK);
    }


    /**
     *
     * @return
     */
    @GetMapping("details-cart")
    public ResponseEntity<DetailsCarts> getDetailsCarts() {
        log.info("REST request to get details Carts ");
        DetailsCarts detailsCarts = cartService.getDetailsCarts();
        return new ResponseEntity<>(detailsCarts, HttpStatus.OK);
    }
}
