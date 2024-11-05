package com.golvia_waitlist.demo.service.impl;

import com.golvia_waitlist.demo.entity.Waitlist;
import com.golvia_waitlist.demo.repository.WaitlistRepository;
import com.golvia_waitlist.demo.service.EmailService;
import com.golvia_waitlist.demo.service.WaitlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;


@Service
@RequiredArgsConstructor
public class WaitlistServiceImpl implements WaitlistService {


    private final WaitlistRepository waitlistRepository;
    private final EmailService emailService;

    @Override
    public String waitlist(Waitlist waitlist) {
        // Validate fields are not null
        if (waitlist.getFirstName() == null || waitlist.getFirstName().isEmpty()) {
            return "First name cannot be null or empty.";
        }
        if (waitlist.getLastName() == null || waitlist.getLastName().isEmpty()) {
            return "Last name cannot be null or empty.";
        }
        if (waitlist.getEmailAddress() == null || waitlist.getEmailAddress().isEmpty()) {
            return "Email address cannot be null or empty.";
        }
        if (waitlist.getProfileType() == null) {
            return "Profile type cannot be null.";
        }

        // Validate email format
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex);
        if (!pattern.matcher(waitlist.getEmailAddress()).matches()) {
            return "Invalid email address format.";
        }
        // Check if email already exists in the database
        if (waitlistRepository.existsByEmailAddress(waitlist.getEmailAddress())) {
            return "Email address already exists in the waitlist.";
        }

        // Save to repository if all validations pass
        waitlistRepository.save(waitlist);

        // Send a confirmation email
        String subject = "Waitlist Confirmation";
        String body = "Hello " + waitlist.getFirstName() + ",\n\n"
                + "Thank you for joining our waitlist! We’ll keep you updated.\n\n"
                + "Best regards,\nYour Company";
        emailService.sendEmail(waitlist.getEmailAddress(), subject, body);

        return "Waitlist entry added successfully!";
    }

}