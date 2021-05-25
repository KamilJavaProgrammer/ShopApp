package ShopAppBackend.User;


import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {


    User findByUsername(String username);
    boolean  existsUserByUsername(String username);
    boolean  existsUserByEmail(String email);
    User  findByEmail(String mail);

    @Query(value = "SELECT  client_id from users where id = :id",nativeQuery = true)
    Long getClientId(Long id);

    @Query(value = "SELECT  shop_client_id from users where username = :username",nativeQuery = true)
    Long getClientIdByUserUsername(String username);

    @Query(value = "SELECT  client_id from users where username = :username",nativeQuery = true)
    Long getClientByUsername1(String username);

    @Query(value = "SELECT id from users where username = :username",nativeQuery = true)
    Long getUserId(String username);


    @Query(value = "SELECT id,role,username from users where username = :username",nativeQuery = true)
    Long getUsersByas();


//    @Query(value = "select u.username,u.id,m. from User and Message m inner join u.messages")

    //za pomoca jpql///
//    @Query(value = "SELECT u.username\n" +
//            "      ,m.messageText\n" +
//            "  FROM User u INNER JOIN Message m\n" +
//            "             ON u.id = m.author.id")
//
//
//    List<sdfconvertet> saas();

//    @Query(value = "SELECT new ShopAppBackend.User.sdfconvertet(u.username,m.messageText) FROM User u INNER JOIN Message m\n" +
//            "             ON u.id = m.author.id GROUP by u.username")
//
//
//    List<sdfconvertet> saas();





    @Query(value = "SELECT users.username ,messages.message_text FROM users INNER JOIN messages ON users.id = messages.author_id GROUP by message_text" ,nativeQuery = true)

            List<Object> saas1();

}
