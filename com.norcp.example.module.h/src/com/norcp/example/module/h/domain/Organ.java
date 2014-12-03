package com.norcp.example.module.h.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity( name="Organ" )
@Table( name="Table_Organ" )
public class Organ implements Serializable {

	public Organ() {
	}

	private int id;
	
	private String name;

	@Id
	@Column( name="id" )
	@GeneratedValue( strategy=GenerationType.IDENTITY )
	public int getId(){
	    return this.id;
	}

	public void setId(int id){
	    this.id=id;
	}

	@Column( name="name")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
