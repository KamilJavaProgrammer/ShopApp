package ShopAppBackend.Repositories;

import ShopAppBackend.Entities.ShopClient;
import ShopAppBackend.AbstractSuperclass.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopClientRepository extends JpaRepository<ShopClient,Long>  {

   ShopClient findByNameAndSurnameAndEmail(String name,String surname,String email);
   boolean existsShopClientByNameAndSurnameAndEmail(String name,String surname,String email);

}
