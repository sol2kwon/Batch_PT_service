package batch_service.batch_service.packaze;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass // 엔티티가 베이스 엔티티를 상속받게 되면 내부 정의된 것들을 컬럼으로 인식
@EntityListeners(AuditingEntityListener.class) //jpa 엔티티에 이벤트 발생할때 콜백 처리, 코드를 실행하는 방법 // 내부 메소드로 실행되면 자동으로 업데이트 시켜줌..
public abstract class BaseEntity {
    @CreatedDate
    @Column(updatable = false, nullable = false) //업데이트되지 않도록 추가
    private LocalDateTime createAt;

    @LastModifiedDate //변경할때 업데이트
    private LocalDateTime modifieAt;
}
