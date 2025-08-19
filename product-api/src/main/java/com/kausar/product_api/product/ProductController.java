package com.kausar.product_api.product;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService service;

    @GetMapping
    public List<Product> getAll(@RequestParam(required = false) String q){
        return service.getAll(q);
    }

    @GetMapping("/{id}")
    public Product getOne(@PathVariable Long id){
        return service.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product create(@Valid @RequestBody Product product){
        return service.create(product);
    }

    @PutMapping("/{id}")
    public Product update(@PathVariable Long id, @Valid @RequestBody Product product){
        return service.update(id, product);
    }

    @PatchMapping("/{id}/increase")
    public Product increaseQuantity(@PathVariable Long id, @RequestParam(defaultValue = "1") int by){
        return service.increaseQuantity(id, by);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        service.delete(id);
    }

    @GetMapping("/filter")
    public List<Product> filterPrice(@RequestParam BigDecimal minPrice, @RequestParam BigDecimal maxPrice){
        return service.filterPrice(minPrice, maxPrice);
    }
}
