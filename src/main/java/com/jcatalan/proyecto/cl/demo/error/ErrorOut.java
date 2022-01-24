package com.jcatalan.proyecto.cl.demo.error;

import java.util.List;

public class ErrorOut {
	private List<ErrorInfo> error;
	
	public ErrorOut(List<ErrorInfo>  err) {
	    this.error = err;
	}
	
	public List<ErrorInfo>  getError() {
		return error;
    }
}
