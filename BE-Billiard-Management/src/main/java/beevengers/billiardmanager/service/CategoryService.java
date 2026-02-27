package beevengers.billiardmanager.service;

import beevengers.billiardmanager.entity.product.Category;
import beevengers.billiardmanager.exception.common.ResourceHasAvailable;
import beevengers.billiardmanager.exception.common.ResourceNotFoundException;

import java.util.List;

public interface CategoryService {
    List<Category> findAll();

    Category findById(Long id) throws ResourceNotFoundException;

    Category save(Category category) throws ResourceHasAvailable;

    Category update(Long id, Category category) throws ResourceNotFoundException, ResourceHasAvailable;

    void delete(Long id) throws ResourceNotFoundException;
}
