package com.application.base.codis.architecture.enumer;

import redis.clients.jedis.BinaryClient;

public enum ListPosition {

	BEFORE, AFTER;

	public BinaryClient.LIST_POSITION warp() {
		if (BEFORE.equals(this)) {
			return BinaryClient.LIST_POSITION.BEFORE;
		}
		return BinaryClient.LIST_POSITION.AFTER;
	}
}