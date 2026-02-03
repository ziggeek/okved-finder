package com.okved.finder.controller;

import com.okved.finder.controller.response.OkvedSearchResponse;
import com.okved.finder.entity.Okved;
import com.okved.finder.service.OkvedService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "API поиска ОКВЭД по номеру телефона.")
@Slf4j
@RestController
@RequestMapping("/api/okved")
@RequiredArgsConstructor

public class OkvedFinderController {

    private final OkvedService okvedService;

    @GetMapping("/find")
    public ResponseEntity<OkvedSearchResponse> findOkvedByPhoneNumber(@RequestParam String phoneNumber) {
        log.info("Получен запрос на поиск ОКВЭД для номера: {}", phoneNumber);
        List<Okved> okveds = okvedService.findOkvedByPhoneNumber(phoneNumber);
        OkvedSearchResponse response = OkvedSearchResponse.from(okveds, phoneNumber);
        return ResponseEntity.ok(response);
    }
}
