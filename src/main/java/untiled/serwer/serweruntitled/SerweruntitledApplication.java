package untiled.serwer.serweruntitled;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;
import untiled.serwer.serweruntitled.CompleteOrder.CompleteOrder;
import untiled.serwer.serweruntitled.CompleteOrder.CompleteOrderDTO;
import untiled.serwer.serweruntitled.Invoice.Invoice;
import untiled.serwer.serweruntitled.Invoice.InvoiceDTO;


@SpringBootApplication
@EnableAsync


public class SerweruntitledApplication {


	public static void main(String[] args) {
		SpringApplication.run(SerweruntitledApplication.class, args);

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
		return modelMapper;
	}

	@Bean
	public RestTemplate restTemplate(){

		return new RestTemplate();
	}
}
