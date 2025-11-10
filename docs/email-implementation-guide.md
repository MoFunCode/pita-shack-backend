# Email Notifications Implementation Guide

## Overview
This guide covers implementing email notifications for the Pita Shack Restaurant API. This is a learning implementation - eventually, the production system will integrate with Toast POS API to handle notifications automatically.

---

## Architecture Plan

### Phase 1: Learning (Current)
- ✅ Menu management (Complete)
- ✅ Order system (Complete)
- 🔄 Email notifications (In Progress)
- 📱 Frontend (Planned)

### Phase 2: Production (Future)
- Keep custom frontend
- Integrate with Toast API
- Toast handles: payments, notifications, kitchen display, reporting

---

## Step-by-Step Implementation

### Step 1: Add Email Dependency

**File:** `pom.xml`

Add this dependency inside the `<dependencies>` section:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-mail</artifactId>
</dependency>
```

**After adding:** Run `./mvnw clean install`

**Why?** Spring Boot's mail starter provides JavaMailSender and email functionality.

---

### Step 2: Configure Email Properties

**File:** `src/main/resources/application.properties`

Add these properties:

```properties
# Email Configuration
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your-email@gmail.com
spring.mail.password=your-app-password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
spring.mail.properties.mail.smtp.starttls.required=true

# Custom property for sender display name
app.mail.sender=Pita Shack Grill <noreply@pitashack.com>
```

**Property Explanations:**
- `spring.mail.host`: SMTP server (Gmail's is smtp.gmail.com)
- `spring.mail.port`: Port 587 for TLS encryption
- `spring.mail.username`: Your Gmail address
- `spring.mail.password`: **Gmail App Password** (NOT regular password)
- `mail.smtp.auth=true`: Enable authentication
- `mail.smtp.starttls.enable=true`: Enable TLS encryption

**🔐 Getting Gmail App Password:**
1. Enable 2-Factor Authentication on Google Account
2. Go to Security → App Passwords
3. Generate new app password for "Mail"
4. Use that 16-character password in properties

**⚠️ Security:** Never commit real credentials to Git! Use environment variables in production.

---

### Step 3: Create EmailConfig.java

**File:** `src/main/java/com/pitashack/restaurant/config/EmailConfig.java`

```java
package com.pitashack.restaurant.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.beans.factory.annotation.Value;

import java.util.Properties;

@Configuration
public class EmailConfig {

    @Value("${spring.mail.host}")
    private String mailHost;

    @Value("${spring.mail.port}")
    private int mailPort;

    @Value("${spring.mail.username}")
    private String mailUsername;

    @Value("${spring.mail.password}")
    private String mailPassword;

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost(mailHost);
        mailSender.setPort(mailPort);
        mailSender.setUsername(mailUsername);
        mailSender.setPassword(mailPassword);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.starttls.required", "true");

        return mailSender;
    }
}
```

**Key Concepts:**
- `@Configuration`: Tells Spring this class contains bean definitions
- `@Value`: Injects values from application.properties
- `@Bean`: Creates JavaMailSender that Spring manages and injects
- `JavaMailSenderImpl`: Implementation that actually sends emails

---

### Step 4: Create EmailService.java

**File:** `src/main/java/com/pitashack/restaurant/service/EmailService.java`

```java
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
              .append(" - $").append(item.getPrice()).append("</li>");
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
              .append(" - $").append(item.getPrice()).append("</li>");
        }
        sb.append("</ul>");

        sb.append("<h3><strong>Total: $").append(order.getTotalPrice()).append("</strong></h3>");

        sb.append("</body></html>");

        return sb.toString();
    }
}
```

**Key Concepts:**
- `@Service`: Marks as Spring service component
- `@RequiredArgsConstructor`: Lombok generates constructor for dependency injection
- `@Slf4j`: Lombok provides logging
- `MimeMessage`: Allows HTML emails
- `MimeMessageHelper`: Helper to set from, to, subject, content
- `helper.setText(content, true)`: `true` = HTML format
- **Error Handling**: Catch exceptions, log them, but don't throw - email failures shouldn't break orders
- `StringBuilder`: Efficiently builds HTML email content

---

### Step 5: Update OrderService.java

**File:** `src/main/java/com/pitashack/restaurant/service/OrderService.java`

**Add to class fields:**
```java
private final EmailService emailService;
```
(Lombok's `@RequiredArgsConstructor` will inject it automatically)

**In `createOrder()` method, after line 113 (after saving order):**

```java
Order savedOrder = orderRepository.save(order);

// Send email notifications
try {
    emailService.sendOrderConfirmation(savedOrder);
    emailService.sendNewOrderNotification(savedOrder);
} catch (Exception e) {
    log.warn("Failed to send email notifications for order: {}", savedOrder.getOrderNumber(), e);
    // Continue - don't fail order creation due to email issues
}

return convertToResponse(savedOrder);
```

**Why?**
- Send emails **after** saving to ensure order exists in database
- Wrap in try-catch so email failures don't crash order creation
- Send both customer confirmation and restaurant notification

---

## Testing Your Implementation

### 1. Start Application
```bash
./mvnw spring-boot:run
```

### 2. Place Test Order

**Using curl:**
```bash
curl -X POST http://localhost:8080/api/orders \
  -H "Content-Type: application/json" \
  -d '{
    "customerName": "John Doe",
    "customerEmail": "your-test-email@gmail.com",
    "customerPhone": "555-1234",
    "orderType": "delivery",
    "deliveryAddress": "123 Main St",
    "items": [
      {
        "menuItemId": 1,
        "quantity": 2
      }
    ]
  }'
```

**Or using Postman:**
- Method: POST
- URL: http://localhost:8080/api/orders
- Headers: Content-Type: application/json
- Body: (same JSON as above)

### 3. Check Results

**Email Inbox:**
- Customer email should receive order confirmation
- Restaurant email should receive new order notification

**Application Logs:**
Look for:
```
Order confirmation email sent to: your-test-email@gmail.com
New order notification sent to restaurant
```

---

## Key Learning Concepts

### 1. Dependency Injection
Spring automatically provides `JavaMailSender` to `EmailService` - you don't manually create it.

### 2. Configuration Management
Using `application.properties` to externalize configuration - no hardcoded values.

### 3. HTML Email Building
Creating formatted emails with order details using StringBuilder.

### 4. Error Handling
Gracefully handling email failures without breaking core functionality.

### 5. Separation of Concerns
Email logic lives in `EmailService`, not cluttering `OrderService`.

---

## Future Enhancements

### Optional Improvements:
1. **Async Emails**: Add `@Async` to email methods so they don't slow down order creation
2. **Email Templates**: Use Thymeleaf templates instead of building HTML strings
3. **Status Change Emails**: Send emails when order status changes (in `updateOrderStatus()`)
4. **Email Scheduling**: Send reminder emails for pending orders

---

## SMS Notifications (Optional)

Spring Boot doesn't have built-in SMS support. Need third-party service:

### Popular Options:
- **Twilio** (most popular)
- **AWS SNS**
- **Vonage/Nexmo**

### Cost Consideration:
- Email: Free
- SMS: ~$0.01 per message

### Basic Twilio Example:

**Add dependency:**
```xml
<dependency>
    <groupId>com.twilio.sdk</groupId>
    <artifactId>twilio</artifactId>
    <version>9.14.1</version>
</dependency>
```

**Service implementation:**
```java
@Service
public class SmsService {

    @PostConstruct
    public void init() {
        Twilio.init(accountSid, authToken);
    }

    public void sendOrderConfirmationSms(Order order) {
        String messageBody = String.format(
            "Hi %s! Your order %s confirmed. Total: $%.2f",
            order.getCustomerName(),
            order.getOrderNumber(),
            order.getTotalPrice()
        );

        Message.creator(
            new PhoneNumber(order.getCustomerPhone()),
            new PhoneNumber(fromNumber),
            messageBody
        ).create();
    }
}
```

---

## Production: Toast POS Integration

### When Ready for Production:

**1. Contact Toast**
- Website: https://pos.toasttab.com/
- Get API credentials (sandbox → production)
- Restaurant gets hardware (POS tablets, kitchen displays)

**2. Toast Handles:**
- Payment processing
- Email/SMS notifications
- Kitchen display system
- Receipt printing
- Staff management
- Analytics & reporting

**3. Your System Keeps:**
- Custom branded frontend
- Online ordering interface
- Menu management (or sync with Toast)

### Integration Architecture:

```
Customer Frontend (Your Custom UI)
       ↓
Your Backend API (Spring Boot)
       ↓
Toast API
       ↓
Toast System handles:
  - Payments
  - Notifications
  - Kitchen displays
  - Reporting
```

### Basic Toast Integration:

```java
@Service
public class ToastService {

    public ToastOrderResponse submitOrder(Order order) {
        // Convert your order format to Toast format
        ToastOrderRequest toastOrder = convertToToastFormat(order);

        // Send to Toast API
        ResponseEntity<ToastOrderResponse> response =
            restTemplate.postForEntity(toastApiUrl, toastOrder, ToastOrderResponse.class);

        // Toast automatically sends notifications
        return response.getBody();
    }
}
```

### Migration Strategy (Feature Flag):

```java
@Service
public class NotificationService {

    @Value("${app.use-toast:false}")
    private boolean useToast;

    public void handleNewOrder(Order order) {
        if (useToast) {
            // Production: Use Toast
            toastService.submitOrder(order);
        } else {
            // Development: Use your email system
            emailService.sendOrderConfirmation(order);
            emailService.sendNewOrderNotification(order);
        }
    }
}
```

Switch between systems with config flag in `application.properties`:
```properties
app.use-toast=false  # Development
app.use-toast=true   # Production
```

---

## Cost Comparison

### Your System:
- Hosting: $5-20/month (AWS, DigitalOcean)
- Email: Free (Gmail) or $0.001/email (SendGrid)
- SMS: ~$0.0075/message (Twilio)
- **Total: Very low cost**

### Toast POS:
- Payment processing: ~2.49% + $0.15 per transaction
- Software: Usually $0 with payment processing
- Hardware: ~$1000-$2000 one-time
- **Total: % of revenue + upfront hardware**

---

## Summary

✅ **You're learning the fundamentals** - understanding how email notifications work

✅ **Building working prototype** - functional system for testing

✅ **Planning for scale** - will integrate Toast for production features

✅ **Best of both worlds** - custom frontend + enterprise backend

---

## Resources

- **Spring Mail Documentation**: https://docs.spring.io/spring-boot/docs/current/reference/html/io.html#io.email
- **Toast API Docs**: https://doc.toasttab.com/
- **Gmail App Passwords**: https://support.google.com/accounts/answer/185833
- **Twilio SMS**: https://www.twilio.com/docs/sms

---

**Created:** 2025-11-06
**Project:** Pita Shack Restaurant API
**Phase:** Email Notifications Implementation (Learning)
