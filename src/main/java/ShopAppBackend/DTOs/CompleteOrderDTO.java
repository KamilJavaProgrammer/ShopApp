package ShopAppBackend.DTOs;

import lombok.Data;

@Data
public class CompleteOrderDTO {


    private Long id;
    private String deliveryOption;
    private String paymentMethod;
    private boolean invoice;
    private double sumPaid;
    private String state;
    private String date;


}
