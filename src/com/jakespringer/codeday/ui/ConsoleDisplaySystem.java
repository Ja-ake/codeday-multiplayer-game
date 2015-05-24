package com.jakespringer.codeday.ui;

import org.newdawn.slick.Color;

import com.jakespringer.engine.core.AbstractSystem;
import com.jakespringer.engine.core.Main;
import com.jakespringer.engine.graphics.Graphics2D;
import com.jakespringer.engine.graphics.RenderManagerComponent2D;
import com.jakespringer.engine.util.Color4d;
import com.jakespringer.engine.util.Vec2;

public class ConsoleDisplaySystem extends AbstractSystem {

	private CommandInputComponent cic;
	private RenderManagerComponent2D wc;

	public ConsoleDisplaySystem(CommandInputComponent c) {
		cic = c;
		wc = Main.gameManager.rmc2;
	}

	@Override
	public void update() {
		if (cic.visible) {
			Graphics2D.fillRect(new Vec2(wc.viewPos.x - wc.viewSize.x / 2, wc.viewPos.y + wc.viewSize.y / 2), new Vec2(wc.viewSize.x, -wc.viewSize.y / 2), new Color4d(0.3d, 0.3d, 0.3d, 0.5d));
			Graphics2D.drawText("> " + cic.current, "Default", new Vec2(wc.viewPos.x - wc.viewSize.x / 2 + 10.0d, wc.viewPos.y + 16), Color.white);

			for (int i = cic.history.size() - 1; i >= 0; i--) {
				Graphics2D.drawText(": " + cic.history.get(i), "Default", new Vec2(wc.viewPos.x - wc.viewSize.x / 2 + 10.0d, wc.viewPos.y + (double) (cic.history.size() - i) * 16.0d + 16), Color.white);
			}
		}
	}

}
