package com.blockwars.game.entities.items;

import com.blockwars.game.entities.mobs.Player;
import com.blockwars.graphics.Sprite;
import com.blockwars.utils.Util;

public class HpPotion extends Item {

	public HpPotion(Sprite sprite, int x, int y) {
		super(sprite, x, y);
		setItemCode(Item.HP_POTION);
	}

	public HpPotion(Sprite sprite, int x, int y, Object[] objs) {
		this(sprite, x, y);
		this.objs = objs;
	}

	public HpPotion(Sprite sprite, double id, int x, int y, Object[] objs) {
		super(sprite, id, x, y);
		setItemCode(Item.HP_POTION);
		this.objs = objs;
	}

	public void getEvent(Player player) {
		int healAmount = Util.toInt(objs[0]);
		if (player.currentHealth + healAmount > player.maxHealth) {
			player.currentHealth = player.maxHealth;
		} else {
			player.currentHealth += healAmount;
		}
		remove();
	}

}
