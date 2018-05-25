package models.basket;

import com.sun.org.apache.bcel.internal.generic.NEW;
import com.sun.tools.javah.Gen;
import models.stock.Stock;

import models.users.Customer;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Tables;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="basket")
public class Basket {
    private int id;
    private List<Stock> stock;
    private Customer customer;


    public Basket() {
        this.stock = new ArrayList<>();
        this.customer = new Customer();
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

//    @ManyToMany(cascade = CascadeType.PERSIST)
////    @JoinTable(name="basket_stock",
////    inverseJoinColumns = {@JoinColumn(name="basket_id", nullable = false, updatable = false)},
////    joinColumns = {@JoinColumn(name="stock_id", nullable = false, updatable = false)})
    @OneToMany(mappedBy = "basket")

    public List<Stock> getStock() {
        return stock;
    }

    public void setStock(List<Stock> stock) {
        this.stock = stock;
    }

    public int stockCount() {
        return stock.size();
    }

    public void addStock(Stock stock, int quantity) {
        this.stock.add(new Stock(stock.getDescription(), stock.getType(), stock.getPrice(), quantity));
        stock.setQuantity(stock.getQuantity() - quantity);
    }

    public void removeStock(Stock originalStock) {
        int quantity = 0;
        ArrayList<Stock> copiedStock = new ArrayList<>(stock);
        for (Stock item : copiedStock) {
            if (originalStock.getDescription() == item.getDescription()) {
                quantity =  item.getQuantity();
                stock.remove(item);
            }
        }
        originalStock.setQuantity(originalStock.getQuantity() + quantity);
    }

//    public void removeStock(Stock stock) {
//
////        this.stock.remove(stock);
//    }

    public List<Stock> sell() {
        List<Stock> copy = new ArrayList<>(stock);
        this.stock.clear();
        return copy;
    }

    @OneToOne(mappedBy = "basket", cascade = CascadeType.PERSIST )
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
