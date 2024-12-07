package org.cripsy.productservice.service;

import lombok.AllArgsConstructor;
import org.cripsy.productservice.dto.ReserveItemDTO;
import org.cripsy.productservice.dto.ReservedStockDTO;
import org.cripsy.productservice.model.ReservedStock;
import org.cripsy.productservice.model.Transaction;
import org.cripsy.productservice.repository.ProductRepository;
import org.cripsy.productservice.repository.TransactionRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReservedStockService {
    private final TransactionRepository transactionRepo;
    private final ProductRepository productRepo;

    @Transactional
    public Integer initializeReservation(List<ReserveItemDTO> reservationDetails){
        if (reservationDetails == null || reservationDetails.isEmpty()) {
            throw new IllegalArgumentException("Reservation details cannot be null or empty");
        }

        Transaction transaction = new Transaction();
        Transaction savedTransaction = transactionRepo.save(transaction);

        String values = reservationDetails.stream().map(item -> {
            Integer productId = item.getProductId();
            Integer quantity = item.getQuantity();

            int updatedRows = productRepo.decrementStock(productId, quantity);
            if (updatedRows == 0) {
                throw new RuntimeException("Insufficient stock for product ID: " + productId);
            }

            return String.format(
                "(%d, %d, %d)",
                savedTransaction.getTransactionId(),
                productId,
                quantity
            );
        }).collect(Collectors.joining(", "));

        transactionRepo.insertReservedStocks(values);
        return savedTransaction.getTransactionId();
    }


    public List<ReservedStockDTO> confirmReservation(Integer transactionId){
        Transaction transaction = transactionRepo.findById(transactionId)
            .orElseThrow(() -> new RuntimeException("Transaction not found with ID: " + transactionId));

        List<ReservedStock> reservedStocks = transaction.getReservedStocks();

        List<ReservedStockDTO> reservedStockList = reservedStocks.stream().map(item -> {
            Integer productId = item.getProduct().getProductId();
            Double price = item.getProduct().getPrice();
            Double discount = item.getProduct().getDiscount();
            Integer reservedQuantity = item.getQuantity();
            return new ReservedStockDTO(productId, reservedQuantity, price, discount);
        }).toList();

        transactionRepo.deleteById(transactionId);
        return reservedStockList;
    }

    @Transactional
    public String cancelReservation(Integer transactionId){
        Transaction transaction = transactionRepo.findById(transactionId)
            .orElseThrow(() -> new RuntimeException("Transaction not found with ID: " + transactionId));

        List<ReservedStock> reservedStocks = transaction.getReservedStocks();

        for (ReservedStock item : reservedStocks) {
            Integer productId = item.getProduct().getProductId();
            Integer reservedQuantity = item.getQuantity();
            productRepo.incrementStock(productId, reservedQuantity);
        }

        transactionRepo.deleteById(transactionId);
        return "Order canceled";
    }


    @Scheduled(fixedRate = 60000) // Run every minute
    @Transactional
    public void rollbackExpiredReservations() {
        System.out.println("Checking For Rollback Transactions...");
        ZonedDateTime expiryTime = ZonedDateTime.now(ZoneId.of("UTC")).minusMinutes(3);
        List<Transaction> expiredTransactions = transactionRepo.findExpiredTransactions(expiryTime);

        for (Transaction transaction : expiredTransactions) {
            try {
                this.cancelReservation(transaction.getTransactionId());
                System.out.println("Rolled back transaction ID: " + transaction.getTransactionId());
            } catch (Exception e){
                System.err.println("Failed to rollback transaction ID: " + transaction.getTransactionId() + ". Error: " + e.getMessage());
            }
        }
    }

}
