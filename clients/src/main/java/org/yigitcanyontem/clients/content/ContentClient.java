package org.yigitcanyontem.clients.content;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "content")
public interface ContentClient {
}
