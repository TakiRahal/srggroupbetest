package com.takirahal.srfgroup.modules.user.processors;

import com.takirahal.srfgroup.modules.user.entities.Authority;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;


@Component
public class AuthorityProcessor implements ItemProcessor<Authority, Authority> {
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy-HH:mm");
    @Override
    public Authority process(Authority address) throws Exception {
        // bankTransaction.setTransactionDate(simpleDateFormat.parse(bankTransaction.getStrTransactionDate()));
        return address;
    }
}
