package com.golvia_waitlist.demo.repository;

import com.golvia_waitlist.demo.entity.Waitlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WaitlistRepository extends JpaRepository<Waitlist, Long> {

    boolean existsByEmailAddress(String emailAddress);
}
