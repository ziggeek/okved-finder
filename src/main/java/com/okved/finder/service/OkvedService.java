package com.okved.finder.service;

import com.okved.finder.entity.Okved;
import com.okved.finder.repository.OkvedRepository;
import com.okved.finder.utils.PhoneNormalizationUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OkvedService {

    private final OkvedRepository okvedRepository;

    public List<Okved> findOkvedByPhoneNumber(String phoneNumber) {
        String normalizedPhoneNumber = PhoneNormalizationUtils.normalize(phoneNumber);
        log.info("Начало поиска ОКВЭД для номера: {}", normalizedPhoneNumber);

        String phoneDigits = extractLastSixDigits(normalizedPhoneNumber);


        List<String> allPossibleVariations = new ArrayList<>();

        for (int i = 0; i < phoneDigits.length(); i++) {
            String substring = phoneDigits.substring(i);
            allPossibleVariations.add(substring);
        }

        Okved okved = okvedRepository.findFirstByCodeIn(allPossibleVariations).orElse(null);

        if (okved == null) {
            Page<Okved> okveds = okvedRepository.findAll(Pageable.ofSize(10));
            return okveds.getContent();
        }

        return List.of(okved);

    }

    @Nonnull
    private static String extractLastSixDigits(String normalizedPhoneNumber) {
        return normalizedPhoneNumber.substring(6);
    }
}
