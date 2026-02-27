package beevengers.billiardmanager.service;

import beevengers.billiardmanager.entity.Customer;
import beevengers.billiardmanager.exception.common.ResourceHasAvailable;
import beevengers.billiardmanager.exception.common.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerService {
    Page<Customer> findAll(Pageable pageable);

    Customer findById(Long id) throws ResourceNotFoundException;

    Customer save(Customer customer) throws ResourceHasAvailable;

    Customer update(Long id, Customer customer) throws ResourceNotFoundException, ResourceHasAvailable;

    void delete(Long id) throws ResourceNotFoundException;
}
