package com.makhsodur.ecommerce.dao;

import com.makhsodur.ecommerce.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin("http://localhost:4200")
@RepositoryRestResource(collectionResourceRel = "productCategory", path = "product-category")
//(collectionResourceRel = "productCategory" = json name )  (path = "product-category" = url path)
public interface ProductCategoryRepository extends JpaRepository<ProductCategory,Long> {
}
