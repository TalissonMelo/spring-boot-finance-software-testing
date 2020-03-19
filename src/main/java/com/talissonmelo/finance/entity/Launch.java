package com.talissonmelo.finance.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.talissonmelo.finance.entity.enums.StatusLaunch;
import com.talissonmelo.finance.entity.enums.TypeLaunch;

@Entity
@Table(name = "launch")
public class Launch implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String description;
	private Integer month;
	private Integer year;
	private Double value;

	@JsonFormat(pattern = "dd/MM/yyyy", timezone = "America/Sao_Paulo")
	private Date date;
	
	@Enumerated(value = EnumType.STRING)
	private TypeLaunch type;
	
	@Enumerated(value = EnumType.STRING)
	private StatusLaunch status;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	public Launch() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public TypeLaunch getType() {
		return type;
	}

	public void setType(TypeLaunch type) {
		this.type = type;
	}

	public StatusLaunch getStatus() {
		return status;
	}

	public void setStatus(StatusLaunch status) {
		this.status = status;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}