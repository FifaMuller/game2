package game.engine.weapons;

import game.engine.titans.AbnormalTitan;
import game.engine.titans.ArmoredTitan;
import game.engine.titans.ColossalTitan;
import game.engine.titans.PureTitan;
import game.engine.titans.Titan;

public class WeaponRegistry
{
	private final int code;
	private int price;
	private int damage;
	private String name;
	private int minRange;
	private int maxRange;

	public WeaponRegistry(int code, int price)
	{
		super();
		this.code = code;
		this.price = price;
	}

	public WeaponRegistry(int code, int price, int damage, String name)
	{
		super();
		this.code = code;
		this.price = price;
		this.damage = damage;
		this.name = name;
	}

	public WeaponRegistry(int code, int price, int damage, String name, int minRange, int maxRange)
	{
		super();
		this.code = code;
		this.price = price;
		this.damage = damage;
		this.name = name;
		this.minRange = minRange;
		this.maxRange = maxRange;
	}

	public int getCode()
	{
		return code;
	}

	public int getPrice()
	{
		return price;
	}

	public int getDamage()
	{
		return damage;
	}

	public String getName()
	{
		return name;
	}

	public int getMinRange()
	{
		return minRange;
	}

	public int getMaxRange()
	{
		return maxRange;
	}
	public Weapon buildWeapon() 
	{
		Weapon w = null;
		switch(code) {
		case 1:
			w = new PiercingCannon(damage);
			break;
		case 2:
			w = new SniperCannon(damage);
			break;
		case 3:
			w = new VolleySpreadCannon(damage,minRange,maxRange);
			break;
		case 4:
			w = new WallTrap(damage);
			break;
		}
		
		return w;
	}
}
