package game.engine.weapons;

import java.util.PriorityQueue;

import game.engine.interfaces.Attacker;
import game.engine.interfaces.Mobil;
import game.engine.titans.Titan;

public class PiercingCannon extends Weapon implements Attacker
{
	public static final int WEAPON_CODE = 1;

	public PiercingCannon(int baseDamage)
	{
		super(baseDamage);
	}
	public int turnAttack(PriorityQueue<Titan> laneTitans) {
		int rv = 0;
		if(laneTitans.isEmpty())
			return 0;
		else {
			int size = laneTitans.size();
			PriorityQueue<Titan> pq = new PriorityQueue<Titan>();
			if(size > 5) {
				
				for(int i = 0; i<5;i++) {
					rv = rv+ attack(laneTitans.peek());
					pq.add(laneTitans.poll());
				}
				for(int i = 0; i<5;i++) {
					if (pq.peek().isDefeated())
						pq.poll();
					else
						laneTitans.add(pq.poll());
						
				}
				
					
			}
			else{
				
				for(int i = 0; i<size;i++) {
					rv = rv + attack(laneTitans.peek());
					pq.add(laneTitans.poll());
				}
				while(!pq.isEmpty()) {
					if (pq.peek().isDefeated()) {
						pq.poll();
					}
					else {
						laneTitans.add(pq.poll());
					}
				}
				
		

			
		}
			return rv;
		}
		

		
	}

}
