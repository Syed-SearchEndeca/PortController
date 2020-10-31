package com.srch.tools.main;

import javafx.scene.control.CheckBox;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor @Getter @Setter
@EqualsAndHashCode 
@ToString
public class ProcessKillVO {
	
	private String protocl;
	
	private String localAddress;
	
	private String stdError;
	
    private String state;
    
    private String port;
    
    private String pId;

    private CheckBox checkBox= new CheckBox();

}
