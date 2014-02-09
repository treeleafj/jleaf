package org.jleaf.format.query;

/**
 * 代表条件
 * 
 * @author leaf
 * @date 2014-2-4 下午10:50:12
 */
public class Condition {

	private String key;

	private Object value;

	private Operator expression;

	public Condition(String key, Object value, Operator expression) {
		this.key = key;
		this.value = value;
		this.expression = expression;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public Operator getExpression() {
		return expression;
	}

	public void setExpression(Operator expression) {
		this.expression = expression;
	}

}
