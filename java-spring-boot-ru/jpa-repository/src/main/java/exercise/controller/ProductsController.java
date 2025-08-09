package exercise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.OptionalInt;

import exercise.model.Product;
import exercise.repository.ProductRepository;
import exercise.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    private ProductRepository productRepository;

    // BEGIN
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    private List<Product> getFilteredProducts(@RequestParam(required = false) Integer min, @RequestParam(required = false) Integer max) {
        if (min == null && max == null) {
            return productRepository.findAll();
        }
        if (min == null) {
            return productRepository.findByPriceLessThanOrderByPriceAsc(max);
        }
        if (max == null) {
            return productRepository.findByPriceGreaterThanOrderByPriceAsc(min);
        }
        return productRepository.findByPriceBetweenOrderByPriceAsc(min, max);
    }
    // END

    @GetMapping(path = "/{id}")
    public Product show(@PathVariable long id) {

        var product =  productRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));

        return product;
    }
}
