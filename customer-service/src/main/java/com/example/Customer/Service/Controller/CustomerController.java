package com.example.Customer.Service.Controller;

import com.example.Customer.Service.DTO.CustomerDTO;
import com.example.Customer.Service.Service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping("/customer/v2")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/create")
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CustomerDTO customerDTO){
        CustomerDTO customerDTO1 = customerService.createCustomer(customerDTO);
        return ResponseEntity.ok(customerDTO1);
    }

    @GetMapping("/get/{cus_id}")
    public ResponseEntity<CustomerDTO> getCustomer(@PathVariable("cus_id") String cus_id){
        return ResponseEntity.ok(customerService.getCustomerByCusId(cus_id));
    }

    @GetMapping("/getAllCus")
    public ResponseEntity<List<CustomerDTO>> getAllCus(){
        return ResponseEntity.ok(customerService.getAllCustomer());
    }

    @PatchMapping("/update/{cus_id}")
    public ResponseEntity<CustomerDTO> updatePartially(@PathVariable("cus_id") String cus_id,
                                                       @RequestBody Map<String, Object> update){
        CustomerDTO updateCustomer = customerService.updateCustomerPartiallyById(cus_id, update);
        return ResponseEntity.ok(updateCustomer);
    }
}
