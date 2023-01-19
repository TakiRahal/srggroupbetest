package com.takirahal.srfgroup.modules.user.services.impl;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.takirahal.srfgroup.constants.AuthoritiesConstants;
import com.takirahal.srfgroup.constants.SrfGroupConstants;
import com.takirahal.srfgroup.enums.SourceConnectedDevice;
import com.takirahal.srfgroup.exceptions.UnauthorizedException;
import com.takirahal.srfgroup.modules.address.mapper.AddressMapper;
import com.takirahal.srfgroup.modules.favoriteuser.services.FavoriteUserService;
import com.takirahal.srfgroup.modules.notification.dto.NotificationDTO;
import com.takirahal.srfgroup.modules.notification.entities.Notification;
import com.takirahal.srfgroup.modules.notification.enums.ModuleNotification;
import com.takirahal.srfgroup.modules.notification.repositories.NotificationRepository;
import com.takirahal.srfgroup.modules.notification.services.NotificationService;
import com.takirahal.srfgroup.modules.permission.enums.EPermission;
import com.takirahal.srfgroup.modules.user.dto.*;
import com.takirahal.srfgroup.exceptions.BadRequestAlertException;
import com.takirahal.srfgroup.modules.user.dto.filter.UserFilter;
import com.takirahal.srfgroup.modules.user.entities.RefreshToken;
import com.takirahal.srfgroup.modules.user.entities.UserOneSignal;
import com.takirahal.srfgroup.modules.user.enums.BlockedUser;
import com.takirahal.srfgroup.modules.user.enums.EAuthority;
import com.takirahal.srfgroup.modules.user.exceptioins.*;
import com.takirahal.srfgroup.modules.user.mapper.AuthorityMapper;
import com.takirahal.srfgroup.modules.user.services.AuthorityService;
import com.takirahal.srfgroup.modules.user.services.RefreshTokenService;
import com.takirahal.srfgroup.modules.user.services.UserOneSignalService;
import com.takirahal.srfgroup.modules.websocket.models.ConnectedUser;
import com.takirahal.srfgroup.security.CustomUserDetailsService;
import com.takirahal.srfgroup.security.JwtTokenProvider;
import com.takirahal.srfgroup.security.UserPrincipal;
import com.takirahal.srfgroup.services.impl.MailService;
import com.takirahal.srfgroup.services.impl.ResizeImage;
import com.takirahal.srfgroup.services.impl.StorageService;
import com.takirahal.srfgroup.modules.user.entities.Authority;
import com.takirahal.srfgroup.modules.user.entities.User;
import com.takirahal.srfgroup.exceptions.ResouorceNotFoundException;
import com.takirahal.srfgroup.modules.user.mapper.UserMapper;
import com.takirahal.srfgroup.modules.user.repositories.AuthorityRepository;
import com.takirahal.srfgroup.modules.user.repositories.UserRepository;
import com.takirahal.srfgroup.modules.user.services.UserService;
import com.takirahal.srfgroup.utils.RandomUtil;
import com.takirahal.srfgroup.utils.RequestUtil;
import com.takirahal.srfgroup.utils.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.MessageSource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.criteria.Predicate;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.time.Instant;
import java.util.*;

import static com.takirahal.srfgroup.utils.SecurityUtils.protectedAdminAndSuperAdmin;
import static com.takirahal.srfgroup.utils.SecurityUtils.protectedSuperAdmin;


@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthorityRepository authorityRepository;

    @Autowired
    MailService mailService;

    @Autowired
    UserMapper userMapper;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider tokenProvider;

    @Autowired
    StorageService storageService;

    @Autowired
    ResizeImage resizeImage;

    @Autowired
    AddressMapper addressMapper;

    @Value("${dynamicsvariables.googleClientId}")
    private String googleClientId;

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Autowired
    UserOneSignalService userOneSignalService;

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    MessageSource messageSource;

    @Autowired
    NotificationService notificationService;

    @Autowired
    FavoriteUserService favoriteUserService;

    @Autowired
    AuthorityMapper authorityMapper;

    @Autowired
    AuthorityService authorityService;

    @Autowired
    RefreshTokenService refreshTokenService;

    @Override
    public User registerUser(RegisterDTO registerDTO) {
        log.debug("Request to register new user : {}", registerDTO.getEmail());
        if (isPasswordLengthInvalid(registerDTO.getPassword())) {
            throw new InvalidPasswordException("Incorrect password");
        }
        userRepository
                .findOneByEmailIgnoreCase(registerDTO.getEmail())
                .ifPresent(
                        existingUser -> {
                            throw new EmailAlreadyUsedException("signup.label_email_already_used");
                        }
                );

        User newUser = new User();
        newUser.setEmail(registerDTO.getEmail());
        newUser.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        newUser.setFirstName("");
        newUser.setLastName("");
        newUser.setUsername(registerDTO.getEmail());
        newUser.setActivatedAccount(false);
        newUser.setActivationKey(RandomUtil.generateActivationKey(20));
        newUser.setSourceConnectedDevice(RequestUtil.getHeaderAttribute(SrfGroupConstants.SOURCE_CONNECTED_DEVICE));
        newUser.setRegisterDate(Instant.now());
        newUser.setLangKey(RequestUtil.getHeaderAttribute(SrfGroupConstants.LANG_KEY));

        Set<Authority> authorities = new HashSet<>();
        authorityRepository.findByName(EAuthority.ROLE_USER).ifPresent(authorities::add);
        newUser.setAuthorities(authorities);
        User user = userRepository.save(newUser);

        // Register one signal id
        if(registerDTO.getIdOneSignal()!=null){
            UserOneSignalDTO userOneSignalDTO = new UserOneSignalDTO();
            userOneSignalDTO.setIdOneSignal(registerDTO.getIdOneSignal());
            userOneSignalDTO.setUser(userMapper.toDtoIdEmail(user));
            userOneSignalService.save(userOneSignalDTO);
        }

        // Add all notifications
        addAllNotification(user);


        if( RequestUtil.getHeaderAttribute(SrfGroupConstants.SOURCE_CONNECTED_DEVICE).equals(SourceConnectedDevice.WEB_BROWSER.toString()) ||
            RequestUtil.getHeaderAttribute(SrfGroupConstants.SOURCE_CONNECTED_DEVICE).equals(SourceConnectedDevice.MOBILE_BROWSER.toString())){

            // Send Activation Email with link activation
            mailService.sendActivationEmail(user, true);
        }
        else{
            // Send Activation Email with only code activation
            mailService.sendActivationEmail(user, false);
        }

        return newUser;
    }

    @Override
    public Optional<User> activateRegistration(String key) {
        log.debug("Request to activation account by key : {}", key);
        return userRepository
                .findOneByActivationKey(key)
                .map(
                        user -> {
                            // activate given user for the registration key.
                            user.setActivatedAccount(true);
                            user.setActivationKey(null);
                            log.debug("Activated user: {}", user);
                            return user;
                        }
                );
    }

    @Override
    public UserDTO findById(Long id) {
        log.debug("Request to find user by id : {}", id);
        return userRepository.findById(id)
                .map(user -> userMapper.toDtoPublicUser(user))
                .orElseThrow(() -> new ResouorceNotFoundException("Not found user with id "+id));
    }


    @Override
    public UserDTO findByIdForAdmin(Long id) {
        log.debug("Request to find user by id : {}", id);
        return userRepository.findById(id)
                .map(user -> userMapper.toDtoListAdmin(user))
                .orElseThrow(() -> new ResouorceNotFoundException("Not found user with id "+id));
    }

    @Override
    public UserDTO getCurrentUser() {
        log.debug("Request to get current user : {}");
        return SecurityUtils.getEmailByCurrentUser()
                .flatMap(userRepository::findOneByEmailIgnoreCase)
                .map(userMapper::toCurrentUser)
                .orElseThrow(() -> new AccountResourceException("Current user login not found"));
    }

    @Override
    public JwtResponseVM signinClient(LoginDTO loginDTO) {
        try {

            log.debug("Request to signin as client with email: {}", loginDTO.getEmail());
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDTO.getEmail(),
                            loginDTO.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = tokenProvider.generateToken(authentication);

            Long currentUserId = SecurityUtils.getIdByCurrentUser();
            RefreshTokenDTO refreshToken = refreshTokenService.createRefreshToken(currentUserId);

            JwtResponseVM jwtResponseVM = JwtResponseVM.builder()
                    .token(jwt)
                    .refreshToken(refreshToken.getToken())
                    .build();

            if(!loginDTO.getIdOneSignal().equals("")){

                // Register one signal id if not exist already
                saveOneSignal(loginDTO.getIdOneSignal(), RequestUtil.getHeaderAttribute(SrfGroupConstants.SOURCE_CONNECTED_DEVICE));
            }
//            else{
//
//                // Create notif for subscribe in PushNotif
//                UserPrincipal currentUser = SecurityUtils.getCurrentUser().orElseThrow(() -> new AccountResourceException("Current user login not found"));
//                Locale locale = Locale.forLanguageTag(!currentUser.getLangKey().equals("") ? currentUser.getLangKey() : "fr");
//                String messageSubscPush = messageSource.getMessage("signin.message.subscribe_push_notif", null, locale);
//
//                creatNotificationByAdmin(userMapper.currentUserToEntity(currentUser), messageSubscPush);
//            }
            return jwtResponseVM;
        }
        catch(BadCredentialsException e){
            log.error("Exception for request to signin client: {}", e.getMessage());
            throw new InvalidPasswordException(RequestUtil.messageTranslate("signin.bad_credentials"));
        }
    }

    @Override
    public String signInAdmin(LoginDTO loginDTO) {
        log.debug("Request to signin as admin with email: {}", loginDTO.getEmail());
        Optional<User> existingUser = userRepository.findOneByEmailIgnoreCase(loginDTO.getEmail());
        if( !existingUser.isPresent() ){
            throw new AccountResourceException("Not found user with email");
        }

        if( !SecurityUtils.hasUserThisAuthority(existingUser.get().getAuthorities(), AuthoritiesConstants.ADMIN) &&
            !SecurityUtils.hasUserThisAuthority(existingUser.get().getAuthorities(), AuthoritiesConstants.SUPER_ADMIN)){
            throw new BadRequestAlertException("Not admin");
        }

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDTO.getEmail(),
                            loginDTO.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            return tokenProvider.generateToken(authentication);
        }
        catch(BadCredentialsException e){
            log.error("Exception for request to signin as admin: {}", e.getMessage());
            throw new InvalidPasswordException("Bad Credentials");
        }
    }

    @Override
    public UserDTO updateAvatar(MultipartFile file) {
        log.debug("Request to update avatar: {}");
        Long currentUserId = SecurityUtils
                .getIdByCurrentUser();


        Optional<User> existingUser = userRepository.findById(currentUserId);
        if(!existingUser.isPresent()){
            throw new AccountResourceException("Current user login not found");
        }

        existingUser.get().setImageUrl(file.getOriginalFilename());
        userRepository.save(existingUser.get());

        String pathAvatarUser = storageService.getBaseStorageUserImages() + currentUserId;

        Path rootLocation = Paths.get(pathAvatarUser);
        if (storageService.existPath(pathAvatarUser)) { // Already exixit path
            storageService.store(file, rootLocation);
        } else { // Create  new path
            storageService.init(pathAvatarUser);
            storageService.store(file, rootLocation);
        }

        // Rotate Exif
        resizeImage.rotateImages(storageService.getBaseStorageUserImages() + currentUserId + "/" + file.getOriginalFilename());

        // Resize
        resizeImage.resizeAvatar(storageService.getBaseStorageUserImages() + currentUserId + "/" + file.getOriginalFilename());

        return userMapper.toDto(existingUser.get());
    }

    @Override
    public Resource getAvatar(Long id, String filename) {
        Path rootLocation = Paths.get(storageService.getBaseStorageUserImages() + id);
        return storageService.loadFile(filename, rootLocation);
    }

    @Override
    public UserDTO updateCurrentUser(UserDTO user) {
        log.debug("Request to update infos: {}", user);
        Long userId = SecurityUtils.getIdByCurrentUser();

        User currentUser = userRepository.findById(userId)
                .orElseThrow(() -> new AccountResourceException("Current user login not found"));

        currentUser.setFirstName(user.getFirstName());
        currentUser.setLastName(user.getLastName());
        currentUser.setPhone(user.getPhone());
        currentUser.setAddress(addressMapper.toEntity(user.getAddress()));
        currentUser.setLangKey(user.getLangKey());
        currentUser.setLinkProfileFacebook(user.getLinkProfileFacebook());

        User newUser = userRepository.save(currentUser);

        return userMapper.toCurrentUser(newUser);
    }

    @Override
    public Boolean updatePasswordCurrentUser(UpdatePasswordDTO updatePasswordDTO) {
        log.debug("Request to update password: {}", updatePasswordDTO);
        if (isPasswordLengthInvalid(updatePasswordDTO.getNewPassword())) {
            throw new InvalidPasswordException("Inavalid length password");
        }

        SecurityUtils
                .getEmailCurrentUser()
                .flatMap(userRepository::findOneByEmailIgnoreCase)
                .ifPresent(user -> {
                    String currentEncryptedPassword = user.getPassword();
                    if (!passwordEncoder.matches(updatePasswordDTO.getCurrentPassword(), currentEncryptedPassword)) {
                        throw new InvalidPasswordException("account.invalid_old_password");
                    }
                    String encryptedPassword = passwordEncoder.encode(updatePasswordDTO.getNewPassword());
                    user.setPassword(encryptedPassword);
                    log.debug("Changed password for User: {}", user);
                });

        /*
        Long userId = SecurityUtils.getIdByCurrentUser()
                .orElseThrow(() -> new AccountResourceException("Current user login not found"));

        User currentUser = userRepository.findById(userId)
                .orElseThrow(() -> new AccountResourceException("Current user login not found"));

        String currentEncryptedPassword = currentUser.getPassword();

        if (!passwordEncoder.matches(updatePasswordDTO.getCurrentPassword(), currentEncryptedPassword)) {
            throw new InvalidPasswordException("Invalid old password");
        }

        String encryptedPassword = passwordEncoder.encode(updatePasswordDTO.getNewPassword());
        currentUser.setPassword(encryptedPassword);
        userRepository.save(currentUser);
        */
        return Boolean.TRUE;
    }

    @Override
    public String signinGooglePlus(GooglePlusVM googlePlusVM) throws IOException{
        log.debug("Request to Signin GooglePlus: {}", googlePlusVM);
        final NetHttpTransport transport = new NetHttpTransport();
        final JacksonFactory jacksonFactory = JacksonFactory.getDefaultInstance();
        GoogleIdTokenVerifier.Builder verifier = new GoogleIdTokenVerifier.Builder(transport, jacksonFactory)
                .setAudience(Collections.singletonList(googleClientId));
        final GoogleIdToken googleIdToken = GoogleIdToken.parse(verifier.getJsonFactory(), googlePlusVM.getTokenId());
        final GoogleIdToken.Payload payload = googleIdToken.getPayload();

        if( payload.getEmail().equals("srfgroup.contact@gmail.com")){
            throw new UnauthorizedException("User super admin");
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(payload.getEmail());
        userDTO.setEmail(payload.getEmail());
        userDTO.setFirstName(googlePlusVM.getProfileObj().getFamilyName());
        userDTO.setLastName(googlePlusVM.getProfileObj().getGivenName());
        userDTO.setImageUrl(googlePlusVM.getProfileObj().getImageUrl());
        userDTO.setSourceConnectedDevice(googlePlusVM.getSourceConnectedDevice());
        Set<AuthorityDTO> authorities = new HashSet<>();
        AuthorityDTO authority = new AuthorityDTO();
        authority.setId(3);
        authority.setName(EAuthority.ROLE_USER);
        authorities.add(authority);
        userDTO.setAuthorities(authorities);

        // Update user
        Optional<User> userExist = updateUserSocialMedia(userDTO, payload.getEmail());

        // Save new User
        User user = userMapper.toEntity(userDTO);
        user.setPassword(RandomUtil.generateActivationKey(20));
        user.setActivatedAccount(true);
        user.setRegisterDate(Instant.now());
        user.setLangKey(RequestUtil.getHeaderAttribute(SrfGroupConstants.LANG_KEY));
        User newUser = userRepository.save(user);

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(userMapper.toDto(newUser).getEmail());
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                "",
                userDetails.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        String jwt = tokenProvider.createToken(authenticationToken, true);


        if(!googlePlusVM.getIdOneSignal().equals("")){

            // Register one signal id if not exist already
            saveOneSignal(googlePlusVM.getIdOneSignal(), RequestUtil.getHeaderAttribute(SrfGroupConstants.SOURCE_CONNECTED_DEVICE));
        }
//        else{
//
//            // Create notif for subscribe in PushNotif
//            UserPrincipal currentUser = SecurityUtils.getCurrentUser().orElseThrow(() -> new AccountResourceException("Current user login not found"));
//            Locale locale = Locale.forLanguageTag(!currentUser.getLangKey().equals("") ? currentUser.getLangKey() : "fr");
//            String messageSubscPush = messageSource.getMessage("signin.message.subscribe_push_notif", null, locale);
//
//            creatNotificationByAdmin(userMapper.currentUserToEntity(currentUser), messageSubscPush);
//        }

        // Add Welcome notification first time
        if (!userExist.isPresent()) {

            // Add all notifications
            addAllNotification(user);
        }

        return jwt;
    }

    @Override
    public String signinFacebook(FacebookVM facebookVM) {
        log.debug("Request to Signin Facebook: {}", facebookVM);

        if(facebookVM.getEmail()==null){
            throw new ResouorceNotFoundException(RequestUtil.messageTranslate("signin.email_facebook_undefined"));
        }

        Facebook facebook = new FacebookTemplate(facebookVM.getAccessToken());
        final String[] fields = { "email", "picture" };
        FacebookVM userFacebook = facebook.fetchObject("me", FacebookVM.class, fields);

        if( facebookVM.getEmail().equals("srfgroup.contact@gmail.com")){
            throw new UnauthorizedException("User super admin");
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(userFacebook.getEmail());
        userDTO.setEmail(facebookVM.getEmail());
        userDTO.setFirstName(facebookVM.getName());
        userDTO.setLastName("");
        userDTO.setImageUrl(facebookVM.getPicture().getData().getUrl());
        userDTO.setSourceConnectedDevice(facebookVM.getSourceConnectedDevice());
        Set<AuthorityDTO> authorities = new HashSet<>();
        AuthorityDTO authority = new AuthorityDTO();
        authority.setId(3);
        authority.setName(EAuthority.ROLE_USER);
        authorities.add(authority);
        userDTO.setAuthorities(authorities);

        // Update user
        Optional<User> userExist = updateUserSocialMedia(userDTO, userFacebook.getEmail());

        // Save new User
        User user = userMapper.toEntity(userDTO);
        user.setPassword(RandomUtil.generateActivationKey(20));
        user.setActivatedAccount(true);
        user.setRegisterDate(Instant.now());
        user.setLangKey(RequestUtil.getHeaderAttribute(SrfGroupConstants.LANG_KEY));
        User newUser = userRepository.save(user);

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(userMapper.toDto(newUser).getEmail());
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                "",
                userDetails.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        String jwt = tokenProvider.createToken(authenticationToken, true);


        if(!facebookVM.getIdOneSignal().equals("")){

            // Register one signal id if not exist already
            saveOneSignal(facebookVM.getIdOneSignal(), RequestUtil.getHeaderAttribute(SrfGroupConstants.SOURCE_CONNECTED_DEVICE));
        }
//        else{
//
//            // Create notif for subscribe in PushNotif
//            UserPrincipal currentUser = SecurityUtils.getCurrentUser().orElseThrow(() -> new AccountResourceException("Current user login not found"));
//            Locale locale = Locale.forLanguageTag(!currentUser.getLangKey().equals("") ? currentUser.getLangKey() : "fr");
//            String messageSubscPush = messageSource.getMessage("signin.message.subscribe_push_notif", null, locale);
//
//            creatNotificationByAdmin(userMapper.currentUserToEntity(currentUser), messageSubscPush);
//        }

        // Add Welcome notification first time register
        if (!userExist.isPresent()) {

            // Add all notifications
            addAllNotification(user);
        }

        return jwt;
    }

    @Override
    public Boolean requestPasswordReset(String mail) {
        log.debug("Request to init reset password: {}", mail);
        User user = userRepository.findOneByEmailIgnoreCase(mail)
                .orElseThrow(() -> new ResouorceNotFoundException(RequestUtil.messageTranslate("forgot_password_init.user_not_found_by_email")));

        if(!user.isActivatedAccount()){
            throw new UserNotActivatedException(RequestUtil.messageTranslate("signin.account_not_activated"));
        }

        user.setResetKey(RandomUtil.generateActivationKey(20));
        User newUser = userRepository.save(user);

        if( RequestUtil.getHeaderAttribute(SrfGroupConstants.SOURCE_CONNECTED_DEVICE).equals(SourceConnectedDevice.WEB_BROWSER.toString()) ||
                RequestUtil.getHeaderAttribute(SrfGroupConstants.SOURCE_CONNECTED_DEVICE).equals(SourceConnectedDevice.MOBILE_BROWSER.toString())){

            // Send Activation Email with link activation
            mailService.sendPasswordResetMail(newUser, false);
        }
        else{
            // Send Activation Email with only code activation
            mailService.sendPasswordResetMail(newUser, false);
        }

        return true;
    }

    @Override
    public void completePasswordReset(String password, String key) {
        log.debug("Request to finish reset password: {}", key);

        if (isPasswordLengthInvalid(password)) {
            throw new InvalidPasswordException("Incorrect password");
        }

        Optional<User> newUser = userRepository
                .findOneByResetKey(key)
                .map(user -> {
                    user.setPassword(passwordEncoder.encode(password));
                    user.setResetKey(null);
                    return user;
                });

        if (!newUser.isPresent()) {
            throw new AccountResourceException(RequestUtil.messageTranslate("forgot_password_init.message_invalid_key"));
        }
    }

    @Override
    public Page<UserDTO> findByCriteria(UserFilter userFilter, Pageable pageable) {
        return userRepository.findAll(createSpecification(userFilter), pageable).map(userMapper::toDtoListAdmin);
    }

    @Override
    public void blockedUserByAdmin(Long id, String blockUnblock) {
        log.debug("Request to blocked user by admin : {} - {}", id, blockUnblock);

        // Check Permission
        authorityService.checkForPermissions(EPermission.BLOCKED_USER);

        Optional<User> user = userRepository.findById(id);
        if(!user.isPresent()){
            log.error("Exception to block user: {}");
            throw new ResouorceNotFoundException("User not found");
        }

        if(blockUnblock.equals("true")){
            user.get().setBlocked(BlockedUser.BlockedByAdmin.toString());
        }
        else{
            user.get().setBlocked(null);
        }
        userRepository.save(user.get());
    }

    /*
    @Override
    public void addRemoveAdmin(Long id, String addRemove) {
        log.debug("Request to add/remove admin : {} - {}", id, addRemove);
        Optional<User> user = userRepository.findById(id);
        if(!user.isPresent()){
            throw new ResouorceNotFoundException("User not found");
        }

        // Protected Super Admin
        protectedSuperAdmin(user.get());

        /
        if(addRemove.equals("true")){
            Set<Authority> authorities = user.get().getAuthorities();

            Authority authority = new Authority();
            authority.setName(AuthoritiesConstants.ADMIN);
            authorities.add(authority);

            user.get().setAuthorities(authorities);

            createAddRemoveAdminNotification(user.get(), true);
        }
        else{

            Set<Authority> authorities = new HashSet<>();
            Authority authority = new Authority();
            authority.setName(EAuthority.ROLE_USER);
            authorities.add(authority);

            user.get().setAuthorities(authorities);

            createAddRemoveAdminNotification(user.get(), false);
        }
        /
        userRepository.save(user.get());
    }
    */

    @Override
    public String signinGooglePlusOneTap(GooglePlusVM googlePlusVM) throws IOException{
        log.debug("Request to Signin GooglePlus OneTap: {}", googlePlusVM);
        final NetHttpTransport transport = new NetHttpTransport();
        final JacksonFactory jacksonFactory = JacksonFactory.getDefaultInstance();
        GoogleIdTokenVerifier.Builder verifier = new GoogleIdTokenVerifier.Builder(transport, jacksonFactory)
                .setAudience(Collections.singletonList(googleClientId));
        final GoogleIdToken googleIdToken = GoogleIdToken.parse(verifier.getJsonFactory(), googlePlusVM.getTokenId());
        final GoogleIdToken.Payload payload = googleIdToken.getPayload();

        if( payload.getEmail().equals("srfgroup.contact@gmail.com")){
            throw new UnauthorizedException("User super admin");
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(googlePlusVM.getProfileObj().getEmail());
        userDTO.setEmail(googlePlusVM.getProfileObj().getEmail());
        userDTO.setFirstName(googlePlusVM.getProfileObj().getFamilyName());
        userDTO.setLastName(googlePlusVM.getProfileObj().getGivenName());
        userDTO.setImageUrl(googlePlusVM.getProfileObj().getImageUrl());
        userDTO.setSourceConnectedDevice(googlePlusVM.getSourceConnectedDevice());
        Set<AuthorityDTO> authorities = new HashSet<>();
        AuthorityDTO authority = new AuthorityDTO();
        authority.setId(3);
        authority.setName(EAuthority.ROLE_USER);
        authorities.add(authority);
        userDTO.setAuthorities(authorities);

        // Update user
        Optional<User> userExist = updateUserSocialMedia(userDTO, googlePlusVM.getProfileObj().getEmail());

        // Save new User
        User user = userMapper.toEntity(userDTO);
        user.setPassword(RandomUtil.generateActivationKey(20));
        user.setActivatedAccount(true);
        user.setRegisterDate(Instant.now());
        user.setLangKey(RequestUtil.getHeaderAttribute(SrfGroupConstants.SOURCE_CONNECTED_DEVICE));
        User newUser = userRepository.save(user);

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(userMapper.toDto(newUser).getEmail());
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                "",
                userDetails.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        String jwt = tokenProvider.createToken(authenticationToken, true);


        if(!googlePlusVM.getIdOneSignal().equals("")){

            // Register one signal id if not exist already
            saveOneSignal(googlePlusVM.getIdOneSignal(), RequestUtil.getHeaderAttribute(SrfGroupConstants.SOURCE_CONNECTED_DEVICE));
        }
//        else{
//
//            // Create notif for subscribe in PushNotif
//            UserPrincipal currentUser = SecurityUtils.getCurrentUser().orElseThrow(() -> new AccountResourceException("Current user login not found"));
//            Locale locale = Locale.forLanguageTag(!currentUser.getLangKey().equals("") ? currentUser.getLangKey() : "fr");
//            String messageSubscPush = messageSource.getMessage("signin.message.subscribe_push_notif", null, locale);
//
//            creatNotificationByAdmin(userMapper.currentUserToEntity(currentUser), messageSubscPush);
//        }


        // Add Welcome notification first time
        if (!userExist.isPresent()) {

            // Add all notifications
            addAllNotification(user);
        }

        return jwt;
    }

    @Override
    @Cacheable(value = "profile_favorite_user", key = "#id")
    public ProfileFavoriteUserDTO findWithFavoriteUserById(Long id) {
        log.debug("Request to find user and check if favorite: {}", id);
        UserDTO userDTO = userRepository.findById(id)
                .map(user -> userMapper.toDtoPublicUser(user))
                .orElseThrow(() -> new ResouorceNotFoundException("Not found user with id "+id));

        ProfileFavoriteUserDTO profileFavoriteUserDTO = new ProfileFavoriteUserDTO();
        profileFavoriteUserDTO.setUser(userDTO);
        Optional<UserPrincipal> currentUser = SecurityUtils.getCurrentUser();
        if(!currentUser.isPresent()){
            profileFavoriteUserDTO.setMyFavoriteUser(false);
        }
        else{
            profileFavoriteUserDTO.setMyFavoriteUser(favoriteUserService.isMyFavoriteUser(currentUser.get(), userDTO));
        }
        return profileFavoriteUserDTO;
    }

    @Override
    public void updateUserAuthority(Long id, Set<Authority> authorities) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResouorceNotFoundException("Not found user with id "+id));
        user.setAuthorities(authorities);
        userRepository.save(user);
    }

    @Override
    public JwtResponseVM refreshToken(TokenRefreshRequestDTO request) {
        String requestRefreshToken = request.getRefreshToken();
        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = tokenProvider.generateTokenFromUsername(user.getUsername());
                    return JwtResponseVM.builder()
                            .token(token)
                            .refreshToken(requestRefreshToken)
                            .build();
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken+" : Invalid RefreshToken"));
    }

    private Optional<User> updateUserSocialMedia(UserDTO userDTO, String email){
        Optional<User> userExist = userRepository.findOneByEmailIgnoreCase(email);
        if (userExist.isPresent()) {
            // Update user
            userDTO.setAuthorities(authorityMapper.toDtoIdSet(userExist.get().getAuthorities()));
            userDTO.setId(userExist.get().getId());
            userDTO.setBlocked(userExist.get().getBlocked());
            userDTO.setAddress(addressMapper.toDto(userExist.get().getAddress()));
            userDTO.setPhone(userExist.get().getPhone());
            userDTO.setLinkProfileFacebook(userExist.get().getLinkProfileFacebook());
        }
        return userExist;
    }

    private Specification<User> createSpecification(UserFilter userFilter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            query.orderBy(criteriaBuilder.asc(root.get("id")));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    /**
     *
     * @param idOneSignal
     */
    private void saveOneSignal(String idOneSignal, String sourceConnectedDevice){
        if(idOneSignal!=null && !ObjectUtils.isEmpty(idOneSignal)){
            Optional<UserPrincipal> userPrincipalOptional = SecurityUtils.getCurrentUser();
            Optional<UserOneSignal> userOneSignal = userOneSignalService.findByIdOneSignalAndUser(idOneSignal,userMapper.currentUserToEntity(userPrincipalOptional.get()));
            if(!userOneSignal.isPresent()){
                UserOneSignalDTO userOneSignalDTO = new UserOneSignalDTO();
                userOneSignalDTO.setIdOneSignal(idOneSignal);
                userOneSignalDTO.setSourceConnectedDevice(sourceConnectedDevice);
                userOneSignalDTO.setRegisterDate(Instant.now());
                userOneSignalDTO.setUser(userMapper.toCurrentUserPrincipal(userPrincipalOptional.get()));
                userOneSignalService.save(userOneSignalDTO);
            }
        }
    }

    /**
     *
     * @param user
     */
    private void addAllNotification(User user){
        Locale locale = Locale.forLanguageTag(!user.getLangKey().equals("") ? user.getLangKey() : "fr");
        String messageWelcome = messageSource.getMessage("register.message_welcome", null, locale);
        String messageUpdate = messageSource.getMessage("register.message_update_infos", null, locale);

        // Add Welcome notification
        creatNotificationByAdmin(user, messageWelcome);

        // Add notif to update infos in first time
        creatNotificationByAdmin(user, messageUpdate);
    }

    private void creatNotificationByAdmin(User user, String message){
        Notification notification = new Notification();
        notification.setDateCreated(Instant.now());
        notification.setContent(message);
        notification.setModule(ModuleNotification.AdminNotification.name());
        notification.setUser(user);
        notificationRepository.save(notification);
    }

    private static boolean isPasswordLengthInvalid(String password) {
        return (
                StringUtils.isEmpty(password) ||
                        password.length() < RegisterDTO.PASSWORD_MIN_LENGTH ||
                        password.length() > RegisterDTO.PASSWORD_MAX_LENGTH
        );
    }


    /**
     *
     * @param user
     */
    private void createAddRemoveAdminNotification(User user, boolean addRemove){
        Locale locale = Locale.forLanguageTag(!user.getLangKey().equals("") ? user.getLangKey() : "fr");
        String message = messageSource.getMessage(addRemove ? "user.message_add_admin" : "user.message_remove_admin", null, locale);

        Notification notification = new Notification();
        notification.setDateCreated(Instant.now());
        notification.setContent(message);
        notification.setModule(ModuleNotification.AdminNotification.name());
        notification.setUser(user);
        notificationRepository.save(notification);
    }
}
