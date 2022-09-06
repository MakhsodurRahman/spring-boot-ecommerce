package com.makhsodur.ecommerce.config;

import com.makhsodur.ecommerce.entity.Product;
import com.makhsodur.ecommerce.entity.ProductCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

//this class create for  disable http method for product : delete,put,post
@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {

    private final EntityManager entityManager;

    public MyDataRestConfig(EntityManager theEntityManager){
        entityManager = theEntityManager;
    }
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        HttpMethod[] theUnsupportedActions = {HttpMethod.PUT,HttpMethod.DELETE,HttpMethod.POST};

        //disable http method for product : delete,put,post

        config.getExposureConfiguration()
                .forDomainType(Product.class)
                .withItemExposure(((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions)))
                .withCollectionExposure(((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions)));

        //disable http method for productCategory : delete,put,post
        config.getExposureConfiguration()
                .forDomainType(ProductCategory.class)
                .withItemExposure(((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions)))
                .withCollectionExposure(((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions)));
        //call internal helper method
        exposeIds(config);
    }

    private void exposeIds(RepositoryRestConfiguration config) {

        //get a list of all entity class from entity manager
        Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();

        //create an array list of entity type
        List<Class> entityClasses = new ArrayList<>();

        //get the entity types for the entities
        for(EntityType entityType: entities){
            entityClasses.add(entityType.getJavaType());
        }

        // expose the entities ids for the array of entity/domain type
        Class[] domainType = entityClasses.toArray(new Class[0]);
        config.exposeIdsFor(domainType);

    }
}
