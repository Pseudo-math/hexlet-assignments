package exercise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

import exercise.model.Product;

import org.springframework.data.domain.Sort;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // BEGIN
    public List<Product> findByPriceBetweenOrderByPriceAsc(Integer startPrice, Integer endPrice);
    public List<Product> findByPriceGreaterThanOrderByPriceAsc(Integer price);
    public List<Product> findByPriceLessThanOrderByPriceAsc(Integer price);
    // END
}
