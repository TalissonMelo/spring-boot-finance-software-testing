package com.talissonmelo.finance.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
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

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "launch")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Launch implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "month")
	private Integer month;
	
	@Column(name = "year")
	private Integer year;
	
	@Column(name = "value")
	private Double value;

	@Column(name = "date")
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", timezone = "America/Sao_Paulo")
	private Date date;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "type")
	private TypeLaunch type;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "status")
	private StatusLaunch status;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
}