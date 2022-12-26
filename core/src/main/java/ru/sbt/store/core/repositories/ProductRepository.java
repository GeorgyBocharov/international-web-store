package ru.sbt.store.core.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.sbt.store.core.entities.Product;


@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {

    @Transactional(readOnly = true)
    @Query("select p from Product p left join fetch p.infos infos left join fetch p.parameters params where p.id=:id")
    Product findProductById(@Param("id") Long id);


    @Transactional(readOnly = true)
    @Query("select p from Product p " +
            "left join fetch p.infos infos " +
            "left join fetch p.parameters params " +
            "where p.id=:id and infos.language.id=:languageId")
    Product findProductByIdAndLanguage(@Param("id") Long id, @Param("languageId") Long languageId);

}
