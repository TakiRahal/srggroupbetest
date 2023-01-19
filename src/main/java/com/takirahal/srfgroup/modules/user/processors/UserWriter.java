package com.takirahal.srfgroup.modules.user.processors;

import com.takirahal.srfgroup.modules.user.entities.User;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;


@Component
public class UserWriter implements ItemWriter<User> {

//    @Autowired
//    UserRepository userRepository;

//    @Override
//    public void write(List<? extends User> list){
//        for(int i=0; i<list.size(); i++){
//            User user = list.get(i);
//            Set<Authority> authorities = new HashSet<>();
//            Authority authority = new Authority();
//            authority.setName(EAuthority.ROLE_MODERATOR);
//            authorities.add(authority);
//            user.setAuthorities(authorities);
//            userRepository.save(user);
//        }
//        // userRepository.saveAll(list);
//    }

    @Override
    public void write(Chunk<? extends User> chunk) throws Exception {

    }
}
