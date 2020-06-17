package com.example.lesson18.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

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

public class LentaNews {
	private String type;
	private LentaNewsInfo info;
	private LentaNewsInfo modified;
	private LentaNewsLink links; 
	private LentaNewsRubric rubric;
	private List<LentaNewsTag> tags;
	
	@JsonProperty("title_image")
	private LentaNewsImage titleImage;
}