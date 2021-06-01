package ShopAppBackend.DTOs;

public class CompleteOrderDTO {


    private Long id;

    private String deliveryOption;
    private String paymentMethod;

    private boolean invoice;

    private double sumPaid;
    private String state;
    private String date;

    public CompleteOrderDTO() {
    }

    public CompleteOrderDTO(Long id, String deliveryOption, String paymentMethod, boolean invoice, double sumPaid, String state, String date) {
        this.id = id;
        this.deliveryOption = deliveryOption;
        this.paymentMethod = paymentMethod;
        this.invoice = invoice;
        this.sumPaid = sumPaid;
        this.state = state;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeliveryOption() {
        return deliveryOption;
    }

    public void setDeliveryOption(String deliveryOption) {
        this.deliveryOption = deliveryOption;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public boolean isInvoice() {
        return invoice;
    }

    public void setInvoice(boolean invoice) {
        this.invoice = invoice;
    }

    public double getSumPaid() {
        return sumPaid;
    }

    public void setSumPaid(double sumPaid) {
        this.sumPaid = sumPaid;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "CompleteOrderDTO{" +
                "id=" + id +
                ", deliveryOption='" + deliveryOption + '\'' +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", invoice=" + invoice +
                ", sumPaid=" + sumPaid +
                ", state='" + state + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
