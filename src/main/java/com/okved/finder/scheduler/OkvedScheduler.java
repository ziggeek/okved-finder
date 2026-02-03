package com.okved.finder.scheduler;

import com.okved.finder.client.GithubClient;
import com.okved.finder.entity.Okved;
import com.okved.finder.repository.OkvedRepository;
import com.okved.finder.utils.OkvedParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class OkvedScheduler {

    private final OkvedRepository okvedRepository;
    private final GithubClient githubClient;


    @Async
    @Transactional
//    @Scheduled(cron = "0 0 0 * * ?")
    @Scheduled(initialDelay = 1 * 5 * 1000/*, fixedDelay = Long.MAX_VALUE*/)
    public void dailyCurrencyRateUpdateTask() throws IOException {
        updateOkvedDictionary();
    }


    private void updateOkvedDictionary() throws IOException {
        String jsonResponse = githubClient.getAllOkved();
        List<OkvedParser.OkvedItem> items = OkvedParser.parseAllItemsWithCodeAndName(jsonResponse);

        List<Okved> okveds = items.stream()
                .map(item -> Okved.builder()
                        .code(item.getCode())
                        .name(item.getName())
                        .build())
                .toList();
        log.info("*** okveds size: {}", okveds.size());

        okvedRepository.saveAll(okveds);
        log.info("*** saved");
    }
}
