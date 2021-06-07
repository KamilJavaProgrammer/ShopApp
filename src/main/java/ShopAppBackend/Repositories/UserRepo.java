package ShopAppBackend.Repositories;


import ShopAppBackend.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {


    User findByUsername(String username);
    boolean  existsUserByUsername(String username);
    boolean  existsUserByEmail(String email);
    User  findByEmail(String mail);


    @Query(value = "SELECT  shop_client_id from users where username = :username",nativeQuery = true)
    Long getClientIdByUserUsername(String username);

    @Query(value = "SELECT  client_id from users where username = :username",nativeQuery = true)
    Long getClientByUsername1(String username);


    @Query(value = "SELECT users.username ,messages.message_text FROM users INNER JOIN messages ON users.id = messages.author_id GROUP by message_text" ,nativeQuery = true)

    List<Object> saas1();
}
