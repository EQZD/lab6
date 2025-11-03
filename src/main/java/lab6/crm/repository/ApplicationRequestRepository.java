package lab6.crm.repository;

import lab6.crm.entity.ApplicationRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRequestRepository extends JpaRepository<ApplicationRequest, Long> {
    List<ApplicationRequest> findAllByCourseId(Long courseId);
}
