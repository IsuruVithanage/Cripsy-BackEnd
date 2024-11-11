package org.cripsy.deliveryservice.service;

import lombok.AllArgsConstructor;
import org.cripsy.deliveryservice.dto.DeliveryDTO;
import org.cripsy.deliveryservice.model.Delivery;
import org.cripsy.deliveryservice.repository.DeliveryRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<DeliveryDTO> getAll() {
        List<Delivery> userList = deliveryRepository.findAll();
        return modelMapper.map(userList, new TypeToken<List<DeliveryDTO>>() {
        }.getType());
    }
}
