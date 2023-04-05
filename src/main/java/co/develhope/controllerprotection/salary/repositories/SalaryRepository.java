package co.develhope.controllerprotection.salary.repositories;

import co.develhope.controllerprotection.salary.entities.Salary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SalaryRepository extends JpaRepository<Salary, Long> {

    Optional<Salary> findByUserId(Long id);

}
