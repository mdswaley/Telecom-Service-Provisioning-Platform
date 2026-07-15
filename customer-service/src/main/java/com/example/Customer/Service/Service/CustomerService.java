package com.example.Customer.Service.Service;

import com.example.Customer.Service.DTO.CustomerDTO;
import com.example.Customer.Service.Entity.CustomerEntity;
import com.example.Customer.Service.Exception.ResourceNotFoundException;
import com.example.Customer.Service.Repository.CustomerRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

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

    public List<CustomerDTO> getAllCustomer(){
        List<CustomerEntity> allCus = customerRepo.findAll();

        return allCus.stream()
                .map(get -> modelMapper.map(get, CustomerDTO.class))
                .toList();
    }


    public CustomerDTO updateCustomerPartiallyById(String cus_id, Map<String, Object> update){
        CustomerEntity customerEntity = customerRepo.findByCustomerId(cus_id).orElseThrow(
                () -> new ResourceNotFoundException("Customer not present with given ID: "+cus_id)
        );

        update.forEach((field, value) ->{
            if(field.equalsIgnoreCase("id") || field.equalsIgnoreCase("customerId")){
                throw new RuntimeException("You don't allow to update IDs");
            }

            Field fieldToBeUpdate = ReflectionUtils.findField(CustomerEntity.class, field);

            if(fieldToBeUpdate != null){
                fieldToBeUpdate.setAccessible(true);
                ReflectionUtils.setField(fieldToBeUpdate, customerEntity, value);
            }
        });

        CustomerEntity save = customerRepo.save(customerEntity);

        return modelMapper.map(save, CustomerDTO.class);
    }


}
