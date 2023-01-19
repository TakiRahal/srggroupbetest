package com.takirahal.srfgroup.modules.faq.services.impl;

import com.takirahal.srfgroup.modules.faq.dto.FaqDTO;
import com.takirahal.srfgroup.modules.faq.dto.filter.FaqFilter;
import com.takirahal.srfgroup.modules.faq.entities.Faq;
import com.takirahal.srfgroup.modules.faq.mapper.FaqMapper;
import com.takirahal.srfgroup.modules.faq.repositories.FaqRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FaqServiceImplTest {

    @Mock
    FaqRepository faqRepositoryMock;

    @InjectMocks
    private FaqServiceImpl faqServiceImpl;

    @Spy
    private FaqMapper faqMapper = Mappers.getMapper(FaqMapper.class);

    @Before(value = "")
    public void init() {
        ReflectionTestUtils.setField(faqMapper , "faqServiceImpl", faqServiceImpl);
    }

    @BeforeEach
    void setUp() {
        // autoCloseable = MockitoAnnotations.openMocks(this); // this is needed for inititalizytion of mocks, if you use @Mock
    }

    @AfterEach
    void tearDown() throws Exception {
        // autoCloseable.close();
    }

    @Test
    @DisplayName("when save Faq, then FaqDTO are retrieved")
    void save() {
        //Given
        FaqDTO faqDTO = FaqDTO.builder().questionAr("Qar").responseAr("Rar").questionFr("Qfr").responseFr("Rfr").questionEn("Qen").responseEn("Ren").build();
        Faq faq = Faq.builder().questionAr("Qar").responseAr("Rar").questionFr("Qfr").responseFr("Rfr").questionEn("Qen").responseEn("Ren").build();

        //When
        when(faqRepositoryMock.save(any(Faq.class))).thenReturn(faq);
        FaqDTO faqDTOSave = faqServiceImpl.save(faqDTO);

        //Then
        assertNotNull(faqDTOSave);
        assertEquals(faqDTOSave.getId(), faqDTO.getId());
    }

    @Test
    @DisplayName("when list Faq by criteria, then Faqs are retrieved")
    void findByCriteria() {

        // Given
        Faq faq = new Faq();
        faq.setId(1L);
        faq.setQuestionAr("Qar");
        faq.setResponseAr("Rar");
        faq.setQuestionFr("Qfr");
        faq.setResponseFr("Rfr");
        faq.setQuestionEn("Qen");
        faq.setResponseEn("Ren");
        List<Faq> faqs = new ArrayList<>();
        faqs.add(faq);
        Pageable pageable = PageRequest.of(0, 1);
        FaqFilter criteria = new FaqFilter();
        Page<Faq> faqPage = new PageImpl<>(faqs, pageable, faqs.size());

        FaqDTO faqDTO = new FaqDTO();
        faqDTO.setId(1L);
        faqDTO.setQuestionAr("Qar");
        faqDTO.setResponseAr("Rar");
        faqDTO.setQuestionFr("Qfr");
        faqDTO.setResponseFr("Rfr");
        faqDTO.setQuestionEn("Qen");
        faqDTO.setResponseEn("Ren");
        List<FaqDTO> faqDtoList = new ArrayList<>();
        faqDtoList.add(faqDTO);
        Page<FaqDTO> faqDtoPage = new PageImpl<>(faqDtoList, pageable, faqDtoList.size());
        Mockito.when(faqMapper.toDto(faq)).thenReturn(faqDTO);
        Mockito.when(faqRepositoryMock.findAll(any(Specification.class), any(Pageable.class))).thenReturn(faqPage);

        // When
        Page<FaqDTO> faqDTOS = faqServiceImpl.findByCriteria(criteria, pageable);

        // Then
        assertNotNull(faqDTOS);
        assertFalse(faqDTOS.getContent().isEmpty());
        assertThat(faqDTOS).isEqualTo(faqDtoPage);
    }

    @Test
    @Disabled
    void createSpecification() {
    }

    @Test
    @DisplayName("when list Faq, then Faqs are retrieved")
    void findAll() {

        // Given
        Faq faq = new Faq();
        faq.setId(1L);
        faq.setQuestionAr("Qar");
        faq.setResponseAr("Rar");
        faq.setQuestionFr("Qfr");
        faq.setResponseFr("Rfr");
        faq.setQuestionEn("Qen");
        faq.setResponseEn("Ren");
        when(faqRepositoryMock.findAll()).thenReturn(Arrays.asList(faq));

        // When
        List<Faq> faqList = faqServiceImpl.findAll();

        // Then
        assertNotNull(faqList);
        assertFalse(faqList.isEmpty());
        assertEquals(faq.getId(), faqList.get(0).getId());
    }
}
