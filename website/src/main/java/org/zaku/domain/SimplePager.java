package org.zaku.domain;

public class SimplePager implements Pageable {
	private int skip;
	private int amount;
	
	public SimplePager(int _skip, int _amount){
		this.skip=_skip;
		this.amount=_amount;
	}
	@Override
	public int getSkip() {
		return skip;
	}
	@Override
	public int getAmount() {
		return amount;
	}

}
