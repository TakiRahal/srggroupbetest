package com.takirahal.srfgroup.modules.user.processors;

import com.takirahal.srfgroup.modules.user.repositories.AuthorityRepository;
import com.takirahal.srfgroup.modules.user.entities.Authority;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;


@Component
public class AuthorityWriter implements ItemWriter<Authority> {

//    @Autowired
//    AuthorityRepository authorityRepository;
//
//    @Override
//    public void write(List<? extends Authority> list) {
//         authorityRepository.saveAll(list);
//    }

    @Override
    public void write(Chunk<? extends Authority> chunk) throws Exception {

    }
}
