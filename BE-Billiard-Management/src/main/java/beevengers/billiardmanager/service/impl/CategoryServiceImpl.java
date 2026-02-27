package beevengers.billiardmanager.service.impl;

import beevengers.billiardmanager.entity.product.Category;
import beevengers.billiardmanager.exception.common.ResourceHasAvailable;
import beevengers.billiardmanager.exception.common.ResourceNotFoundException;
import beevengers.billiardmanager.repo.CategoryRepository;
import beevengers.billiardmanager.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category findById(Long id) throws ResourceNotFoundException {
        return categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("id", id));
    }

    @Override
    public Category save(Category category) throws ResourceHasAvailable {
        if (categoryRepository.existsByName(category.getName())) {
            throw new ResourceHasAvailable("name", category);
        }
        return categoryRepository.save(category);
    }

    @Override
    public Category update(Long id, Category categoryRequest) throws ResourceNotFoundException, ResourceHasAvailable {
        categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("id", id));
        categoryRequest.setId(id);
        if (categoryRepository.existsByName(categoryRequest.getName())) {
            throw new ResourceHasAvailable("name", categoryRequest);
        }
        return categoryRepository.save(categoryRequest);
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("id", id));
        categoryRepository.delete(category);
    }
}
