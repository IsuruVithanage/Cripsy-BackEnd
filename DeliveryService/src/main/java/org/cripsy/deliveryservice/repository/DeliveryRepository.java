package org.cripsy.deliveryservice.repository;

import org.cripsy.deliveryservice.model.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface DeliveryRepository extends JpaRepository<Delivery, Integer>{

    @Transactional
    @Modifying
    @Query("UPDATE Delivery d SET d.availability = :availability WHERE d.personId = :personId")
    int updateAvailabilityByPersonId(Integer personId, Boolean availability);
}



