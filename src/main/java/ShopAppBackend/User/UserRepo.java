package ShopAppBackend.User;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {


    User findByUsername(String f);
    boolean  existsUserByUsername(String username);
    boolean  existsUserByEmail(String email);
    User  findByEmail(String mail);

    @Query(value = "SELECT  client_id from users where id = :id",nativeQuery = true)
    Long getClientId(Long id);

    @Query(value = "SELECT  client_id from users where username = :username",nativeQuery = true)
    Long getClientByUsername1(String username);

    @Query(value = "SELECT id from users where username = :username",nativeQuery = true)
    Long getUserId(String username);




}
