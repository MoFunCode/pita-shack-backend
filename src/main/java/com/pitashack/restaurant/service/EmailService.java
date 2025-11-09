package com.pitashack.restaurant.service;

import com.pitashack.restaurant.model.Order;
import com.pitashack.restaurant.model.OrderItem;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public void sendOrderConfirmation(Order order) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(order.getCustomerEmail());
            helper.setSubject("Order Confirmation - " + order.getOrderNumber());

            String emailContent = buildOrderConfirmationEmail(order);
            helper.setText(emailContent, true); // true = HTML email

            mailSender.send(message);
            log.info("Order confirmation email sent to: {}", order.getCustomerEmail());

        } catch (MessagingException e) {
            log.error("Failed to send order confirmation email to: {}", order.getCustomerEmail(), e);
            // Don't throw exception - we don't want email failures to break order creation
        }
    }

    public void sendNewOrderNotification(Order order) {
    try {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom(fromEmail);
        helper.setTo(fromEmail); // Send to restaurant email (yourself)
        helper.setSubject("🔔 New Order Received - " + order.getOrderNumber());

        String emailContent = buildNewOrderNotificationEmail(order);
        helper.setText(emailContent, true);

        mailSender.send(message);
        log.info("New order notification sent to restaurant");

    } catch (MessagingException e) {
        log.error("Failed to send new order notification", e);
        }
    }

    private String buildOrderConfirmationEmail(Order order) {
    StringBuilder sb = new StringBuilder();
    sb.append("<html><body style='font-family: Arial, sans-serif;'>");
    sb.append("<h2>Thank you for your order!</h2>");
    sb.append("<p>Hi ").append(order.getCustomerName()).append(",</p>");
    sb.append("<p>Your order has been received and is being processed.</p>");

    sb.append("<h3>Order Details:</h3>");
    sb.append("<p><strong>Order Number:</strong> ").append(order.getOrderNumber()).append("</p>");
    sb.append("<p><strong>Order Type:</strong> ").append(order.getOrderType()).append("</p>");
    sb.append("<p><strong>Status:</strong> ").append(order.getStatus()).append("</p>");

    if (order.getDeliveryAddress() != null) {
        sb.append("<p><strong>Delivery Address:</strong> ").append(order.getDeliveryAddress()).append("</p>");
    }

    sb.append("<h3>Items:</h3>");
    sb.append("<ul>");
    for (OrderItem item : order.getItems()) {
        sb.append("<li>").append(item.getQuantity()).append("x ")
                .append(item.getMenuItem().getName())
                .append(" - $").append(item.getSubtotal()).append("</li>");
    }
    sb.append("</ul>");

    sb.append("<h3>Order Total:</h3>");
    sb.append("<p><strong>Subtotal:</strong> $").append(order.getSubtotal()).append("</p>");
    sb.append("<p><strong>Tax:</strong> $").append(order.getTax()).append("</p>");
    if (order.getDeliveryFee() != null) {
        sb.append("<p><strong>Delivery Fee:</strong> $").append(order.getDeliveryFee()).append("</p>");
    }
    sb.append("<p style='font-size: 18px;'><strong>Total:</strong> $").append(order.getTotalPrice()).append("</p>");

    sb.append("<p>We'll notify you when your order status changes.</p>");
    sb.append("<p>Best regards,<br>Pita Shack Grill Team</p>");
    sb.append("</body></html>");

    return sb.toString();
    }

    private String buildNewOrderNotificationEmail(Order order) {
    StringBuilder sb = new StringBuilder();
    sb.append("<html><body style='font-family: Arial, sans-serif;'>");
    sb.append("<h2 style='color: #28a745;'>New Order Received!</h2>");

    sb.append("<h3>Customer Information:</h3>");
    sb.append("<p><strong>Name:</strong> ").append(order.getCustomerName()).append("</p>");
    sb.append("<p><strong>Email:</strong> ").append(order.getCustomerEmail()).append("</p>");
    sb.append("<p><strong>Phone:</strong> ").append(order.getCustomerPhone()).append("</p>");

    sb.append("<h3>Order Details:</h3>");
    sb.append("<p><strong>Order Number:</strong> ").append(order.getOrderNumber()).append("</p>");
    sb.append("<p><strong>Order Type:</strong> ").append(order.getOrderType()).append("</p>");

    if (order.getDeliveryAddress() != null) {
        sb.append("<p><strong>Delivery Address:</strong> ").append(order.getDeliveryAddress()).append("</p>");
    }

    if (order.getSpecialInstructions() != null) {
        sb.append("<p><strong>Special Instructions:</strong> ").append(order.getSpecialInstructions()).append("</p>");
    }

    sb.append("<h3>Items:</h3>");
    sb.append("<ul>");
    for (OrderItem item : order.getItems()) {
        sb.append("<li>").append(item.getQuantity()).append("x ")
                .append(item.getMenuItem().getName())
                .append(" - $").append(item.getSubtotal()).append("</li>");
    }
    sb.append("</ul>");

    sb.append("<h3><strong>Total: $").append(order.getTotalPrice()).append("</strong></h3>");

    sb.append("</body></html>");

    return sb.toString();
    }
}

