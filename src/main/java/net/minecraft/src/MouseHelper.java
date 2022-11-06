package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.lax1dude.eaglercraft.EaglerAdapter;

public class MouseHelper {

	public void grabMouse() {
		EaglerAdapter.mouseSetGrabbed(true);
		deltaX = 0;
		deltaY = 0;
	}

	public void ungrabMouse() {
		EaglerAdapter.mouseSetCursorPosition(EaglerAdapter.getCanvasWidth() / 2, EaglerAdapter.getCanvasHeight() / 2);
		EaglerAdapter.mouseSetGrabbed(false);
	}

	public void mouseXYChange() {
		if(EaglerAdapter.isPointerLocked2()) {
			deltaX = EaglerAdapter.mouseGetDX();
			deltaY = EaglerAdapter.mouseGetDY();
		}else {
			deltaX = 0;
			deltaY = 0;
		}
	}
	
	public int deltaX;
	public int deltaY;
}
