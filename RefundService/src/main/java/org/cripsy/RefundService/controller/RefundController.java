package org.cripsy.RefundService.controller;

import org.cripsy.RefundService.dto.RefundDTO;
import org.cripsy.RefundService.service.RefundService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/refunds")
public class RefundController {

    private final RefundService refundService;

    @GetMapping("/getAllRefunds")
    public List<RefundDTO> getAllRefunds() {
        return refundService.getAllRefunds();
    }

    @GetMapping("/{id}")
    public RefundDTO getRefundById(@PathVariable Integer id) {
        return refundService.getRefundById(id);
    }

    @PostMapping("/createRefund")
    public RefundDTO createRefund(@RequestBody RefundDTO refundDTO) {
        return refundService.createRefund(refundDTO);
    }

    @PutMapping("/updateRefund/{id}")
    public RefundDTO updateRefund(@PathVariable Integer id, @RequestBody RefundDTO refundDTO) {
        return refundService.updateRefund(id, refundDTO);
    }

    @DeleteMapping("/deleteRefund/{id}")
    public void deleteRefund(@PathVariable Integer id) {
        refundService.deleteRefund(id);
    }
}
