package com.easzz.corejava.pattern.proxy;

/**
 * Created by easzz on 2017/8/16 11:15
 */
public class PlayProxy implements IPlay {
	private IPlay iPlay;

	public PlayProxy(IPlay _iPlay) {
		this.iPlay = _iPlay;
	}

	@Override
	public void play() {
		iPlay.play();
	}
}
