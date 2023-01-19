package com.takirahal.srfgroup.modules.address.processors;

import com.takirahal.srfgroup.modules.address.entities.Address;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

@Component
public class AddressTransactionItemProcessor implements ItemProcessor<Address, Address> {
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy-HH:mm");
    @Override
    public Address process(Address address) throws Exception {
        // bankTransaction.setTransactionDate(simpleDateFormat.parse(bankTransaction.getStrTransactionDate()));
        return address;
    }
}
