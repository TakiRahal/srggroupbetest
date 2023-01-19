package com.takirahal.srfgroup.modules.commentoffer.dto.filter;


import com.takirahal.srfgroup.modules.offer.dto.OfferDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentOfferFilter {
    private Long id;
    private OfferDTO offer;
    private Boolean blockedByReported;
}
