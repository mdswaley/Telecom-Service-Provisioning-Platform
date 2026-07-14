package com.example.Customer.Service.Service;

import com.example.Customer.Service.DTO.CustomerDTO;
import com.example.Customer.Service.Entity.CustomerEntity;
import com.example.Customer.Service.Exception.ResourceNotFoundException;
import com.example.Customer.Service.Repository.CustomerRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepo customerRepo;
    private final ModelMapper modelMapper;

    public CustomerDTO createCustomer(CustomerDTO customerDTO){
        CustomerEntity customerEntity = customerRepo.findByEmail(customerDTO.getEmail()).orElse(null);

        if(customerEntity != null){
            throw new RuntimeException("Customer already exist with given email : "+customerDTO.getEmail());
        }

        CustomerEntity create = modelMapper.map(customerDTO, CustomerEntity.class);
        CustomerEntity save = customerRepo.save(create);

        return modelMapper.map(save, CustomerDTO.class);
    }

    public CustomerDTO getCustomerByCusId(String cus_id){
        CustomerEntity customerEntity = customerRepo.findByCustomerId(cus_id).orElseThrow(
                () -> new ResourceNotFoundException("Customer with give id is not present : "+cus_id)
        );

        return modelMapper.map(customerEntity, CustomerDTO.class);
    }


}
