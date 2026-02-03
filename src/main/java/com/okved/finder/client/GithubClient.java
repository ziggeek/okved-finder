package com.okved.finder.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(
        value = "github",
        url = "${feign.client.config.github.url}")
public interface GithubClient {

    @GetMapping("/bergstar/testcase/master/okved.json")
    String getAllOkved();

}
