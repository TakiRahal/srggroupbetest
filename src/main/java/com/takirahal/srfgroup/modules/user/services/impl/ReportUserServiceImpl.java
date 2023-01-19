package com.takirahal.srfgroup.modules.user.services.impl;

import com.takirahal.srfgroup.modules.user.entities.ReportUser;
import com.takirahal.srfgroup.modules.user.entities.User;
import com.takirahal.srfgroup.modules.user.enums.BlockedUser;
import com.takirahal.srfgroup.modules.user.exceptioins.AccountResourceException;
import com.takirahal.srfgroup.modules.user.repositories.ReportUserRepository;
import com.takirahal.srfgroup.modules.user.repositories.UserRepository;
import com.takirahal.srfgroup.modules.user.services.ReportUserService;
import com.takirahal.srfgroup.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.takirahal.srfgroup.utils.SecurityUtils.protectedAdminAndSuperAdmin;

@Service
public class ReportUserServiceImpl implements ReportUserService {

    @Value("${user.nbe_report}")
    private int maxNbeReportUser;

    @Autowired
    ReportUserRepository reportUserRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public boolean addUpdate(Long idReportedUser) {

        User userReported = userRepository.findById(idReportedUser)
                .orElseThrow(() -> new AccountResourceException("Current user login not found"));

        // Protected Admin and Super Admin: User should not Admin / Super Admin
        protectedAdminAndSuperAdmin(userReported);

        Optional<ReportUser> reportedUser = reportUserRepository.findByReportedUser(userReported);

        Long currentUserId = SecurityUtils
            .getIdByCurrentUser();

        if( !reportedUser.isPresent() ){
            ReportUser reportUser = new ReportUser();
            reportUser.setReportedUser(userReported);
            reportUser.setReportingUsers(currentUserId.toString());
            reportUserRepository.save(reportUser);
        }
        else{
            ReportUser reportUserUpdated = reportedUser.get();
            List<String> listOfUsersReporting = Arrays.asList(reportUserUpdated.getReportingUsers().split(","));
            List<Long> listOfIdUsersReporting = listOfUsersReporting.stream()
                    .map(s -> Long.parseLong(s))
                    .collect(Collectors.toList());

            if ( !listOfIdUsersReporting.contains(currentUserId) ) {
                String newListUsers = reportUserUpdated.getReportingUsers().concat(","+currentUserId);
                reportUserUpdated.setReportingUsers(newListUsers);
                reportUserRepository.save(reportUserUpdated);

                listOfIdUsersReporting.add(currentUserId);
                checkBlockedByReport(reportedUser.get().getReportedUser(), listOfIdUsersReporting.size());
            }
        }
        return true;
    }

    private void checkBlockedByReport(User user, int nbeUsersReporting) {
        if( nbeUsersReporting >= maxNbeReportUser ){
            user.setBlocked(BlockedUser.BlockedByReport.toString());
            userRepository.save(user);
        }
    }
}
