package ShopAppBackend;

import ShopAppBackend.CompleteOrder.CompleteOrder;
import ShopAppBackend.User.User;
import ShopAppBackend.User.UserDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;
import ShopAppBackend.CompleteOrder.CompleteOrderDTO;
import ShopAppBackend.Invoice.Invoice;
import ShopAppBackend.Invoice.InvoiceDTO;

@SpringBootApplication
@EnableAsync


public class ShopAppBackendApplication {


	public static void main(String[] args) {
		SpringApplication.run(ShopAppBackendApplication.class, args);

	}

	@Bean
	public ModelMapper getModelMapper(){
		ModelMapper modelMapper = new ModelMapper();
//		modelMapper.addMappings(new PropertyMap<ProductEntity, ProductDTO>() {
//			@Override
//			protected void configure() {
//				map().setName(source.getName());
//				map().setModel(source.getModel());
//				map().setNumberOfItems(source.getNumberOfItems());
//				map().setPathToFile(source.getPathToFile());
//				map().setPrice(source.getPrice());
//				map().setTechnicalData(source.getTechnicalData());
//				map().setState(source.getState());
//
//			}
//
//		});

		modelMapper.addMappings(new PropertyMap<CompleteOrder, CompleteOrderDTO>() {

			@Override
			protected void configure() {
				map().setId(source.getId());
				map().setDeliveryOption(source.getDeliveryOption());
//				map().setInvoice(source.getInvoice());
				map().setPaymentMethod(source.getPaymentMethod());
				map().setSumPaid(source.getSumPaid());
				map().setState(source.getState());
				map().setDate(source.getDate());
			}

		});




		modelMapper.addMappings(new PropertyMap<Invoice, InvoiceDTO>() {

			@Override
			protected void configure() {
				map().setId(source.getId());
				map().setDate(source.getDate());
				map().setInvoicePath(source.getInvoicePath());
				map().setProductBaskets(source.getProductBaskets());
				map().setBusiness(source.getBusiness());
				map().setRecipient(source.getRecipient());
				map().setPayForm(source.getPayForm());
				map().setPaid(source.getPaid());
				map().setSpendFromStock(source.getSpendFromStock());
				map().setPaymentDeadline(source.getPaymentDeadline());
				map().setSumVatValue(source.getSumVatValue());
				map().setSumNettoValue(source.getSumNettoValue());
				map().setSumBruttoValue(source.getSumBruttoValue());
			}

		});

		modelMapper.addMappings(new PropertyMap<User, UserDto>() {
			@Override
			protected void configure() {

				map().setId(source.getId());
				map().setUsername(source.getUsername());
				map().setEmail(source.getEmail());
//				map().setRole(source.getRole());
//				map().setAuthorization(source.isAuthorization());
				map().setShopClient(source.getShopClient());
				map().setPassword(source.getPassword());
//				map().setCodeVerification(source.getCodeVerification());
				map().setChangedPassword(source.getChangedPassword());

			}
		});
		return modelMapper;
	}

	@Bean
	public RestTemplate restTemplate(){

		return new RestTemplate();
	}
}
