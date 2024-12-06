package org.cripsy.deliveryservice.repository;

import java.util.Optional;

import org.cripsy.deliveryservice.model.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<Delivery, Integer> {

    Optional<Delivery> findFirstByAvailabilityTrue();

}
