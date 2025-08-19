package com.kausar.product_api.product;

import com.kausar.product_api.common.NotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {
    private final ProductRepository repo;
    public List<Product> getAll(String q){
        if(q != null && !q.isBlank()){
            return repo.findByNameContainingIgnoreCase(q);
        }
        return repo.findAll();
    }

    public Product getById(Long id){
        return repo.findById(id).orElseThrow(() -> new NotFoundException("Product not found with id "+id));
    }

    public Product create(Product p){
        p.setId(null);    // ensure new
        return repo.save(p);
    }

    public void delete(Long id){
        Product existing = getById(id);
        repo.delete(existing);
    }

    public Product update(Long id, Product request) {
        Product existing = getById(id);
        existing.setName(request.getName());
        existing.setDescription(request.getDescription());
        existing.setPrice(request.getPrice());
        existing.setQuantity(request.getQuantity());
        return repo.save(existing);
    }

    public Product increaseQuantity(Long id, int by){
        Product existing = getById(id);
        existing.setQuantity(existing.getQuantity() + by);
        return repo.save(existing);
    }

    public List<Product> filterPrice(BigDecimal minPrice, BigDecimal maxPrice){
        return repo.findByPriceBetween(minPrice, maxPrice);
    }
}