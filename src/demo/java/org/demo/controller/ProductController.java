package org.demo.controller;

import org.demo.entity.Product;
import org.demo.query.ProductQuery;
import org.demo.service.impl.ProductServiceImpl;
import org.jleaf.db.controller.CrudController;
import org.jleaf.web.annotation.Control;

@Control
public class ProductController extends CrudController<Product, ProductServiceImpl, ProductQuery> {

}
