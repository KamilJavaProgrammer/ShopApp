package ShopAppBackend.ServiceClient.ShopClient;

import ShopAppBackend.ServiceClient.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopClientRepository extends JpaRepository<ShopClient,Long>  {

   ShopClient findByNameAndSurnameAndEmail(String name,String surname,String email);
   Client findBySurname(String surname);
   boolean existsByName(String name1);

   @Query(value = "SELECT id FROM `clients` WHERE user_id = :userId",nativeQuery = true)
   Long getUser(Long userId);


   boolean existsShopClientByNameAndSurnameAndEmail(String name,String surname,String email);

   boolean existsClientByNameAndSurnameAndEmailAndAddress_Id(String name, String surname, String email, Long id);


}
