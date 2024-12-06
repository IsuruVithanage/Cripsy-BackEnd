package org.cripsy.deliveryservice.service;

import org.cripsy.deliveryservice.dto.DeliveryDTO;
import org.cripsy.deliveryservice.model.Delivery;
import org.cripsy.deliveryservice.repository.DeliveryRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return modelMapper.map(deliveryPersons, new TypeToken<List<DeliveryDTO>>() {}.getType());
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

    public void updateAvailability(Integer personId, Boolean availability) {
        int rowsAffected = deliveryRepository.updateAvailabilityByPersonId(personId, availability);
        if (rowsAffected > 0) {
            System.out.println("Availability updated successfully.");
        } else {
            System.out.println("No matching person found.");
        }
    }


}
