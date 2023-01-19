package com.takirahal.srfgroup.modules.contactus.services.impl;

import com.takirahal.srfgroup.constants.SrfGroupConstants;
import com.takirahal.srfgroup.enums.SourceConnectedDevice;
import com.takirahal.srfgroup.exceptions.BadRequestAlertException;
import com.takirahal.srfgroup.exceptions.UnauthorizedException;
import com.takirahal.srfgroup.modules.contactus.dto.ContactUsDTO;
import com.takirahal.srfgroup.modules.contactus.entities.ContactUs;
import com.takirahal.srfgroup.modules.contactus.mapper.ContactUsMapper;
import com.takirahal.srfgroup.modules.contactus.repositories.ContactUsRepository;
import com.takirahal.srfgroup.modules.contactus.services.ContactUsService;
import com.takirahal.srfgroup.services.impl.ValidateCaptchaService;
import com.takirahal.srfgroup.utils.RequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ContactUsServiceImpl implements ContactUsService {

    private final Logger log = LoggerFactory.getLogger(ContactUsServiceImpl.class);


    @Autowired
    ContactUsRepository contactUsRepository;

    @Autowired
    ContactUsMapper contactUsMapper;

    @Autowired
    ValidateCaptchaService validateCaptchaService;

    @Override
    public ContactUsDTO save(ContactUsDTO contactUsDTO) {
        log.debug("Request to save ContactUs : {}", contactUsDTO);

        final boolean isValidCaptcha = validateCaptchaService.validateCaptcha(contactUsDTO.getCaptchaResponse());
        log.debug("Response recaptcha : {}", isValidCaptcha);

        if(!isValidCaptcha && RequestUtil.getHeaderAttribute(SrfGroupConstants.SOURCE_CONNECTED_DEVICE).equals(SourceConnectedDevice.WEB_BROWSER.toString())){
            throw new UnauthorizedException("Invalid recaptcha");
        }

        if (contactUsDTO.getId() != null) {
            throw new BadRequestAlertException("A new contactUs cannot already have an ID idexists");
        }

        ContactUs contactUs = contactUsMapper.toEntity(contactUsDTO);
        contactUs = contactUsRepository.save(contactUs);
        return contactUsMapper.toDto(contactUs);
    }

    @Override
    public Page<ContactUsDTO> findByCriteria(Pageable pageable) {
        log.debug("Request to get All ContactUs : {}");
        return contactUsRepository.findAll(pageable).map(contactUsMapper::toDto);
    }
}
