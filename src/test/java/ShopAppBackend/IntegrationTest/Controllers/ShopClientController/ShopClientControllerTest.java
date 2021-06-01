package ShopAppBackend.IntegrationTest.Controllers.ShopClientController;

import ShopAppBackend.Entities.ShopClient;
import ShopAppBackend.Repositories.ShopClientRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
public class ShopClientControllerTest {


   @Autowired
   private MockMvc mockMvc;

   @Autowired
   private ObjectMapper objectMapper;

    @Autowired
    private ShopClientRepository shopClientRepository;


    @Test
    @Transactional
    public void shouldGetAllClients() throws Exception {

        ShopClient shopClient = new ShopClient();
        shopClient.setName("Test");
        shopClientRepository.save(shopClient);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/client/shop"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200)).andReturn();
//                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)));

        ShopClient[] shopClients = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),ShopClient[].class);
        assertThat(shopClients).isNotNull();
        assertThat(shopClients[0].getId()).isEqualTo(shopClient.getId());

    }


    @Test
    @Transactional
    public void shouldGetSingleShopClient() throws Exception {

        ShopClient shopClient = new ShopClient();
        shopClient.setName("Test");
        shopClientRepository.save(shopClient);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/client/shop/" + shopClient.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200)).andReturn();
//                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)));

        ShopClient shopClientInstant = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),ShopClient.class);
        assertThat(shopClientInstant).isNotNull();
        assertThat(shopClientInstant.getId()).isEqualTo(shopClient.getId());

    }

    @Test
    @Transactional
    public void shouldDeleteSingleShopClient() throws Exception {

        ShopClient shopClient = new ShopClient();
        shopClient.setName("Test");
        shopClientRepository.save(shopClient);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/client/shop/" + shopClient.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$",Matchers.is("NO_CONTENT"))).andReturn();
    }


    @Test
    @Transactional
    public void shouldAddSingleShopClient() throws Exception {

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/client/shop")
                .contentType("application/json")
                 .content("{\"name\":\"Test\",\"address\":{\"placeOfresident\":\"Kraków\"}}"))
                 .andDo(MockMvcResultHandlers.print())
                 .andExpect(MockMvcResultMatchers.status().is(200))
                 .andExpect(MockMvcResultMatchers.jsonPath("body",Matchers.is("NO_CONTENT"))).andReturn();

    }





}
