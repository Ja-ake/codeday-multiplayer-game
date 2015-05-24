package com.jakespringer.codeday.ui;

import com.jakespringer.engine.core.AbstractSystem;
import com.jakespringer.engine.core.Main;
import com.jakespringer.engine.graphics.RenderManagerComponent2D;

public class TitleScreenSystem extends AbstractSystem {

	private RenderManagerComponent2D rmc;
	
	public TitleScreenSystem() {
		rmc = Main.gameManager.rmc2;
	}
	
	@Override
	public void update() {
		
	}
}
