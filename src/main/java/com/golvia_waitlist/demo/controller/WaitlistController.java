package com.golvia_waitlist.demo.controller;


import com.golvia_waitlist.demo.entity.Waitlist;
import com.golvia_waitlist.demo.service.WaitlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class WaitlistController {

    private final WaitlistService waitlistService;

    @PostMapping("/waitList")
    public String Waitlist(@RequestBody Waitlist waitlist) {
        return waitlistService.waitlist(waitlist);

    }
}
