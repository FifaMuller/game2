package game.tests;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.PriorityQueue;

import org.junit.Test;

public class Milestone2PublicTestsTracing{
	
	private String weaponPath = "game.engine.weapons.Weapon";
	private String weaponPiercingCannonPath = "game.engine.weapons.PiercingCannon";
	private String factoryResponsePath = "game.engine.weapons.factory.FactoryResponse";
	private String weaponFactoryPath = "game.engine.weapons.factory.WeaponFactory";
	private String battlePath = "game.engine.Battle";

	private String titanRegistryPath="game.engine.titans.TitanRegistry";
	private String weaponRegistryPath="game.engine.weapons.WeaponRegistry";
	private String dataLoaderPath="game.engine.dataloader.DataLoader";


	private String wallPath = "game.engine.base.Wall";
	private String lanePath = "game.engine.lanes.Lane";

	private String attackeePath = "game.engine.interfaces.Attackee";
	private String attackerPath = "game.engine.interfaces.Attacker";
	private String mobilPath = "game.engine.interfaces.Mobil";

	private String battlePhasePath = "game.engine.BattlePhase";

	private String gameActionExceptionPath = "game.engine.exceptions.GameActionException";
	private String invalidLaneExceptionPath = "game.engine.exceptions.InvalidLaneException";
	private String insufficientResourcesExceptionPath = "game.engine.exceptions.InsufficientResourcesException";
	private String invalidCSVFormatExceptionPath = "game.engine.exceptions.InvalidCSVFormat";



	private String sniperCannonPath = "game.engine.weapons.SniperCannon";
	private String volleySpreadCannonPath = "game.engine.weapons.VolleySpreadCannon";
	private String wallTrapPath = "game.engine.weapons.WallTrap";

	private String titanClassPath = "game.engine.titans.Titan";
	private String PureTitanClassPath = "game.engine.titans.PureTitan";
	private String AbnormalTitanClassPath = "game.engine.titans.AbnormalTitan";
	private String ArmoredTitanClassPath = "game.engine.titans.ArmoredTitan";
	private String ColossalTitanClassPath = "game.engine.titans.ColossalTitan";

	private String titanPath = "game.engine.titans.Titan";
	private String pureTitanPath = "game.engine.titans.PureTitan";
	private String abnormalTitan = "game.engine.titans.AbnormalTitan";
	private String armoredTitan = "game.engine.titans.ArmoredTitan";
	private String colossalTitan = "game.engine.titans.ColossalTitan";
	
	@Test(timeout = 1000)
	public void testPurchaseWeaponPerformedTurnCalled() {

		try{
			int numberOfTurns = 14;
			int score = (int) (Math.random() * 20) + 1;
			int titanSpawnDistance = (int) (Math.random() * 20) + 1;
			int initialNumOfLanes = (int) (Math.random() * 200) + 1000;
			int initialResourcesPerLane = (int) (Math.random() * 200) + 1000;



			int weaponCode = (int) (Math.random() * 4) + 1;

			Class wallClass = Class.forName(wallPath);
			Constructor<?> wallConstructor = Class.forName(wallPath).getConstructor(int.class);
			Object wall = wallConstructor.newInstance(10000);

			Class laneClass = Class.forName(lanePath);
			Constructor<?> laneConstructor = Class.forName(lanePath).getConstructor(wallClass);
			Object lane = laneConstructor.newInstance(wall);

			Constructor<?> constructor = Class.forName(AbnormalTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
			Object abnormalTitan1 =  constructor.newInstance(50,  20, 10, 0, 15, 15, 2);
			Method addT = laneClass.getDeclaredMethod("addTitan", Class.forName(titanClassPath));
			addT.invoke(lane, abnormalTitan1);


			Class battleClass = Class.forName(battlePath);
			Constructor<?> battleConstructor = Class.forName(battlePath).getConstructor(int.class, int.class, int.class, int.class, int.class);
			Object battle = battleConstructor.newInstance(numberOfTurns, score, titanSpawnDistance, initialNumOfLanes, initialResourcesPerLane);

			Method getLanes = battleClass.getDeclaredMethod("getLanes", null);
			PriorityQueue<Object> lanes = (PriorityQueue<Object>) getLanes.invoke(battle, null);
			lanes.add(lane);
			Field originalLanesField= Class.forName(battlePath).getDeclaredField("originalLanes");
			originalLanesField.setAccessible(true);
			ArrayList<Object> originalLanes= new ArrayList<>();

			originalLanes.addAll(lanes);
			originalLanesField.set(battle, originalLanes);
//			
			Class[] attributes = {int.class, laneClass};
			Method purchase_weapon = battleClass.getDeclaredMethod("purchaseWeapon", attributes);

			Method getBattlePhase = battleClass.getDeclaredMethod("getBattlePhase", null);

			purchase_weapon.invoke(battle, weaponCode, lane);

			Object getBattlePhaseActual = getBattlePhase.invoke(battle, null);
			System.out.println(getBattlePhaseActual);

			assertEquals("The battle phase should be " + getBattlePhaseActual, getBattlePhaseActual, (Enum.valueOf((Class<Enum>) Class.forName(battlePhasePath), "INTENSE")));
		}catch(InvocationTargetException e1) {
			e1.printStackTrace();
			fail("Incorrect weapon,Please check the console for the error");

		}
		catch(ClassNotFoundException|NoSuchMethodException| SecurityException| InstantiationException| IllegalAccessException| IllegalArgumentException e){
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}

	}

	
	private Object createAbnormalTitan() {
		try {
			int baseHealth = 250;
			int baseDamage = (int) (Math.random() * 100)+5;
			int heightInMeters = (int) (Math.random() * 5)+1;
			int distanceFromBase = (int) (Math.random() * 5)+30;
			int speed = (int) (Math.random() * 5)+1;
			int dangerLevel = (int) (Math.random() * 5)+1;
			int resourcesValue = (int) (Math.random() * 5)+1;

			Constructor<?> constructor = Class.forName(AbnormalTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
			Object abnormalTitan =  constructor.newInstance(baseHealth, baseDamage, heightInMeters, distanceFromBase,speed,resourcesValue,dangerLevel);
			return abnormalTitan;
		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
	
	private Object createAbnormalTitanFixed() {
		try {
			int baseHealth = 100;
			int baseDamage = 20;
			int heightInMeters =5;
			int distanceFromBase = 30;
			int speed = 5;
			int dangerLevel = 5;
			int resourcesValue = 10;

			Constructor<?> constructor = Class.forName(AbnormalTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
			Object abnormalTitan =  constructor.newInstance(baseHealth, baseDamage, heightInMeters, distanceFromBase,speed,resourcesValue,dangerLevel);
			return abnormalTitan;
		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
	
	private Object createWeaponVolleySpreadCannon() {
		try {
			int baseDamage = 20;
			int minDistance=0;
			int maxDistance=21;
			Constructor<?> constructor = Class.forName(volleySpreadCannonPath).getConstructor(int.class,int.class,int.class);
			Object piercingCannon =  constructor.newInstance(baseDamage,minDistance,maxDistance);

			return piercingCannon;
		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
	
	private Object createWall() {
		try {
			int baseHealth = 50;

			Constructor<?> constructor = Class.forName(wallPath).getConstructor(int.class);
			Object wall =  constructor.newInstance(baseHealth);

			return wall;
		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
	
}