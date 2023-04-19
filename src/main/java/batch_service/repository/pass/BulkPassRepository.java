package batch_service.repository.pass;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface BulkPassRepository extends JpaRepository<BulkPassEntity,Integer> {
    List<BulkPassEntity> finByStatusAndStartedAtGreaterThan(BulkPassStatus status, LocalDateTime startedAt);
}
