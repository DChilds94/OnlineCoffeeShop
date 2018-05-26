package models.basket;

import com.sun.org.apache.bcel.internal.generic.NEW;
import com.sun.tools.javah.Gen;
import db.DBHelper;
import models.stock.Stock;

import models.users.Customer;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Tables;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="baskets")
public class Basket {

    private int id;
    private List<Stock> stock;
    private Customer customer;

    public Basket() {
        this.stock = new ArrayList<>();
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

    public void addStock(Stock stock) {
        this.stock.add(stock);
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

    public ArrayList<Stock> sell() {
        ArrayList<Stock> copy = new ArrayList<>(stock);
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
