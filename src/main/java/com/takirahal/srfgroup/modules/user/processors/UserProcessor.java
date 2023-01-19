package com.takirahal.srfgroup.modules.user.processors;

import com.takirahal.srfgroup.modules.user.entities.User;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

@Component
public class UserProcessor implements ItemProcessor<User, User> {
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy-HH:mm");
    @Override
    public User process(User address) throws Exception {
        // bankTransaction.setTransactionDate(simpleDateFormat.parse(bankTransaction.getStrTransactionDate()));
        return address;
    }
}
