package com.appcloud.vm.fe.model;

public class FilterBean {

	private String name;
	private Object value;
	private FilterType type;
	
	public FilterBean(String name, Object value, FilterType type) {
		this.name = name;
		this.value = value;
		this.type = type;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public FilterType getType() {
		return type;
	}

	public void setType(FilterType type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "FilterBean [name=" + name + ", value=" + value + ", type="
				+ type + "]";
	}

	public enum FilterType{
		EQUAL,
		NOTEQUAL,
		LESS_THAN,
		MORE_THAN,
		LESS_EQUAL,
		MORE_EQUAL,
		IN,
		LIKE;
		
		public String constrain (String name) {
			switch(this) {
			case EQUAL:
				return " = :" + name; 
			case NOTEQUAL: 
				return " <> :" + name;
			case LESS_THAN:
				return " < :" + name;
			case MORE_THAN: 
				return " > :"  + name; 
			case LESS_EQUAL:
				return " <= :" + name;
			case MORE_EQUAL:
				return " >= :" + name;
			case IN:
				return " in ( :"  + name  + ")"; 
			case LIKE: 
				return" like :" + name; 
			}
			return super.toString();
		}
	}
}
