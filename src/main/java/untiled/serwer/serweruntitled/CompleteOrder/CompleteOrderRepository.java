package untiled.serwer.serweruntitled.CompleteOrder;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompleteOrderRepository extends JpaRepository<CompleteOrder,Long> {

    @Query(value = "SELECT * from orders where client_id = :clientId",nativeQuery = true)
    List<CompleteOrder> getAllOrder(Long clientId);
}
