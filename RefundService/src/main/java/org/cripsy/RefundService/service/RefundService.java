package org.cripsy.RefundService.service;

import org.cripsy.RefundService.dto.RefundDTO;
import org.cripsy.RefundService.model.Refund;
import org.cripsy.RefundService.repository.RefundRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RefundService {

    private final RefundRepository refundRepository;
    private final ModelMapper modelMapper;

    public List<RefundDTO> getAllRefunds() {
        List<Refund> refundList = refundRepository.findAll();
        return refundList.stream()
                .map(refund -> modelMapper.map(refund, RefundDTO.class))
                .toList();
    }

    public RefundDTO getRefundById(Integer id) {
        Optional<Refund> refund = refundRepository.findById(id);
        return refund.map(value -> modelMapper.map(value, RefundDTO.class)).orElse(null);
    }

    public RefundDTO createRefund(RefundDTO refundDTO) {
        Refund refund = modelMapper.map(refundDTO, Refund.class);
        Refund savedRefund = refundRepository.save(refund);
        return modelMapper.map(savedRefund, RefundDTO.class);
    }

    public RefundDTO updateRefund(Integer id, RefundDTO refundDTO) {
        if (refundRepository.existsById(id)) {
            Refund refund = modelMapper.map(refundDTO, Refund.class);
            refund.setRefundId(id);
            Refund updatedRefund = refundRepository.save(refund);
            return modelMapper.map(updatedRefund, RefundDTO.class);
        }
        return null;
    }

    public void updateRefundRequestStatus(Integer refundId, String status) {
        Optional<Refund> refundOptional = refundRepository.findById(refundId);
        if (refundOptional.isPresent()) {
            Refund refund = refundOptional.get();
            if ("Accept".equalsIgnoreCase(status) || "Reject".equalsIgnoreCase(status)) {
                refund.setRefundState(status);
                refundRepository.save(refund);
            } else {
                throw new IllegalArgumentException("Invalid status. Must be 'Accept' or 'Reject'.");
            }
        } else {
            throw new IllegalArgumentException("Refund with ID " + refundId + " not found.");
        }
    }
    

    public void deleteRefund(Integer id) {
        refundRepository.deleteById(id);
    }
}
