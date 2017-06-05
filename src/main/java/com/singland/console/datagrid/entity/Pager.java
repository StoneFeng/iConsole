package com.singland.console.datagrid.entity;

import java.util.Collections;
import java.util.List;

public class Pager<T> {

	protected List<T> rows = Collections.emptyList();

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

}
