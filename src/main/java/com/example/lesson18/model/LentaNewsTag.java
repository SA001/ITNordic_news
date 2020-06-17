package com.example.lesson18.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter; 
import lombok.ToString;

@AllArgsConstructor 
@NoArgsConstructor
@Getter 
@Setter 
@ToString 
@JsonIgnoreProperties(ignoreUnknown = true)

public class LentaNewsTag {

	private String type;

}	
