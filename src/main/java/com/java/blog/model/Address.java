package com.java.blog.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

@Embeddable
public class Address {

	private String lineOne;
	private String lineTwo;
	private String lineThree;
	private String lineFour;
	private String postCode;

	@Column(name = "addr_line_one", length = 50)
	public String getLineOne() {
		return lineOne;
	}

	public void setLineOne(String lineOne) {
		this.lineOne = lineOne;
	}

	@Column(name = "addr_line_two", length = 50)
	public String getLineTwo() {
		return lineTwo;
	}

	public void setLineTwo(String lineTwo) {
		this.lineTwo = lineTwo;
	}

	@Column(name = "addr_line_three", length = 50)
	public String getLineThree() {
		return lineThree;
	}

	public void setLineThree(String lineThree) {
		this.lineThree = lineThree;
	}

	@Column(name = "addr_line_four", length = 50)
	public String getLineFour() {
		return lineFour;
	}

	public void setLineFour(String lineFour) {
		this.lineFour = lineFour;
	}

	@Column(name = "addr_postcode", length = 10)
	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	@Transient
	public List<String> getLines() {
		List<String> lines = new ArrayList<String>();
		if (lineOne != null) {
			lines.add(lineOne);
		}
		if (lineTwo != null) {
			lines.add(lineTwo);
		}
		if (lineThree != null) {
			lines.add(lineThree);
		}
		if (lineFour != null) {
			lines.add(lineFour);
		}
		return lines;
	}
}
