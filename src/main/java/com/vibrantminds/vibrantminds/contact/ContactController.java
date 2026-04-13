package com.vibrantminds.vibrantminds.contact;

import com.vibrantminds.vibrantminds.contact.dto.ContactRequestDto;
import com.vibrantminds.vibrantminds.exception.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contact")
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;

    //  PUBLIC - Send contact email
    // POST /api/contact
    @PostMapping
    public ResponseEntity<ApiResponse<?>> sendContactEmail(
            @Valid @RequestBody ContactRequestDto dto) {

        contactService.sendContactEmail(dto);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Message sent successfully. " +
                        "We will get back to you within 24 hours!"));
    }
}