package com.blockwars.game.entities.items;

import com.blockwars.game.entities.mobs.Player;
import com.blockwars.game.weapon.Gun;
import com.blockwars.game.weapon.Weapon;
import com.blockwars.graphics.Sprite;
import com.blockwars.utils.Util;

public class Magazine extends Item {

	public Magazine(Sprite sprite, int x, int y) {
		super(sprite, x, y);
		setItemCode(Item.MAGAZINE);
	}

	public Magazine(Sprite sprite, int x, int y, Object[] objs) {
		this(sprite, x, y);
		this.objs = objs;
	}

	public Magazine(Sprite sprite, double id, int x, int y, Object[] objs) {
		super(sprite, id, x, y);
		setItemCode(Item.MAGAZINE);
		this.objs = objs;
	}

	public void getEvent(Player player) {
		if (!player.dummy) {
			int amount = Util.toInt(objs[0]);
			Weapon weapon = player.getWeapon();
			if (weapon instanceof Gun) {
				if (((Gun) weapon).currentCapacity + ((Gun) weapon).magazineCapacity * amount > ((Gun) weapon).maxCapacity) {
					((Gun) weapon).currentCapacity = ((Gun) weapon).maxCapacity;
				} else {
					((Gun) weapon).currentCapacity += ((Gun) weapon).magazineCapacity * amount;
				}
			}
		}
		remove();
	}

}
