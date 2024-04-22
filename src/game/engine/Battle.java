package game.engine;


import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

import game.engine.base.Wall;
import game.engine.dataloader.DataLoader;
import game.engine.exceptions.InsufficientResourcesException;
import game.engine.exceptions.InvalidLaneException;
import game.engine.lanes.Lane;
import game.engine.titans.Titan;
import game.engine.titans.TitanRegistry;
import game.engine.weapons.Weapon;
import game.engine.weapons.WeaponRegistry;
import game.engine.weapons.factory.FactoryResponse;
import game.engine.weapons.factory.WeaponFactory;

public class Battle
{
	private static final int[][] PHASES_APPROACHING_TITANS =
	{
		{ 1, 1, 1, 2, 1, 3, 4 },
		{ 2, 2, 2, 1, 3, 3, 4 },
		{ 4, 4, 4, 4, 4, 4, 4 } 
	}; // order of the types of titans (codes) during each phase
	private static final int WALL_BASE_HEALTH = 10000;

	private int numberOfTurns;
	private int resourcesGathered;
	private BattlePhase battlePhase;
	private int numberOfTitansPerTurn; // initially equals to 1
	private int score; // Number of Enemies Killed
	private int titanSpawnDistance;
	private final WeaponFactory weaponFactory;
	private final HashMap<Integer, TitanRegistry> titansArchives;
	private final ArrayList<Titan> approachingTitans; // treated as a Queue
	private final PriorityQueue<Lane> lanes;
	private final ArrayList<Lane> originalLanes;

	public Battle(int numberOfTurns, int score, int titanSpawnDistance, int initialNumOfLanes,
			int initialResourcesPerLane) throws IOException
	{
		super();
		this.numberOfTurns = numberOfTurns;
		this.battlePhase = BattlePhase.EARLY;
		this.numberOfTitansPerTurn = 1;
		this.score = score;
		this.titanSpawnDistance = titanSpawnDistance;
		this.resourcesGathered = initialResourcesPerLane * initialNumOfLanes;
		this.weaponFactory = new WeaponFactory();
		this.titansArchives = DataLoader.readTitanRegistry();
		this.approachingTitans = new ArrayList<Titan>();
		this.lanes = new PriorityQueue<>();
		this.originalLanes = new ArrayList<>();
		this.initializeLanes(initialNumOfLanes);
	}

	public int getNumberOfTurns()
	{
		return numberOfTurns;
	}

	public void setNumberOfTurns(int numberOfTurns)
	{
		this.numberOfTurns = numberOfTurns;
	}

	public int getResourcesGathered()
	{
		return resourcesGathered;
	}

	public void setResourcesGathered(int resourcesGathered)
	{
		this.resourcesGathered = resourcesGathered;
	}

	public BattlePhase getBattlePhase()
	{
		return battlePhase;
	}

	public void setBattlePhase(BattlePhase battlePhase)
	{
		this.battlePhase = battlePhase;
	}

	public int getNumberOfTitansPerTurn()
	{
		return numberOfTitansPerTurn;
	}

	public void setNumberOfTitansPerTurn(int numberOfTitansPerTurn)
	{
		this.numberOfTitansPerTurn = numberOfTitansPerTurn;
	}

	public int getScore()
	{
		return score;
	}

	public void setScore(int score)
	{
		this.score = score;
	}

	public int getTitanSpawnDistance()
	{
		return titanSpawnDistance;
	}

	public void setTitanSpawnDistance(int titanSpawnDistance)
	{
		this.titanSpawnDistance = titanSpawnDistance;
	}

	public WeaponFactory getWeaponFactory()
	{
		return weaponFactory;
	}

	public HashMap<Integer, TitanRegistry> getTitansArchives()
	{
		return titansArchives;
	}

	public ArrayList<Titan> getApproachingTitans()
	{
		return approachingTitans;
	}

	public PriorityQueue<Lane> getLanes()
	{
		return lanes;
	}

	public ArrayList<Lane> getOriginalLanes()
	{
		return originalLanes;
	}

	private void initializeLanes(int numOfLanes)
	{
		for (int i = 0; i < numOfLanes; i++)
		{
			Wall w = new Wall(WALL_BASE_HEALTH);
			Lane l = new Lane(w);

			this.getOriginalLanes().add(l);
			this.getLanes().add(l);
		}
	}
	public  void refillApproachingTitans() {
		if(battlePhase == BattlePhase.EARLY) {
			int[] p1 = new int[7];
			for (int i = 0; i<7; i++) {
				p1[i]=PHASES_APPROACHING_TITANS[0][i];
			}
			for(int i=0;i<7;i++) {
				TitanRegistry titanRegistry = titansArchives.get(p1[i]);
				if(titanRegistry!=null) {
					int d = titanSpawnDistance;
					Titan t = titanRegistry.spawnTitan(d);
					approachingTitans.add(t);
			}
			
			
		}
			
		}
		if(battlePhase == BattlePhase.INTENSE) {
			int[] p1 = new int[7];
			for (int i = 0; i<7; i++) {
				p1[i]=PHASES_APPROACHING_TITANS[1][i];
			}
			for(int i=0;i<7;i++) {
				TitanRegistry titanRegistry = titansArchives.get(p1[i]);
				if(titanRegistry!=null) {
					int d = titanSpawnDistance;
					Titan t = titanRegistry.spawnTitan(d);
					approachingTitans.add(t);
			}
			
			
		}
			
		}
		if(battlePhase == BattlePhase.GRUMBLING) {
			int[] p1 = new int[7];
			for (int i = 0; i<7; i++) {
				p1[i]=PHASES_APPROACHING_TITANS[2][i];
			}
			for(int i=0;i<7;i++) {
				TitanRegistry titanRegistry = titansArchives.get(p1[i]);
				if(titanRegistry!=null) {
					int d = titanSpawnDistance;
					Titan t = titanRegistry.spawnTitan(d);
					approachingTitans.add(t);
			}
			
			
		}
			
		}
	}
	public  void purchaseWeapon(int weaponCode, Lane lane) throws InsufficientResourcesException, InvalidLaneException {
		if(!lanes.contains(lane)) {
			throw new InvalidLaneException();
		}
		if (lane.isLaneLost()) {
			throw new InvalidLaneException();
			
		}
		
		FactoryResponse wp = weaponFactory.buyWeapon(resourcesGathered, weaponCode);
		resourcesGathered = wp.getRemainingResources();
		lane.addWeapon(wp.getWeapon());
		performTurn();
		}
	public void passTurn() {
		 performTurn();
	}
	 private void addTurnTitansToLane() {

		 for(int i =0; i < numberOfTitansPerTurn; i++) {
			 if(approachingTitans.isEmpty()) {
				 refillApproachingTitans();
			 }
			 lanes.peek().addTitan(approachingTitans.remove(0));
			 
		 }
		
		}
	 
	 private void moveTitans() {
		 PriorityQueue<Lane> pq = new PriorityQueue<Lane>();
		 while(!lanes.isEmpty()) {
			 lanes.peek().moveLaneTitans();
			 pq.add(lanes.poll());
		 }
		 while(!pq.isEmpty()) {
			 lanes.add(pq.poll());
		 }
	 }
	 private int performWeaponsAttacks() {
		 PriorityQueue<Lane> pq = new PriorityQueue<Lane>();
		 int rv = 0;
		 if(lanes.isEmpty())
			 return rv;
		 else {
			 while(!lanes.isEmpty()) {
				rv = rv + lanes.peek().performLaneWeaponsAttacks();
				 pq.add(lanes.poll());
			 }
			 while(!pq.isEmpty()) {
				 lanes.add(pq.poll());
			 }
			 
		 }
		 resourcesGathered += rv; 
		 score += rv;
		 return rv;
			 
		 }
	 private int performTitansAttacks() {
		 PriorityQueue<Lane> pq = new PriorityQueue<Lane>();
		 int rv = 0;
		 if(lanes.isEmpty())
			 return rv;
		 else {
			 while(!lanes.isEmpty()) {
				rv = rv + lanes.peek().performLaneTitansAttacks();
				if(lanes.peek().isLaneLost())
					lanes.poll();
				else
					pq.add(lanes.poll());
				 
			 }
			 while(!pq.isEmpty()) {
				 lanes.add(pq.poll());
			 }
			 
		 }
		 return rv;
	 }
	 private void updateLanesDangerLevels() {
		 PriorityQueue<Lane> pq = new PriorityQueue<Lane>();
		 while(!lanes.isEmpty()) {
			 lanes.peek().updateLaneDangerLevel();
			 pq.add(lanes.poll());
		 }
		 while(!pq.isEmpty()) {
			 lanes.add(pq.poll());
		 }
	 }
	 private void finalizeTurns() {
		 numberOfTurns++;
		 if(numberOfTurns<15) {
			 battlePhase = BattlePhase.EARLY;
		 }
		 if(numberOfTurns<30 && numberOfTurns>=15) {
			 battlePhase = BattlePhase.INTENSE;
		 }
		 if(numberOfTurns>=30) {
			 battlePhase = BattlePhase.GRUMBLING;
		 }
		 if(numberOfTurns>30 &&  numberOfTurns%5 == 0) {
			 numberOfTitansPerTurn = numberOfTitansPerTurn *2;
		 }
		 
		 
		 
		 
	 }
	 private void performTurn() {
		 
		 moveTitans();
		 performWeaponsAttacks();
		 performTitansAttacks();
		 addTurnTitansToLane();
		 updateLanesDangerLevels();
		 finalizeTurns();
	 }
	 public boolean isGameOver() {
		 PriorityQueue<Lane> pq = new PriorityQueue<Lane>();
		 boolean gameOver = true;
		 while(!lanes.isEmpty()) {
			 if(!lanes.peek().isLaneLost()) {
				 gameOver = false;
				 break;
			 }
			 else {
				 pq.add(lanes.poll());
				 
			 }
		 }
		 while(!pq.isEmpty()) {
			 lanes.add(pq.poll());
		 }
		 return gameOver;
		 
	 }
	 
	 
	 	
}