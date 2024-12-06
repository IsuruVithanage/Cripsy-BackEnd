package org.cripsy.OrderService.controller;

import org.cripsy.OrderService.dto.PaymentNotification;
import org.cripsy.OrderService.dto.PaymentRequest;
import org.cripsy.OrderService.dto.PaymentResponse;
import org.cripsy.OrderService.model.Payment;
import org.cripsy.OrderService.repository.PaymentRepository;
import org.cripsy.OrderService.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/payment")
@CrossOrigin(origins = "http://localhost:5173")
public class PaymentController {

    @Value("${payhere.merchant_id}")
    private String MERCHANT_ID;

    @Value("${payhere.secret}")
    private String MERCHANT_SECRET;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private PaymentRepository paymentRepository;

    @PostMapping("/start")
    public ResponseEntity<PaymentResponse> startPayment(@RequestBody PaymentRequest request)
            throws NoSuchAlgorithmException {
        String orderId = request.getOrder_id();
        String amount = request.getAmount();
        String currency = request.getCurrency();

        // Generate MD5 hash for the start payment request
        String hash = paymentService.generateHash(
                MERCHANT_ID +
                        orderId +
                        amount +
                        currency +
                        paymentService.generateHash(MERCHANT_SECRET).toUpperCase())
                .toUpperCase();

        return ResponseEntity.ok(new PaymentResponse(MERCHANT_ID, hash));
    }

    @PostMapping(value = "/notify", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<String> paymentNotification(@ModelAttribute PaymentNotification notification)
            throws NoSuchAlgorithmException {
        try {
            // Concatenate all values to create the hash string
            String hashString = MERCHANT_ID +
                    notification.getOrder_id().trim() +
                    notification.getPayhere_amount().trim() +
                    notification.getPayhere_currency().trim() +

                    paymentService.generateHash(MERCHANT_SECRET).toUpperCase();

            // Generate MD5 signature for the notification
            String localMd5Sig = paymentService.generateHash(hashString).toUpperCase();

            // Compare the MD5 signature from the notification with the locally generated
            // one
            if (localMd5Sig.equals(notification.getMd5sig()) && "2".equals(notification.getStatus_code())) {
                Payment payment = new Payment();
                payment.setPaymentAmount(Integer.parseInt(notification.getPayhere_amount()));
                payment.setPaymentDate(LocalDate.now());
                payment.setPaymentMethod("PayHere");
                payment.setOrderId(UUID.fromString(notification.getOrder_id()));
                payment.setPaymentStatus("SUCCESS");

                // Save the payment information in the database
                paymentRepository.save(payment);
                return ResponseEntity.ok("Payment processed successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Payment verification failed.");
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error generating hash.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error processing payment notification.");
        }
    }
}
