package org.shop.controller;

import org.jleaf.db.controller.SenchaRestfulCrudController;
import org.jleaf.format.query.QueryObject;
import org.jleaf.web.annotation.Control;
import org.shop.domain.Product;
import org.shop.service.impl.ProductServiceImpl;

@Control
@SuppressWarnings("serial")
public class ProductController extends SenchaRestfulCrudController<Product, ProductServiceImpl, QueryObject>{

}
