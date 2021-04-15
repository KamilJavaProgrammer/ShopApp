package ShopAppBackend.ShopClient;

import ShopAppBackend.ServiceClient.ShopClient.ShopClient;
import ShopAppBackend.ServiceClient.ShopClient.ShopClientController;
import ShopAppBackend.ServiceClient.ShopClient.ShopClientRepository;
import ShopAppBackend.ServiceClient.ShopClient.ShopClientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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



}
