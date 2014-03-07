package org.demo.query;

import org.jleaf.format.query.Operator;
import org.jleaf.format.query.QueryObject;

public class ProductQuery extends QueryObject {

    private String id;

    private String name;

    private Double price;

    @Override
    public void init() {
        if (id != null) {
            this.addCondition("id", id, Operator.EQUAL);
        }

        if (price != null) {
            this.addCondition("price", price, Operator.EQUAL);
        }

        if (name != null) {
            this.addCondition("name", VAGUE + name + VAGUE, Operator.LIKE);
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

}
