package com.vibrantminds.vibrantminds.admin;

import com.vibrantminds.vibrantminds.admin.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AdminReposotory extends JpaRepository<AdminEntity,Long>{

    Optional<AdminEntity> findByEmail(String email);

    boolean existsByEmail(String email);
}