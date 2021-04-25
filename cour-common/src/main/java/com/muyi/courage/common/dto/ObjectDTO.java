package com.muyi.courage.common.dto;

import com.muyi.courage.common.util.RetCodeEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ObjectDTO extends DTO {
	
	private static final long serialVersionUID = 1L;
	
	private Object element;

	public ObjectDTO() {
		super();
	}

	public ObjectDTO(RetCodeEnum e) {
		super(e);
	}

	public ObjectDTO(String retCode, String retMsg) {
		super(retCode, retMsg);
	}

	public Object getElement() {
		return element;
	}

	public void setElement(Object element) {
		this.element = element;
	}
}
