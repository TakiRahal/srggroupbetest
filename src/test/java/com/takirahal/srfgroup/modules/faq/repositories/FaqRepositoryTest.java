package com.takirahal.srfgroup.modules.faq.repositories;

import com.takirahal.srfgroup.modules.faq.entities.Faq;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@ActiveProfiles("test")
class FaqRepositoryTest {

    @Autowired
    FaqRepository faqRepository;

    @Test
    void findByResponseAr() {

        // Given
        String response = "test";
        Faq faq = new Faq();
        faq.setQuestionAr("Qar");
        faq.setResponseAr(response);
        faq.setQuestionFr("Qfr");
        faq.setResponseFr("Rfr");
        faq.setQuestionEn("Qen");
        faq.setResponseEn("Ren");
        faqRepository.save(faq);

        // When
        Optional<Faq> result = faqRepository.findByResponseAr(response);

        // Then
        assertTrue(result.isPresent());
    }

    // Delete all data table after each test (clean state)
    @AfterEach
    void tearDown() {
        faqRepository.deleteAll();
    }
}
