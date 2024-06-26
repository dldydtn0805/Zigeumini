package com.backend.api.domain.notice.controller;

import com.backend.api.domain.notice.entity.SseEmitters;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@Slf4j
@RequestMapping("/sse")
@RequiredArgsConstructor
public class SseController {
    private final SseEmitters sseEmitters;

    @GetMapping(value = "/connect/{channelName}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter connect(@PathVariable String channelName) {
        SseEmitter emitter = new SseEmitter(3_000_000L);
        log.info("구독 요청 - SseController {}", channelName);
        sseEmitters.add(channelName, emitter);
        try {
            log.info("emitter.send 요청 전 : {} ", channelName);

            emitter.send(SseEmitter.event()
                .name("connect")
                .data("connected!")
                .reconnectTime(1000L)
            );
            log.info("emitter.send(SseEmitter.event() - {}", channelName);

        } catch (IOException e) {
            log.info("처음 구독시 에러가 발생했습니다. - {}", channelName);
        }
        return emitter;
    }
}