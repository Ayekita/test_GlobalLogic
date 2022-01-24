package com.jcatalan.proyecto.cl.demo.error;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ErrorInfo {
	
	private Date timestamp;
	private int code;
    private List<String> detail;

    public ErrorInfo(int statusCode, List<String> detail) {
        Calendar calendar = Calendar.getInstance();
		this.timestamp = calendar.getTime();
    	this.code = statusCode;
        this.detail = detail;
    }

    public List<String> getDetail() {
        return detail;
    }

    public int getCode() {
        return code;
    }

    public Date getTimestamp() {
        return timestamp;
    }

}
