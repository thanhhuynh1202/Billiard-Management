package beevengers.billiardmanager.controller;

import beevengers.billiardmanager.config.PrefixConfig;
import beevengers.billiardmanager.dto.request.CategoryRequest;
import beevengers.billiardmanager.dto.response.ApiResponse;
import beevengers.billiardmanager.dto.response.CategoryResponse;
import beevengers.billiardmanager.entity.product.Category;
import beevengers.billiardmanager.exception.common.ResourceHasAvailable;
import beevengers.billiardmanager.exception.common.ResourceNotFoundException;
import beevengers.billiardmanager.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(PrefixConfig.CATEGORY)
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> findAll() {
        List<CategoryResponse> categories = categoryService.findAll().stream().map((element) -> modelMapper.map(element, CategoryResponse.class)).collect(Collectors.toList());
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/findbyid")
    public ResponseEntity<CategoryResponse> findById(@RequestParam(name = "categoryId") Long id) throws ResourceNotFoundException {
        CategoryResponse category = modelMapper.map(categoryService.findById(id), CategoryResponse.class);
        return ResponseEntity.ok(category);
    }

    @PostMapping("/manager")
    public ResponseEntity<CategoryResponse> save(@RequestBody CategoryRequest categoryRequest) throws ResourceHasAvailable {
        CategoryResponse category = modelMapper.map(categoryService.save(modelMapper.map(categoryRequest, beevengers.billiardmanager.entity.product.Category.class)), CategoryResponse.class);
        return ResponseEntity.ok(category);
    }

    @PutMapping("/manager/{id}")
    public ResponseEntity<CategoryResponse> update(@PathVariable Long id, @RequestBody CategoryRequest categoryRequest) throws ResourceHasAvailable, ResourceNotFoundException {
        Category category = modelMapper.map(categoryRequest, Category.class);
        category = categoryService.update(id, category);
        CategoryResponse categoryResponse = modelMapper.map(category, CategoryResponse.class);
        return ResponseEntity.ok(categoryResponse);
    }

    @DeleteMapping("/manager/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long id) throws ResourceNotFoundException {
        categoryService.delete(id);
        return ResponseEntity.ok(new ApiResponse(true, "Xóa thành công"));
    }
}
