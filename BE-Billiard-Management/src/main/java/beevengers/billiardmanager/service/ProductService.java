package beevengers.billiardmanager.service;

import beevengers.billiardmanager.entity.product.Product;
import beevengers.billiardmanager.exception.common.ResourceHasAvailable;
import beevengers.billiardmanager.exception.common.ResourceNotFoundException;

import java.util.List;


public interface ProductService {

    List<Product> findAll();

    Product findById(Long id) throws ResourceNotFoundException;

    Product save(Product product) throws ResourceHasAvailable;

    Product update(Long id, Product product) throws ResourceNotFoundException;

    void delete(Long id) throws ResourceNotFoundException;
}
