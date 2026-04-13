package com.vibrantminds.vibrantminds.contact;

import com.vibrantminds.vibrantminds.contact.dto.ContactRequestDto;

public interface ContactService {

    void sendContactEmail(ContactRequestDto dto);
}