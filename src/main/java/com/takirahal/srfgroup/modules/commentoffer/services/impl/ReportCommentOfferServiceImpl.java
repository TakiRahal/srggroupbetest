package com.takirahal.srfgroup.modules.commentoffer.services.impl;

import com.takirahal.srfgroup.exceptions.BadRequestAlertException;
import com.takirahal.srfgroup.exceptions.ResouorceNotFoundException;
import com.takirahal.srfgroup.modules.commentoffer.dto.ReportCommentOfferDTO;
import com.takirahal.srfgroup.modules.commentoffer.entities.CommentOffer;
import com.takirahal.srfgroup.modules.commentoffer.entities.ReportCommentOffer;
import com.takirahal.srfgroup.modules.commentoffer.mapper.CommentOfferMapper;
import com.takirahal.srfgroup.modules.commentoffer.mapper.ReportCommentOfferMapper;
import com.takirahal.srfgroup.modules.commentoffer.repositories.CommentOfferRepository;
import com.takirahal.srfgroup.modules.commentoffer.repositories.ReportCommentOfferRepository;
import com.takirahal.srfgroup.modules.commentoffer.services.ReportCommentOfferService;
import com.takirahal.srfgroup.modules.user.dto.UserDTO;
import com.takirahal.srfgroup.modules.user.exceptioins.AccountResourceException;
import com.takirahal.srfgroup.modules.user.mapper.UserMapper;
import com.takirahal.srfgroup.utils.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ReportCommentOfferServiceImpl implements ReportCommentOfferService {

    private final Logger log = LoggerFactory.getLogger(ReportCommentOfferServiceImpl.class);

    @Autowired
    ReportCommentOfferMapper reportCommentOfferMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    CommentOfferMapper commentOfferMapper;

    @Autowired
    ReportCommentOfferRepository reportCommentOfferRepository;

    @Autowired
    CommentOfferRepository commentOfferRepository;

    @Value("${comment_offer.nbe-report}")
    private int maxNbeReportComment;

    @Override
    public ReportCommentOfferDTO save(ReportCommentOfferDTO reportCommentOfferDTO) {

        log.debug("Request to save Report Comment: {}", reportCommentOfferDTO);

        if (reportCommentOfferDTO.getId() != null) {
            throw new BadRequestAlertException("A new ReportCommentOffer cannot already have an ID idexists");
        }


        UserDTO currentUser = SecurityUtils.getCurrentUser()
                .map(userMapper::toCurrentUserPrincipal)
                .orElseThrow(() -> new AccountResourceException("Current user login not found"));


        CommentOffer commentOffer = commentOfferMapper.toEntity(reportCommentOfferDTO.getCommentOffer());

        // Check if exist
        if( reportCommentOfferRepository.existsByCommentOfferAndUser(commentOffer, userMapper.toEntity(currentUser)) ){
            throw new BadRequestAlertException("Already reported");
        }

        reportCommentOfferDTO.setUser(currentUser);
        ReportCommentOffer reportCommentOffer = reportCommentOfferMapper.toEntity(reportCommentOfferDTO);
        reportCommentOffer = reportCommentOfferRepository.save(reportCommentOffer);

        // Update status report comment offer
        if( reportCommentOfferRepository.nbeReportedByCommentOfferId(reportCommentOfferDTO.getCommentOffer().getId()) >= maxNbeReportComment ){
            CommentOffer commentOfferUpdate = commentOfferRepository.findById(reportCommentOfferDTO.getCommentOffer().getId())
                    .orElseThrow(() -> new ResouorceNotFoundException("Comment Offer not found"));
            commentOfferUpdate.setBlockedByReported(true);
            commentOfferRepository.save(commentOfferUpdate);
        }

        return reportCommentOfferMapper.toDto(reportCommentOffer);
    }
}
