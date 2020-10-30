package com.webnobis.mastermind.model;

import javax.xml.bind.annotation.XmlEnum;

import com.webnobis.mastermind.service.AssessmentService;

/**
 * Result type as representation of same place or contained
 * 
 * @author steffen
 * @see AssessmentService#assess(Source, Source)
 */
@XmlEnum
public enum ResultType {

	/**
	 * Exact at same place
	 */
	EXACT,
	/**
	 * Contained
	 */
	PRESENT;

}
