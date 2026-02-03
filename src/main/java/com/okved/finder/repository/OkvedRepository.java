package com.okved.finder.repository;

import com.okved.finder.entity.Okved;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OkvedRepository extends JpaRepository<Okved, Long>, JpaSpecificationExecutor<Okved> {

    @Query(value = "SELECT * FROM t_okved WHERE code IN (:codes) LIMIT 1",
            nativeQuery = true)
    Optional<Okved> findFirstByCodeIn(@Param("codes") List<String> codes);

}