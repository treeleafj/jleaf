package org.jleaf.format.query;

import java.util.ArrayList;
import java.util.List;

public class QueryObject extends PageObject{
	
	protected String orderBy = "id";
	protected String orderType = "DESC";
	protected List<Object> params = new ArrayList<Object>();

	protected StringBuilder queryString = new StringBuilder("1=1");

	public QueryObject setOrderBy(String orderBy) {
		this.orderBy = orderBy;
		return this;
	}

	public QueryObject setOrderType(String orderType) {
		this.orderType = orderType;
		return this;
	}


	protected QueryObject setParams(List<Object> params) {
		this.params = params;
		return this;
	}

	public String getOrderType() {
		return this.orderType;
	}

	public String getOrder() {
		return this.orderType;
	}

	public String getOrderBy() {
		return this.orderBy;
	}


	public String getQuery() {
		customizeQuery();
		if ((this.queryString.indexOf("1=1 and") == 0)
				&& (this.queryString.length() > "1=1 and ".length())) {
			this.queryString.delete(0, "1=1 and ".length());
		}
		return this.queryString + orderString();
	}

	protected String orderString() {
		StringBuilder orderString = new StringBuilder(" ");
		if ((getOrderBy() != null) && (!("".equals(getOrderBy())))) {
			orderString.append(" order by obj.").append(getOrderBy());
		}
		if ((getOrderType() != null) && (!("".equals(getOrderType())))) {
			orderString.append(" ").append(getOrderType());
		}
		return orderString.toString();
	}

	public List<Object> getParameters() {
		return this.params;
	}

	public QueryObject addQuery(String field, Object para, String expression) {
		if ((field != null) && (para != null)) {
			this.queryString.append(" and ").append(field).append(" ")
					.append(handleExpression(expression)).append(" ? ");
			this.params.add(para);
		}
		return this;
	}

	public QueryObject addQuery(String field, Object para, String expression,
			String logic) {
		if ((field != null) && (para != null)) {
			this.queryString.append(" ").append(logic).append(" ")
					.append(field).append(" ")
					.append(handleExpression(expression)).append(" ? ");
			this.params.add(para);
		}
		return this;
	}

	public QueryObject addQuery(String scope, Object[] paras) {
		if (scope != null) {
			this.queryString.append(" and ").append(scope);
			if ((paras != null) && (paras.length > 0)) {
				for (int i = 0; i < paras.length; ++i)
					this.params.add(paras[i]);
			}
		}
		return this;
	}

	public QueryObject addQuery(String scope) {
		addQuery(scope, null);
		return this;
	}

	private String handleExpression(String expression) {
		if (expression == null) {
			return "=";
		}
		return expression;
	}

	public void customizeQuery(){
		
	}
}
