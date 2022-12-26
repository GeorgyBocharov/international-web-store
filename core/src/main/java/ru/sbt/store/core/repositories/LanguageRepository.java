package ru.sbt.store.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.sbt.store.core.entities.Language;

@Repository
public interface LanguageRepository extends JpaRepository<Language, Long> {

    @Transactional
    @Modifying
    @Query("update Language l set l.name = :name where l.id = :id")
    void updateLanguage(@Param("id") Long id, @Param("name") String name);
}
