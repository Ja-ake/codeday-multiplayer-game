package com.jakespringer.codeday.ui;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.jakespringer.engine.core.AbstractComponent;
import com.jakespringer.engine.core.AbstractEntity;

public class CommandInputComponent extends AbstractComponent {
	public List<String> history;
	public String current;
	public boolean shift;
	public boolean visible;
	
	public CommandInputComponent() {
		history = new LinkedList<String>();
		current = "";
		shift = false;
		visible = false;
	}
}
