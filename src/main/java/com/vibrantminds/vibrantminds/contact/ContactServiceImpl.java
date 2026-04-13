package com.vibrantminds.vibrantminds.contact;

import com.vibrantminds.vibrantminds.contact.dto.ContactRequestDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String adminEmail;

    @Override
    public void sendContactEmail(ContactRequestDto dto) {

        try {
            // ── Email 1: Notify Admin ──────────────────
            sendAdminNotification(dto);

            // ── Email 2: Confirm to Sender ─────────────
            sendConfirmationToUser(dto);

        } catch (MessagingException e) {
            throw new RuntimeException(
                    "Failed to send email : " + e.getMessage());
        }
    }

    // Email to Admin
    private void sendAdminNotification(ContactRequestDto dto)
            throws MessagingException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper =
                new MimeMessageHelper(message, true);

        helper.setTo(adminEmail);
        helper.setSubject("New Contact Form Submission - "
                + dto.getSubject());

        String content = """
                <html>
                <body style="font-family: Arial, sans-serif;
                             padding: 20px;">
                
                    <h2 style="color: #2c3e50;">
                        New Contact Form Submission
                    </h2>
                    
                    <table style="width: 100%;
                                  border-collapse: collapse;">
                        <tr>
                            <td style="padding: 8px;
                                       font-weight: bold;">
                                Name:
                            </td>
                            <td style="padding: 8px;">
                                %s
                            </td>
                        </tr>
                        <tr style="background-color: #f2f2f2;">
                            <td style="padding: 8px;
                                       font-weight: bold;">
                                Email:
                            </td>
                            <td style="padding: 8px;">
                                %s
                            </td>
                        </tr>
                        <tr>
                            <td style="padding: 8px;
                                       font-weight: bold;">
                                Phone:
                            </td>
                            <td style="padding: 8px;">
                                %s
                            </td>
                        </tr>
                        <tr style="background-color: #f2f2f2;">
                            <td style="padding: 8px;
                                       font-weight: bold;">
                                Subject:
                            </td>
                            <td style="padding: 8px;">
                                %s
                            </td>
                        </tr>
                        <tr>
                            <td style="padding: 8px;
                                       font-weight: bold;">
                                Message:
                            </td>
                            <td style="padding: 8px;">
                                %s
                            </td>
                        </tr>
                    </table>
                    
                    <br/>
                    <p style="color: #7f8c8d; font-size: 12px;">
                        This email was sent from VibrantMinds
                        contact form.
                    </p>
                    
                </body>
                </html>
                """.formatted(
                        dto.getName(),
                        dto.getEmail(),
                        dto.getPhone(),
                        dto.getSubject(),
                        dto.getMessage()
                );

        helper.setText(content, true);
        mailSender.send(message);
    }

    // Confirmation Email to User
    private void sendConfirmationToUser(ContactRequestDto dto)
            throws MessagingException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper =
                new MimeMessageHelper(message, true);

        helper.setTo(dto.getEmail());
        helper.setSubject("Thank you for contacting VibrantMinds!");

        String content = """
                <html>
                <body style="font-family: Arial, sans-serif;
                             padding: 20px;">
                
                    <h2 style="color: #2c3e50;">
                        Thank You, %s!
                    </h2>
                    
                    <p>We have received your message and will
                    get back to you within <strong>24 hours</strong>.
                    </p>
                    
                    <h3 style="color: #2c3e50;">
                        Your Message Summary:
                    </h3>
                    
                    <table style="width: 100%;
                                  border-collapse: collapse;">
                        <tr style="background-color: #f2f2f2;">
                            <td style="padding: 8px;
                                       font-weight: bold;">
                                Subject:
                            </td>
                            <td style="padding: 8px;">
                                %s
                            </td>
                        </tr>
                        <tr>
                            <td style="padding: 8px;
                                       font-weight: bold;">
                                Message:
                            </td>
                            <td style="padding: 8px;">
                                %s
                            </td>
                        </tr>
                    </table>
                    
                    <br/>
                    
                    <div style="background-color: #3498db;
                                padding: 15px;
                                border-radius: 5px;
                                color: white;">
                        <h3 style="margin: 0;">
                            VibrantMinds Technologies
                        </h3>
                        <p style="margin: 5px 0;">
                             Viva Building, Warje, Pune 411058
                        </p>
                        <p style="margin: 5px 0;">
                             +91 95035 79517
                        </p>
                        <p style="margin: 5px 0;">
                             support@vibrantmindstech.com
                        </p>
                    </div>
                    
                    <br/>
                    <p style="color: #7f8c8d; font-size: 12px;">
                        This is an automated email. Please do not
                        reply to this email.
                    </p>
                    
                </body>
                </html>
                """.formatted(
                        dto.getName(),
                        dto.getSubject(),
                        dto.getMessage()
                );

        helper.setText(content, true);
        mailSender.send(message);
    }
}