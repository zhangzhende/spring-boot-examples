package com.zzd.spring.mongodb.model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/4/24 0024.
 */
//这个注解意味着查询的时候默认指定集合，就不用再查询的时候去指定集合了
@Document(collection = "book")
public class Book implements Serializable{

    private static final long serialVersionUID = -1726309217938867449L;
    private String Name;

    private Double price;

    @Override
    public String toString() {
        return "Book{" +
                "Name='" + Name + '\'' +
                ", price=" + price +
                '}';
    }

    public Book(String name, Double price) {
        Name = name;
        this.price = price;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
