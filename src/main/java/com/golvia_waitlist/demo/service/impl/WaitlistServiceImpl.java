package com.golvia_waitlist.demo.service.impl;

import com.golvia_waitlist.demo.response.ResponseDto;
import com.golvia_waitlist.demo.entity.Waitlist;
import com.golvia_waitlist.demo.repository.WaitlistRepository;
import com.golvia_waitlist.demo.service.EmailService;
import com.golvia_waitlist.demo.service.WaitlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class WaitlistServiceImpl implements WaitlistService {

    private final WaitlistRepository waitlistRepository;
    private final EmailService emailService;

    @Override
    public ResponseDto<Waitlist> waitlist(Waitlist waitlist) {
        // Validate fields
        if (waitlist.getFirstName() == null || waitlist.getFirstName().isEmpty()) {
            return ResponseDto.<Waitlist>builder()
                    .status("failure")
                    .message("Validation Error")
                    .data(null)
                    .timestamp(LocalDateTime.now())
                    .errors(Collections.singletonList("First name cannot be null or empty."))
                    .build();
        }
        if (waitlist.getLastName() == null || waitlist.getLastName().isEmpty()) {
            return ResponseDto.<Waitlist>builder()
                    .status("failure")
                    .message("Validation Error")
                    .data(null)
                    .timestamp(LocalDateTime.now())
                    .errors(Collections.singletonList("Last name cannot be null or empty."))
                    .build();
        }
        if (waitlist.getEmailAddress() == null || waitlist.getEmailAddress().isEmpty()) {
            return ResponseDto.<Waitlist>builder()
                    .status("failure")
                    .message("Validation Error")
                    .data(null)
                    .timestamp(LocalDateTime.now())
                    .errors(Collections.singletonList("Email address cannot be null or empty."))
                    .build();
        }
        if (waitlist.getProfileType() == null) {
            return ResponseDto.<Waitlist>builder()
                    .status("failure")
                    .message("Validation Error")
                    .data(null)
                    .timestamp(LocalDateTime.now())
                    .errors(Collections.singletonList("Profile type cannot be null."))
                    .build();
        }

        // Validate email format
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex);
        if (!pattern.matcher(waitlist.getEmailAddress()).matches()) {
            return ResponseDto.<Waitlist>builder()
                    .status("failure")
                    .message("Invalid Email Format")
                    .data(null)
                    .timestamp(LocalDateTime.now())
                    .errors(Collections.singletonList("Invalid email address format."))
                    .build();
        }

        // Check if email already exists in the database
        if (waitlistRepository.existsByEmailAddress(waitlist.getEmailAddress())) {
            return ResponseDto.<Waitlist>builder()
                    .status("failure")
                    .message("Duplicate Entry")
                    .data(null)
                    .timestamp(LocalDateTime.now())
                    .errors(Collections.singletonList("Email address already exists in the waitlist."))
                    .build();
        }

        // Save to repository
        waitlistRepository.save(waitlist);

        // Send a confirmation email
        String subject = "Waitlist Confirmation";
        String body = "Hello " + waitlist.getFirstName() + ",\n\n"
                + "Thank you for joining our waitlist! We’ll keep you updated.\n\n"
                + "Best regards,\nYour Company";
        emailService.sendEmail(waitlist.getEmailAddress(), subject, body);

        return ResponseDto.<Waitlist>builder()
                .status("success")
                .message("Waitlist entry added successfully!")
                .data(waitlist)
                .timestamp(LocalDateTime.now())
                .errors(null)
                .build();
    }
}
