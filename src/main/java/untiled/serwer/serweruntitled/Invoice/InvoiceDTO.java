package untiled.serwer.serweruntitled.Invoice;

import com.fasterxml.jackson.annotation.JsonIgnore;
import untiled.serwer.serweruntitled.ArtcilesOnInvoice.ArticlesOnInvoice;
import untiled.serwer.serweruntitled.Business.Business;
import untiled.serwer.serweruntitled.ProductBasket.ProductBasket;

import java.util.List;

public class InvoiceDTO {

    private Long id;
    private String recipient;
    private String payForm;
    private String date;
    private String paid;
    private String spendFromStock;
    private String paymentDeadline;
    private double sumVatValue;
    private double sumNettoValue;
    private double sumBruttoValue;
    private String businnesName;
    private String nip;
    private List<ProductBasket> productBaskets;
    public InvoiceDTO() {
    }

    public InvoiceDTO(Long id, String recipient, String payForm, String date, String paid, String spendFromStock, String paymentDeadline, double sumVatValue, double sumNettoValue, double sumBruttoValue, String businnesName, String nip, List<ProductBasket> productBaskets) {
        this.id = id;
        this.recipient = recipient;
        this.payForm = payForm;
        this.date = date;
        this.paid = paid;
        this.spendFromStock = spendFromStock;
        this.paymentDeadline = paymentDeadline;
        this.sumVatValue = sumVatValue;
        this.sumNettoValue = sumNettoValue;
        this.sumBruttoValue = sumBruttoValue;
        this.businnesName = businnesName;
        this.nip = nip;
        this.productBaskets = productBaskets;
    }

    public List<ProductBasket> getProductBaskets() {
        return productBaskets;
    }

    public void setProductBaskets(List<ProductBasket> productBaskets) {
        this.productBaskets = productBaskets;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getPayForm() {
        return payForm;
    }

    public void setPayForm(String payForm) {
        this.payForm = payForm;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPaid() {
        return paid;
    }

    public void setPaid(String paid) {
        this.paid = paid;
    }

    public String getSpendFromStock() {
        return spendFromStock;
    }

    public void setSpendFromStock(String spendFromStock) {
        this.spendFromStock = spendFromStock;
    }

    public String getPaymentDeadline() {
        return paymentDeadline;
    }

    public void setPaymentDeadline(String paymentDeadline) {
        this.paymentDeadline = paymentDeadline;
    }

    public double getSumVatValue() {
        return sumVatValue;
    }

    public void setSumVatValue(double sumVatValue) {
        this.sumVatValue = sumVatValue;
    }

    public double getSumNettoValue() {
        return sumNettoValue;
    }

    public void setSumNettoValue(double sumNettoValue) {
        this.sumNettoValue = sumNettoValue;
    }

    public double getSumBruttoValue() {
        return sumBruttoValue;
    }

    public void setSumBruttoValue(double sumBruttoValue) {
        this.sumBruttoValue = sumBruttoValue;
    }

    public String getBusinnesName() {
        return businnesName;
    }

    public void setBusinnesName(String businnesName) {
        this.businnesName = businnesName;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    @Override
    public String toString() {
        return "InvoiceDTO{" +
                "id=" + id +
                ", recipient='" + recipient + '\'' +
                ", payForm='" + payForm + '\'' +
                ", date='" + date + '\'' +
                ", paid='" + paid + '\'' +
                ", spendFromStock='" + spendFromStock + '\'' +
                ", paymentDeadline='" + paymentDeadline + '\'' +
                ", sumVatValue=" + sumVatValue +
                ", sumNettoValue=" + sumNettoValue +
                ", sumBruttoValue=" + sumBruttoValue +
                ", businnesName='" + businnesName + '\'' +
                ", nip='" + nip + '\'' +
                ", productBaskets=" + productBaskets +
                '}';
    }
}


