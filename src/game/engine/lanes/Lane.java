package game.engine.lanes;

import java.util.ArrayList;
import java.util.PriorityQueue;

import game.engine.base.Wall;
import game.engine.titans.Titan;
import game.engine.weapons.Weapon;

public class Lane implements Comparable<Lane>
{
	private final Wall laneWall;
	private int dangerLevel;
	private final PriorityQueue<Titan> titans;
	private final ArrayList<Weapon> weapons;

	public Lane(Wall laneWall)
	{
		super();
		this.laneWall = laneWall;
		this.dangerLevel = 0;
		this.titans = new PriorityQueue<>();
		this.weapons = new ArrayList<>();
	}

	public Wall getLaneWall()
	{
		return this.laneWall;
	}

	public int getDangerLevel()
	{
		return this.dangerLevel;
	}

	public void setDangerLevel(int dangerLevel)
	{
		this.dangerLevel = dangerLevel;
	}

	public PriorityQueue<Titan> getTitans()
	{
		return this.titans;
	}

	public ArrayList<Weapon> getWeapons()
	{
		return this.weapons;
	}

	@Override
	public int compareTo(Lane o)
	{
		return this.dangerLevel - o.dangerLevel;
	}
	public void addTitan(Titan titan) {
		titans.add(titan);
	}
	public void addWeapon(Weapon weapon) {
		weapons.add(weapon);
	}
	public void moveLaneTitans() {
		PriorityQueue<Titan> pq = new PriorityQueue<Titan>();
		while(!titans.isEmpty()) {
			if (titans.peek().hasReachedTarget())
				pq.add(titans.poll());
			else {
				titans.peek().move();
				pq.add(titans.poll());
			}
		}
		while(!pq.isEmpty()) {
				titans.add(pq.poll());
			}
		}
	public int performLaneTitansAttacks() {
		PriorityQueue<Titan> pq = new PriorityQueue<Titan>();
		int rv = 0;
		if (titans.isEmpty())
			return 0;
		else {
			while(!titans.isEmpty()) {
				if(titans.peek().hasReachedTarget()) rv = rv + titans.peek().attack(laneWall);					
				pq.add(titans.poll());
			}
			while (!pq.isEmpty()) {
				titans.add(pq.poll());
			}
			return rv;
		}
		

	}
	public int performLaneWeaponsAttacks() {
		int rv = 0;
		if(weapons.isEmpty()) {
			return 0;
		}
		else {
			int size = weapons.size();
			for(int i = 0; i<size ; i++ ) {
				rv = rv + weapons.get(i).turnAttack(titans);
			}
		return rv;
		}
	}
	public boolean isLaneLost() {
		return laneWall.isDefeated();
	}
	public void updateLaneDangerLevel() {
		int dl = 0;
		PriorityQueue<Titan> pq = new PriorityQueue<Titan>();
		while(!titans.isEmpty()) {
			dl = dl + titans.peek().getDangerLevel();
			pq.add(titans.poll());
		}
		while(!pq.isEmpty())
			titans.add(pq.poll());
		setDangerLevel(dl);
	}
	

}
