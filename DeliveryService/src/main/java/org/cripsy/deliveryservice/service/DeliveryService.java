package org.cripsy.deliveryservice.service;

import org.cripsy.deliveryservice.dto.DeliveryDTO;
import org.cripsy.deliveryservice.model.Delivery;
import org.cripsy.deliveryservice.repository.DeliveryRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;
import java.util.Optional;

@Service
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public DeliveryService(DeliveryRepository deliveryRepository, ModelMapper modelMapper) {
        this.deliveryRepository = deliveryRepository;
        this.modelMapper = modelMapper;
    }

    public List<DeliveryDTO> getAllDeliveryPersons() {
        List<Delivery> deliveryPersons = deliveryRepository.findAll();
        return modelMapper.map(deliveryPersons, new TypeToken<List<DeliveryDTO>>() {
        }.getType());
    }

    public DeliveryDTO getDeliveryPersonById(Integer id) {
        Optional<Delivery> deliveryPerson = deliveryRepository.findById(id);
        return deliveryPerson.map(person -> modelMapper.map(person, DeliveryDTO.class)).orElse(null);
    }

    public DeliveryDTO createDeliveryPerson(DeliveryDTO deliveryDTO) {
        Delivery deliveryPerson = modelMapper.map(deliveryDTO, Delivery.class);
        deliveryPerson = deliveryRepository.save(deliveryPerson);
        return modelMapper.map(deliveryPerson, DeliveryDTO.class);
    }

    public DeliveryDTO updateDeliveryPerson(Integer id, DeliveryDTO deliveryDTO) {
        if (deliveryRepository.existsById(id)) {
            Delivery deliveryPerson = modelMapper.map(deliveryDTO, Delivery.class);
            deliveryPerson.setPersonId(id);
            deliveryPerson = deliveryRepository.save(deliveryPerson);
            return modelMapper.map(deliveryPerson, DeliveryDTO.class);
        }
        return null;
    }

    public boolean deleteDeliveryPerson(Integer id) {
        if (deliveryRepository.existsById(id)) {
            deliveryRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public DeliveryDTO getAvailableDeliveryPerson() {
        Optional<Delivery> availablePerson = deliveryRepository.findAll().stream()
                .filter(delivery -> delivery.isAvailability()) // Use lambda expression
                .findFirst();
        return availablePerson.map(person -> modelMapper.map(person, DeliveryDTO.class)).orElse(null);
    }

    /**
     * Update the availability of a delivery person.
     *
     * @param id           The ID of the delivery person.
     * @param availability The new availability status.
     * @return True if the update was successful, false otherwise.
     */
    public boolean updateAvailability(Integer id, boolean availability) {
        Optional<Delivery> deliveryPerson = deliveryRepository.findById(id);
        if (deliveryPerson.isPresent()) {
            Delivery person = deliveryPerson.get();
            person.setAvailability(availability);
            deliveryRepository.save(person);
            return true;
        }
        return false;
    }

}
