package com.takirahal.srfgroup.modules.offer.services.impl;

import com.takirahal.srfgroup.exceptions.BadRequestAlertException;
import com.takirahal.srfgroup.exceptions.UnauthorizedException;
import com.takirahal.srfgroup.modules.favoriteuser.entities.FavoriteUser;
import com.takirahal.srfgroup.modules.favoriteuser.repositories.FavoriteUserRepository;
import com.takirahal.srfgroup.modules.notification.dto.NotificationDTO;
import com.takirahal.srfgroup.modules.notification.enums.ModuleNotification;
import com.takirahal.srfgroup.modules.notification.services.NotificationService;
import com.takirahal.srfgroup.modules.offer.dto.FindOfferDTO;
import com.takirahal.srfgroup.modules.offer.dto.OfferImagesDTO;
import com.takirahal.srfgroup.modules.offer.entities.FindOffer;
import com.takirahal.srfgroup.modules.offer.dto.filter.FindOfferFilter;
import com.takirahal.srfgroup.modules.offer.mapper.FindOfferMapper;
import com.takirahal.srfgroup.modules.offer.repositories.FindOfferRepository;
import com.takirahal.srfgroup.modules.offer.repositories.OfferImagesRepository;
import com.takirahal.srfgroup.modules.offer.services.FindOfferService;
import com.takirahal.srfgroup.modules.offer.services.OfferImagesService;
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
public class FindOfferServiceImpl implements FindOfferService {

    private final Logger log = LoggerFactory.getLogger(FindOfferServiceImpl.class);

    @Value("${elasticsearch.available}")
    String elasticSearchAvailable;

    @Autowired
    FindOfferMapper findOfferMapper;

    @Autowired
    FindOfferRepository findOfferRepository;

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
    public FindOfferDTO save(FindOfferDTO findOfferDTO) {
        log.debug("Request to save FindOffer : {}", findOfferDTO);

        if (findOfferDTO.getId() != null) {
            throw new BadRequestAlertException(RequestUtil.messageTranslate("common.entity_already_exist"));
        }

        UserPrincipal currentUser = SecurityUtils.getCurrentUser().orElseThrow(() -> new AccountResourceException("Current user login not found"));
        findOfferDTO.setUser(userMapper.toCurrentUserPrincipal(currentUser));
        findOfferDTO.setBlockedByReported(Boolean.FALSE);
        findOfferDTO.setDateCreated(Instant.now());
        FindOffer findOffer = findOfferMapper.toEntity(findOfferDTO);
        findOffer = findOfferRepository.save(findOffer);

        FindOfferDTO result = findOfferMapper.toDto(findOffer);

        for (OfferImagesDTO offerImagesDTO : findOfferDTO.getOfferImages()) {
            offerImagesDTO.setOffer(result);
            offerImagesService.save(offerImagesDTO);
        }

        // Create Notification and send push notification for favorite users
        createNotificationsForFavoriteUsers();

        // Save ElasticSearch
        if( elasticSearchAvailable.equals("true") ){
            SuggestSearch suggestSearch = new SuggestSearch();
            suggestSearch.setId(findOffer.getId().toString());
            suggestSearch.setName(findOffer.getTitle());
            suggestSearch.setDescription(findOffer.getDescription());
            suggestSearch.setModule(ModuleSuggestion.offer.toString());
            suggestSearchService.save(suggestSearch);
        }

        return result;
    }

    @Override
    public Page<FindOfferDTO> findByCriteria(FindOfferFilter findOfferFilter, Pageable pageable) {
        return findOfferRepository.findAll(createSpecification(findOfferFilter), pageable).map(findOfferMapper::toDto);
    }

    @Override
    public FindOfferDTO updateFindOffer(FindOfferDTO findOfferDTO, Long id) {
        log.info("Request to update RentOffer : {}", findOfferDTO);
        if (findOfferDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id idnull");
        }
        if (!Objects.equals(id, findOfferDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID idinvalid");
        }

        FindOffer findOffer = findOfferRepository.findById(id)
                .orElseThrow(() -> new BadRequestAlertException("Entity not found with id"));

        Long useId = SecurityUtils
                .getIdByCurrentUser();

        if (!Objects.equals(useId, findOfferDTO.getUser().getId())) {
            throw new UnauthorizedException("Unauthorized action");
        }

        findOffer = findOfferMapper.toEntity(findOfferDTO);
        FindOffer findOfferUpdate = findOfferRepository.save(findOffer);
        FindOfferDTO newFindSellOfferDTO = findOfferMapper.toDto(findOfferUpdate);


        // Any modif for images
        if( findOfferDTO.getOfferImages().size()> 0 ){

            // Delete folder for images
            String pathAddProduct = storageService.getBaseStorageProductImages() + id;
            if (storageService.existPath(pathAddProduct)) {
                Path rootLocation = Paths.get(pathAddProduct);
                storageService.deleteFiles(rootLocation);
            }

            // Delete old images
            offerImagesRepository.deleteImagesByOfferId(newFindSellOfferDTO.getId());
        }

        // Update images for offer
        for (OfferImagesDTO offerImagesDTO : findOfferDTO.getOfferImages()) {
            offerImagesDTO.setOffer(newFindSellOfferDTO);
            offerImagesService.save(offerImagesDTO);
        }

        return newFindSellOfferDTO;
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

    private Specification<FindOffer> createSpecification(FindOfferFilter findOfferFilter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            query.orderBy(criteriaBuilder.desc(root.get("id")));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
