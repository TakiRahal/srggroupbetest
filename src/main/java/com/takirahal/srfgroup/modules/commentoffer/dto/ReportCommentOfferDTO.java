package com.takirahal.srfgroup.modules.commentoffer.dto;

import com.takirahal.srfgroup.modules.user.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportCommentOfferDTO implements Serializable {

    private Long id;
    private CommentOfferDTO commentOffer;
    private UserDTO user;
}
