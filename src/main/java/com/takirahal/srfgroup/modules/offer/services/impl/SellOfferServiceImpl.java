package com.takirahal.srfgroup.modules.offer.services.impl;

import com.takirahal.srfgroup.exceptions.BadRequestAlertException;
import com.takirahal.srfgroup.exceptions.UnauthorizedException;
import com.takirahal.srfgroup.modules.favoriteuser.entities.FavoriteUser;
import com.takirahal.srfgroup.modules.favoriteuser.repositories.FavoriteUserRepository;
import com.takirahal.srfgroup.modules.notification.dto.NotificationDTO;
import com.takirahal.srfgroup.modules.notification.enums.ModuleNotification;
import com.takirahal.srfgroup.modules.notification.services.NotificationService;
import com.takirahal.srfgroup.modules.offer.dto.OfferImagesDTO;
import com.takirahal.srfgroup.modules.offer.dto.SellOfferDTO;
import com.takirahal.srfgroup.modules.offer.entities.SellOffer;
import com.takirahal.srfgroup.modules.offer.dto.filter.SellOfferFilter;
import com.takirahal.srfgroup.modules.offer.mapper.SellOfferMapper;
import com.takirahal.srfgroup.modules.offer.repositories.OfferImagesRepository;
import com.takirahal.srfgroup.modules.offer.repositories.SellOfferRepository;
import com.takirahal.srfgroup.modules.offer.services.OfferImagesService;
import com.takirahal.srfgroup.modules.offer.services.SellOfferService;
import com.takirahal.srfgroup.modules.suggestion.entities.SuggestSearch;
import com.takirahal.srfgroup.modules.suggestion.enums.ModuleSuggestion;
import com.takirahal.srfgroup.modules.suggestion.services.SuggestSearchService;
import com.takirahal.srfgroup.modules.user.entities.UserOneSignal;
import com.takirahal.srfgroup.modules.user.exceptioins.AccountResourceException;
import com.takirahal.srfgroup.modules.user.mapper.UserMapper;
import com.takirahal.srfgroup.modules.user.services.UserOneSignalService;
import com.takirahal.srfgroup.security.UserPrincipal;
import com.takirahal.srfgroup.services.impl.OneSignalService;
import com.takirahal.srfgroup.services.impl.StorageService;
import com.takirahal.srfgroup.utils.CommonUtil;
import com.takirahal.srfgroup.utils.RequestUtil;
import com.takirahal.srfgroup.utils.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import jakarta.persistence.criteria.Predicate;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class SellOfferServiceImpl implements SellOfferService {

    private final Logger log = LoggerFactory.getLogger(SellOfferServiceImpl.class);

    @Value("${elasticsearch.available}")
    String elasticSearchAvailable;

    @Autowired
    SellOfferMapper sellOfferMapper;

    @Autowired
    SellOfferRepository sellOfferRepository;

    @Autowired
    OfferImagesService offerImagesService;

    @Autowired
    OfferImagesRepository offerImagesRepository;

    @Autowired
    StorageService storageService;

    @Autowired
    UserMapper userMapper;

    @Autowired
    FavoriteUserRepository favoriteUserRepository;

    @Autowired
    NotificationService notificationService;

    @Autowired
    UserOneSignalService userOneSignalService;

    @Autowired
    OneSignalService oneSignalService;

    @Autowired
    MessageSource messageSource;

    @Autowired(required=false)
    SuggestSearchService suggestSearchService;

    @Override
    public SellOfferDTO save(SellOfferDTO sellOfferDTO) {
        log.debug("Request to save SellOffer : {}", sellOfferDTO);

        if (sellOfferDTO.getId() != null) {
            throw new BadRequestAlertException(RequestUtil.messageTranslate("common.entity_already_exist"));
        }

        if( sellOfferDTO.getAmount()!=null ){
            if(sellOfferDTO.getAmount()<1 || sellOfferDTO.getAmount()>999999999999D){
                throw new BadRequestAlertException("Invalid amount");
            }
        }

        UserPrincipal currentUser = SecurityUtils.getCurrentUser().orElseThrow(() -> new AccountResourceException("Current user login not found"));
        sellOfferDTO.setUser(userMapper.toCurrentUserPrincipal(currentUser));
        sellOfferDTO.setBlockedByReported(Boolean.FALSE);
        sellOfferDTO.setDateCreated(Instant.now());
        SellOffer sellOffer = sellOfferMapper.toEntity(sellOfferDTO);
        sellOffer = sellOfferRepository.save(sellOffer);

        SellOfferDTO result = sellOfferMapper.toDto(sellOffer);

        // Save all images
        for (OfferImagesDTO offerImagesDTO : sellOfferDTO.getOfferImages()) {
            offerImagesDTO.setOffer(result);
            offerImagesDTO.setDateCreated(Instant.now());
            offerImagesService.save(offerImagesDTO);
        }

        // Create Notification and send push notification for favorite users
        createNotificationsForFavoriteUsers();

        // Save ElasticSearch
        if( elasticSearchAvailable.equals("true") ){
            SuggestSearch suggestSearch = new SuggestSearch();
            suggestSearch.setId(sellOffer.getId().toString());
            suggestSearch.setName(sellOffer.getTitle());
            suggestSearch.setDescription(sellOffer.getDescription());
            suggestSearch.setModule(ModuleSuggestion.offer.toString());
            suggestSearchService.save(suggestSearch);
        }

        return result;
    }

    @Override
    public Page<SellOfferDTO> findByCriteria(SellOfferFilter sellOfferFilter, Pageable pageable) {
        return sellOfferRepository.findAll(createSpecification(sellOfferFilter), pageable).map(sellOfferMapper::toDto);
    }

    @Override
    public SellOfferDTO updateSellOffer(SellOfferDTO sellOfferDTO,  Long id) {
        log.debug("Request to update SellOffer : {}", sellOfferDTO);
        if (sellOfferDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id null");
        }
        if (!Objects.equals(id, sellOfferDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID id invalid");
        }

        SellOffer sellOffer = sellOfferRepository.findById(id)
                .orElseThrow(() -> new BadRequestAlertException("Entity not found with id"));

        Long useId = SecurityUtils
                .getIdByCurrentUser();
        if (!Objects.equals(useId, sellOffer.getUser().getId())) {
            throw new UnauthorizedException("Unauthorized action");
        }

        sellOffer = sellOfferMapper.toEntity(sellOfferDTO);
        SellOffer sellOfferUpdate = sellOfferRepository.save(sellOffer);
        SellOfferDTO newSellOfferDTO = sellOfferMapper.toDto(sellOfferUpdate);


        // Any modif for images
        if( sellOfferDTO.getOfferImages().size()> 0 ){

            // Delete folder for images
            String pathAddProduct = storageService.getBaseStorageProductImages() + id;
            if (storageService.existPath(pathAddProduct)) {
                Path rootLocation = Paths.get(pathAddProduct);
                storageService.deleteFiles(rootLocation);
            }

            // Delete old images
            offerImagesRepository.deleteImagesByOfferId(newSellOfferDTO.getId());
        }

        // Update images for offer
        for (OfferImagesDTO offerImagesDTO : sellOfferDTO.getOfferImages()) {
            offerImagesDTO.setOffer(newSellOfferDTO);
            offerImagesService.save(offerImagesDTO);
        }

        return newSellOfferDTO;
    }

    private Specification<SellOffer> createSpecification(SellOfferFilter sellOfferFilter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            query.orderBy(criteriaBuilder.desc(root.get("id")));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }


    @Async
    public void createNotificationsForFavoriteUsers(){
        List<FavoriteUser> favoriteUserList = favoriteUserRepository.findYourFavoriteUsers();
        for ( FavoriteUser favoriteUser: favoriteUserList ) {

            Locale locale = Locale.forLanguageTag(!favoriteUser.getCurrentUser().getLangKey().equals("") ? favoriteUser.getCurrentUser().getLangKey() : "fr");
            String messageCommentOffer = CommonUtil.getFullNameUser(userMapper.toDto(favoriteUser.getFavoriteUser()))+" "+messageSource.getMessage("offer.message_favorite_user_add_new_offer", null, locale);

            // Create notification
            NotificationDTO notificationDTO = new NotificationDTO();
            notificationDTO.setDateCreated(Instant.now());
            notificationDTO.setContent(messageCommentOffer);
            notificationDTO.setModule(ModuleNotification.OfferNotification.name());
            notificationDTO.setIsRead(Boolean.FALSE);
            notificationDTO.setUser(userMapper.toDto(favoriteUser.getCurrentUser()));
            notificationService.save(notificationDTO);

            // Send Push Notif
            List<UserOneSignal> listUserOneSignals = userOneSignalService.findOneSignalByUser(favoriteUser.getCurrentUser());
            if(listUserOneSignals.size()>0){
                String result = listUserOneSignals.stream().map(UserOneSignal::getIdOneSignal)
                        .collect(Collectors.joining(","));
                oneSignalService.sendPushNotifToUser(result, messageCommentOffer);
            }
        }
    }
}
