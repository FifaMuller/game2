package game.engine.weapons;

import java.util.PriorityQueue;

import game.engine.interfaces.Attacker;

import game.engine.titans.Titan;

public class WallTrap extends Weapon implements Attacker
{
	public static final int WEAPON_CODE = 4;

	public WallTrap(int baseDamage)
	{
		super(baseDamage);
	}

	@Override
	public int turnAttack(PriorityQueue<Titan> laneTitans) {
		int rv = 0;
		if (laneTitans.isEmpty())
			return 0;
		else {
			if (laneTitans.peek().hasReachedTarget())
				rv = rv+attack(laneTitans.peek());
			return rv;
		}
	}

}
