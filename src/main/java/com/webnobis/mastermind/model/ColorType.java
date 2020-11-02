package com.webnobis.mastermind.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

import com.webnobis.mastermind.view.MastermindDialog;

/**
 * Enum of colors, used by graphical implementation of the game
 * 
 * @author steffen
 * 
 * @see MastermindDialog#start(javafx.stage.Stage)
 */
@XmlType
@XmlEnum
public enum ColorType {

	BLACK, WHITE, YELLOW, ORANGE, RED, GREEN, BLUE, VIOLET, HOLE;

}
