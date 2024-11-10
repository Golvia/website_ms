package com.golvia_waitlist.demo.service;

import com.golvia_waitlist.demo.entity.Waitlist;
import com.golvia_waitlist.demo.response.ResponseDto;


public interface WaitlistService {
    ResponseDto<Waitlist> waitlist(Waitlist waitlist) ;

}
