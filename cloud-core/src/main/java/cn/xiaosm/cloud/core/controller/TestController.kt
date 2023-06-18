package cn.xiaosm.cloud.core.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RequestMapping
@RestController
class TestController {

    @RequestMapping("/test1")
    fun test1(): String {
        return "10";
    }

    class User (
        var date: LocalDateTime
    ) {}

    @RequestMapping("test2")
    fun test2(): User {
        var user = User(LocalDateTime.now());
        return user;
    }

}