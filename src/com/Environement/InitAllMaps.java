package com.Environement;

import com.raylib.Rectangle;
import com.raylib.Vector2;

import com.MapsBuild.Cyty_01;
import com.MapsBuild.Donjon_outside;

import java.util.ArrayList;

public class InitAllMaps
{
	public InitAllMaps()
	{
		
	}

	public static GameMap initAll(ArrayList<GameMap> allMaps)
	{
		CollisionMap collisionMap = new CollisionMap(new Vector2(63, 63), new Vector2(64, 64), 2);
		
		GameMap donjon_outside = new Donjon_outside(
			"Donjon_outside",
			new Vector2(72, 72),
			"assets/Environement/Dungeon_Outside/Donjon_Entry.png",
			null,
			null,
			null,
			null,
			null,
			new Rectangle(2000, 2000, 1728, 1728),
			new Vector2(56, 56),
			2.333333333333337f,
			collisionMap
		);
		donjon_outside.collisionMap.printMap();
		allMaps.add(donjon_outside);
			
		CollisionMap collisionMap2 = new CollisionMap(new Vector2(48, 48), new Vector2(64, 64), 2);

		GameMap cyty_01 = new Cyty_01(
			"Cyty_01",
			new Vector2(48, 48),
			"assets/Environement/City_01/town_01.png",
			null,
			null,
			"assets/Environement/City_01/town_01_top_player.png",
			null,
			null,
			new Rectangle(0, 0, 1536, 1536),
			new Vector2(64, 64),
			2,
			collisionMap2
		);
		cyty_01.collisionMap.printMap();

		allMaps.add(cyty_01);

		return cyty_01;
	}
}
