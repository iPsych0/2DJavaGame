package dev.ipsych0.mygame.tiles;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import dev.ipsych0.mygame.gfx.Assets;

public class Tiles {
	
	// First try-out tiles
	
	public static Tiles[] tiles = new Tiles[4096];

	public static Tiles blackTile = new Tiles(Assets.black, 28);
	
	public static Tiles darkGrass = new Tiles(Assets.darkGrass, 352);
	public static Tiles darkGrassPatch1 = new Tiles(Assets.darkGrassPatch1, 415);
	public static Tiles darkGrassPatch2 = new Tiles(Assets.darkGrassPatch2, 416);
	public static Tiles darkGrassPatch3 = new Tiles(Assets.darkGrassPatch3, 417);

	// Terrain
	public static Tiles waterSmallTopLeft = new Tiles(Assets.waterSmallTopLeft, 85);
	public static Tiles waterSmallTopRight = new Tiles(Assets.waterSmallTopRight, 86);
	public static Tiles waterSmallBottomLeft = new Tiles(Assets.waterSmallBottomLeft, 117);
	public static Tiles waterSmallBottomRIght = new Tiles(Assets.waterSmallBottomRight, 118);
	public static Tiles waterTopLeft = new Tiles(Assets.waterTopLeft, 148);
	public static Tiles waterTopMiddle = new Tiles(Assets.waterTopMiddle, 149);
	public static Tiles waterTopRight = new Tiles(Assets.waterTopRight, 150);
	public static Tiles waterMiddleLeft = new Tiles(Assets.waterMiddleLeft, 180);
	public static Tiles waterMiddleMiddle = new Tiles(Assets.waterMiddleMiddle, 181);
	public static Tiles waterMiddleRight = new Tiles(Assets.waterMiddleRight, 182);
	public static Tiles waterBottomLeft = new Tiles(Assets.waterBottomLeft, 212);
	public static Tiles waterBottomMiddle = new Tiles(Assets.waterBottomMiddle, 213);
	public static Tiles waterBottomRight = new Tiles(Assets.waterBottomRight, 214);
	public static Tiles waterFlow1 = new Tiles(Assets.waterFlow1, 244);
	public static Tiles waterFlow2 = new Tiles(Assets.waterFlow2, 245);
	public static Tiles waterFlow3 = new Tiles(Assets.waterFlow3, 246);
	
	public static Tiles sandWaterSmallTopLeft = new Tiles(Assets.sandWaterSmallTopLeft, 271);
	public static Tiles sandWaterSmallTopRight = new Tiles(Assets.sandWaterSmallTopRight, 272);
	public static Tiles sandWaterSmallBottomLeft = new Tiles(Assets.sandWaterSmallBottomLeft, 303);
	public static Tiles sandWaterSmallBottomRight = new Tiles(Assets.sandWaterSmallBottomRight, 304);
	public static Tiles sandWaterTopLeft = new Tiles(Assets.sandWaterTopLeft, 334);
	public static Tiles sandWaterTopMiddle = new Tiles(Assets.sandWaterTopMiddle, 335);
	public static Tiles sandWaterTopRight = new Tiles(Assets.sandWaterTopRight, 336);
	public static Tiles sandWaterMiddleLeft = new Tiles(Assets.sandWaterMiddleLeft, 366);
	public static Tiles sandWaterMiddleMiddle = new Tiles(Assets.sandWaterMiddleMiddle, 367);
	public static Tiles sandWaterMiddleRight = new Tiles(Assets.sandWaterMiddleRight, 368);
	public static Tiles sandWaterBottomLeft = new Tiles(Assets.sandWaterBottomLeft, 398);
	public static Tiles sandWaterBottomMiddle = new Tiles(Assets.sandWaterBottomMiddle, 399);
	public static Tiles sandWaterBottomRight = new Tiles(Assets.sandWaterBottomRight, 400);
	
	public static Tiles lavaSmallTopLeft = new Tiles(Assets.lavaSmallTopLeft, 73);
	public static Tiles lavaSmallTopRight = new Tiles(Assets.lavaSmallTopRight, 74);
	public static Tiles lavaSmallBottomLeft = new Tiles(Assets.lavaSmallBottomLeft, 105);
	public static Tiles lavaSmallBottomRight = new Tiles(Assets.lavaSmallBottomRight, 106);
	public static Tiles lavaTopLeft = new Tiles(Assets.lavaTopLeft, 136);
	public static Tiles lavaTopMiddle = new Tiles(Assets.lavaTopMiddle, 137);
	public static Tiles lavaTopRight = new Tiles(Assets.lavaTopRight, 138);
	public static Tiles lavaMiddleLeft = new Tiles(Assets.lavaMiddleLeft, 168);
	public static Tiles lavaMiddleMiddle = new Tiles(Assets.lavaMiddleMiddle, 169);
	public static Tiles lavaMiddleRight = new Tiles(Assets.lavaMiddleRight, 170);
	public static Tiles lavaBottomLeft = new Tiles(Assets.lavaBottomLeft, 200);
	public static Tiles lavaBottomMiddle = new Tiles(Assets.lavaBottomMiddle, 201);
	public static Tiles lavaBottomRight = new Tiles(Assets.lavaBottomRight, 202);
	public static Tiles lavaFlow1 = new Tiles(Assets.lavaFlow1, 232);
	public static Tiles lavaFlow2 = new Tiles(Assets.lavaFlow2, 233);
	public static Tiles lavaFlow3 = new Tiles(Assets.lavaFlow3, 234);
	
	public static Tiles holeSmallTopLeft = new Tiles(Assets.holeSmallTopLeft, 76);
	public static Tiles holeSmallTopRight = new Tiles(Assets.holeSmallTopRight, 77);
	public static Tiles holeSmallBottomLeft = new Tiles(Assets.holeSmallBottomLeft, 108);
	public static Tiles holeSmallBottomRight = new Tiles(Assets.holeSmallBottomRight, 109);
	public static Tiles holeTopLeft = new Tiles(Assets.holeTopLeft, 139);
	public static Tiles holeTopMiddle = new Tiles(Assets.holeTopMiddle, 140);
	public static Tiles holeTopRight = new Tiles(Assets.holeTopRight, 141);
	public static Tiles holeMiddleLeft = new Tiles(Assets.holeMiddleLeft, 171);
	public static Tiles holeMiddleMiddle = new Tiles(Assets.holeMiddleMiddle, 172);
	public static Tiles holeMiddleRight = new Tiles(Assets.holeMiddleRight, 173);
	public static Tiles holeBottomLeft = new Tiles(Assets.holeBottomLeft, 203);
	public static Tiles holeBottomMiddle = new Tiles(Assets.holeBottomMiddle, 204);
	public static Tiles holeBottomRight = new Tiles(Assets.holeBottomRight, 205);
	
	public static Tiles transDirtTopLeft = new Tiles(Assets.transDirtTopLeft, 121);
	public static Tiles transDirtTopMiddle = new Tiles(Assets.transDirtTopMiddle, 122);
	public static Tiles transDirtTopRight = new Tiles(Assets.transDirtTopRight, 123);
	public static Tiles transDirtMiddleLeft = new Tiles(Assets.transDirtMiddleLeft, 153);
	public static Tiles transDirtMiddleMiddle = new Tiles(Assets.transDirtMiddleMiddle, 154);
	public static Tiles transDirtMiddleRight = new Tiles(Assets.transDirtMiddleRight, 155);
	public static Tiles transDirtBottomLeft = new Tiles(Assets.transDirtBottomLeft, 185);
	public static Tiles transDirtBottomMiddle = new Tiles(Assets.transDirtBottomMiddle, 186);
	public static Tiles transDirtBottomRight = new Tiles(Assets.transDirtBottomRight, 187);
	public static Tiles transDirtSmallTopLeft = new Tiles(Assets.transDirtSmallTopLeft, 58);
	public static Tiles transDirtSmallTopRight = new Tiles(Assets.transDirtSmallTopRight, 59);
	public static Tiles transDirtSmallBottomLeft = new Tiles(Assets.transDirtSmallBottomLeft, 90);
	public static Tiles transDirtSmallBottomRight = new Tiles(Assets.transDirtSmallBottomRight, 91);
	
	public static Tiles darkDirtTopLeft = new Tiles(Assets.darkDirtTopLeft, 124);
	public static Tiles darkDirtTopMiddle = new Tiles(Assets.darkDirtTopMiddle, 125);
	public static Tiles darkDirtTopRight = new Tiles(Assets.darkDirtTopRight, 126);
	public static Tiles darkDirtMiddleLeft = new Tiles(Assets.darkDirtMiddleLeft, 156);
	public static Tiles darkDirtMiddleMiddle = new Tiles(Assets.darkDirtMiddleMiddle, 157);
	public static Tiles darkDirtMiddleRight = new Tiles(Assets.darkDirtMiddleRight, 158);
	public static Tiles darkDirtBottomLeft = new Tiles(Assets.darkDirtBottomLeft, 188);
	public static Tiles darkDirtBottomMiddle = new Tiles(Assets.darkDirtBottomMiddle, 189);
	public static Tiles darkDirtBottomRight = new Tiles(Assets.darkDirtBottomRight, 190);
	public static Tiles darkDirtEffect1 = new Tiles(Assets.darkDirtEffect1, 220);
	public static Tiles darkDirtEffect2 = new Tiles(Assets.darkDirtEffect2, 221);
	public static Tiles darkDirtEffect3 = new Tiles(Assets.darkDirtEffect3, 222);
	public static Tiles darkDirtSmallTopLeft = new Tiles(Assets.darkDirtSmallTopLeft, 61);
	public static Tiles darkDirtSmallTopRight = new Tiles(Assets.darkDirtSmallTopRight, 62);
	public static Tiles darkDirtSmallBottomLeft = new Tiles(Assets.darkDirtSmallBottomLeft, 93);
	public static Tiles darkDirtSmallBottomRight = new Tiles(Assets.darkDirtSmallBottomRight, 94);


	public static Tiles greyDirtTopLeft = new Tiles(Assets.greyDirtTopLeft, 133);
	public static Tiles greyDirtTopMiddle = new Tiles(Assets.greyDirtTopMiddle, 134);
	public static Tiles greyDirtTopRight = new Tiles(Assets.greyDirtTopRight, 135);
	public static Tiles greyDirtMiddleLeft = new Tiles(Assets.greyDirtMiddleLeft, 165);
	public static Tiles greyDirtMiddleMiddle = new Tiles(Assets.greyDirtMiddleMiddle, 166);
	public static Tiles greyDirtMiddleRight = new Tiles(Assets.greyDirtMiddleRight, 167);
	public static Tiles greyDirtBottomLeft = new Tiles(Assets.greyDirtBottomLeft, 197);
	public static Tiles greyDirtBottomMiddle = new Tiles(Assets.greyDirtBottomMiddle, 198);
	public static Tiles greyDirtBottomRight = new Tiles(Assets.greyDirtBottomRight, 199);
	public static Tiles greyDirtEffect1 = new Tiles(Assets.greyDirtEffect1, 229);
	public static Tiles greyDirtEffect2 = new Tiles(Assets.greyDirtEffect2, 230);
	public static Tiles greyDirtEffect3 = new Tiles(Assets.greyDirtEffect3, 231);
	public static Tiles greyDirtSmallTopLeft = new Tiles(Assets.greyDirtSmallTopLeft, 70);
	public static Tiles greyDirtSmallTopRight = new Tiles(Assets.greyDirtSmallTopRight, 71);
	public static Tiles greyDirtSmallBottomLeft = new Tiles(Assets.greyDirtSmallBottomLeft, 102);
	public static Tiles greyDirtSmallBottomRight = new Tiles(Assets.greyDirtSmallBottomRight, 103);


	public static Tiles redDirtTopLeft = new Tiles(Assets.redDirtTopLeft, 127);
	public static Tiles redDirtTopMiddle = new Tiles(Assets.redDirtTopMiddle, 128);
	public static Tiles redDirtTopRight = new Tiles(Assets.redDirtTopRight, 129);
	public static Tiles redDirtMiddleLeft = new Tiles(Assets.redDirtMiddleLeft, 159);
	public static Tiles redDirtMiddleMiddle = new Tiles(Assets.redDirtMiddleMiddle, 160);
	public static Tiles redDirtMiddleRight = new Tiles(Assets.redDirtMiddleRight, 161);
	public static Tiles redDirtBottomLeft = new Tiles(Assets.redDirtBottomLeft, 191);
	public static Tiles redDirtBottomMiddle = new Tiles(Assets.redDirtBottomMiddle, 192);
	public static Tiles redDirtBottomRight = new Tiles(Assets.redDirtBottomRight, 193);
	public static Tiles redDirtEffect1 = new Tiles(Assets.redDirtEffect1, 223);
	public static Tiles redDirtEffect2 = new Tiles(Assets.redDirtEffect2, 224);
	public static Tiles redDirtEffect3 = new Tiles(Assets.redDirtEffect3, 225);
	public static Tiles redDirtSmallTopLeft = new Tiles(Assets.redDirtSmallTopLeft, 64);
	public static Tiles redDirtSmallTopRight = new Tiles(Assets.redDirtSmallTopRight, 65);
	public static Tiles redDirtSmallBottomLeft = new Tiles(Assets.redDirtSmallBottomLeft, 96);
	public static Tiles redDirtSmallBottomRight = new Tiles(Assets.redDirtSmallBottomRight, 97);
	
	public static Tiles snowSmallTopLeft = new Tiles(Assets.snowSmallTopLeft, 460);
	public static Tiles snowSmallTopRight = new Tiles(Assets.snowSmallTopRight, 461);
	public static Tiles snowSmallBottomLeft = new Tiles(Assets.snowSmallBottomLeft, 492);
	public static Tiles snowSmallBottomRight = new Tiles(Assets.snowSmallBottomRight, 493);
	public static Tiles snowTopLeft = new Tiles(Assets.snowTopLeft, 523);
	public static Tiles snowTopMiddle = new Tiles(Assets.snowTopMiddle, 524);
	public static Tiles snowTopRight = new Tiles(Assets.snowTopRight, 525);
	public static Tiles snowMiddleLeft = new Tiles(Assets.snowMiddleLeft, 555);
	public static Tiles snowMiddleMiddle = new Tiles(Assets.snowMiddleMiddle, 556);
	public static Tiles snowMiddleRight = new Tiles(Assets.snowMiddleRight, 557);
	public static Tiles snowBottomLeft = new Tiles(Assets.snowBottomLeft, 587);
	public static Tiles snowBottomMiddle = new Tiles(Assets.snowBottomMiddle, 588);
	public static Tiles snowBottomRight = new Tiles(Assets.snowBottomRight, 589);
	public static Tiles snowPattern1 = new Tiles(Assets.snowPattern1, 619);
	public static Tiles snowPattern2 = new Tiles(Assets.snowPattern2, 620);
	public static Tiles snowPattern3 = new Tiles(Assets.snowPattern3, 621);
	
	public static Tiles greenHouseRoof = new Tiles(Assets.greenHouseRoof, 409);
	public static Tiles greenHouseWall = new Tiles(Assets.greenHouseWall, 410);
	public static Tiles greenHouseEntrance = new Tiles(Assets.greenHouseEntrance, 411);
	
	public static Tiles sandSmallTopLeft = new Tiles(Assets.sandSmallTopLeft, 268);
	public static Tiles sandSmallTopRight = new Tiles(Assets.sandSmallTopRight, 269);
	public static Tiles sandSmallBottomLeft = new Tiles(Assets.sandSmallBottomLeft, 300);
	public static Tiles sandSmallBottomRight = new Tiles(Assets.sandSmallBottomRight, 301);
	public static Tiles sandTopLeft = new Tiles(Assets.sandTopLeft, 331);
	public static Tiles sandTopMiddle = new Tiles(Assets.sandTopMiddle, 332);
	public static Tiles sandTopRight = new Tiles(Assets.sandTopRight, 333);
	public static Tiles sandMiddleLeft = new Tiles(Assets.sandMiddleLeft, 363);
	public static Tiles sandMiddleMiddle = new Tiles(Assets.sandMiddleMiddle, 364);
	public static Tiles sandMiddleRight = new Tiles(Assets.sandMiddleRight, 365);
	public static Tiles sandBottomLeft = new Tiles(Assets.sandBottomLeft, 395);
	public static Tiles sandBottomMiddle = new Tiles(Assets.sandBottomMiddle, 396);
	public static Tiles sandBottomRight = new Tiles(Assets.sandBottomRight, 397);
	public static Tiles sandPattern1 = new Tiles(Assets.sandPattern1, 427);
	public static Tiles sandPattern2 = new Tiles(Assets.sandPattern2, 428);
	public static Tiles sandPattern3 = new Tiles(Assets.sandPattern3, 429);

	public static Tiles snowWaterSmallTopLeft = new Tiles(Assets.snowWaterSmallTopLeft, 623);
	public static Tiles snowWaterSmallTopRight = new Tiles(Assets.snowWaterSmallTopRight, 624);
	public static Tiles snowWaterSmallBottomLeft = new Tiles(Assets.snowWaterSmallBottomLeft, 655);
	public static Tiles snowWaterSmallBottomRight = new Tiles(Assets.snowWaterSmallBottomRight, 656);
	public static Tiles snowWaterTopLeft = new Tiles(Assets.snowWaterTopLeft, 686);
	public static Tiles snowWaterTopMiddle = new Tiles(Assets.snowWaterTopMiddle, 687);
	public static Tiles snowWaterTopRight = new Tiles(Assets.snowWaterTopRight, 688);
	public static Tiles snowWaterMiddleLeft = new Tiles(Assets.snowWaterMiddleLeft, 718);
	public static Tiles snowWaterMiddleMiddle = new Tiles(Assets.snowWaterMiddleMiddle, 719);
	public static Tiles snowWaterMiddleRight = new Tiles(Assets.snowWaterMiddleRight, 720);
	public static Tiles snowWaterBottomLeft = new Tiles(Assets.snowWaterBottomLeft, 750);
	public static Tiles snowWaterBottomMiddle = new Tiles(Assets.snowWaterBottomMiddle, 751);
	public static Tiles snowWaterBottomRight = new Tiles(Assets.snowWaterBottomRight, 752);
	
	public static Tiles lightGrass = new Tiles(Assets.lightGrass, 346);
	public static Tiles lightGrassPatch1 = new Tiles(Assets.lightGrassPatch1, 409);
	public static Tiles lightGrassPatch2 = new Tiles(Assets.lightGrassPatch2, 410);
	public static Tiles lightGrassPatch3 = new Tiles(Assets.lightGrassPatch3, 411);
	public static Tiles flowerPatch1 = new Tiles(Assets.flowerPatch1, 412);
	public static Tiles flowerPatch2 = new Tiles(Assets.flowerPatch2, 413);
	public static Tiles flowerPatch3 = new Tiles(Assets.flowerPatch3, 414);

	public static Tiles invisible = new Tiles(Assets.invisible, 736);
	public static Tiles sparkleTile = new Tiles(Assets.sparkleTile, 649);
	public static Tiles redMushroom = new Tiles(Assets.redMushroom, 1184);
	public static Tiles blueMushroom = new Tiles(Assets.blueMushroom, 1168);
	public static Tiles smallRedRock = new Tiles(Assets.smallRedRocks, 664);
	
	// Objects
	public static Tiles woodenRoofTopLeft = new Tiles(Assets.woodenRoofTopLeft, 1521);
	public static Tiles woodenRoofTopMiddle = new Tiles(Assets.woodenRoofTopMiddle, 1522);
	public static Tiles woodenRoofTopRight = new Tiles(Assets.woodenRoofTopRight, 1523);
	public static Tiles woodenRoofMiddleLeft = new Tiles(Assets.woodenRoofMiddleLeft, 1529);
	public static Tiles woodenRoofMiddleMiddle = new Tiles(Assets.woodenRoofMiddleMiddle, 1530);
	public static Tiles woodenRoofMiddleRight = new Tiles(Assets.woodenRoofMiddleRight, 1531);
	public static Tiles woodenRoofBottomLeft = new Tiles(Assets.woodenRoofBottomLeft, 1537);
	public static Tiles woodenRoofBottomMiddle = new Tiles(Assets.woodenRoofBottomMiddle, 1538);
	public static Tiles woodenRoofBottomRight = new Tiles(Assets.woodenRoofBottomRight, 1539);
	
	public static Tiles greenRoofTopLeft = new Tiles(Assets.greenRoofTopLeft, 1526);
	public static Tiles greenRoofTopMiddle = new Tiles(Assets.greenRoofTopMiddle, 1527);
	public static Tiles greenRoofTopRight = new Tiles(Assets.greenRoofTopRight, 1528);
	public static Tiles greenRoofMiddleLeft = new Tiles(Assets.greenRoofMiddleLeft, 1534);
	public static Tiles greenRoofMiddleMiddle = new Tiles(Assets.greenRoofMiddleMiddle, 1535);
	public static Tiles greenRoofMiddleRight = new Tiles(Assets.greenRoofMiddleRight, 1536);
	public static Tiles greenRoofBottomLeft = new Tiles(Assets.greenRoofBottomLeft, 1542);
	public static Tiles greenRoofBottomMiddle = new Tiles(Assets.greenRoofBottomMiddle, 1543);
	public static Tiles greenRoofBottomRight = new Tiles(Assets.greenRoofBottomRight, 1544);

	public static Tiles wallLeft = new Tiles(Assets.wallLeft, 1377);
	public static Tiles wallRight = new Tiles(Assets.wallRight, 1379);
	public static Tiles wallMiddle = new Tiles(Assets.wallMiddle, 1378);
	public static Tiles lightWallLeft = new Tiles(Assets.lightWallLeft, 1369);
	public static Tiles lightWallMiddle = new Tiles(Assets.lightWallMiddle, 1370);
	public static Tiles lightWallRight = new Tiles(Assets.lightWallRight, 1371);
	public static Tiles entrance = new Tiles(Assets.entrance, 1663);
	public static Tiles woodenDoorTop = new Tiles(Assets.woodenDoorTop, 1423);
	public static Tiles woodenDoorBottom = new Tiles(Assets.woodenDoorBottom, 1431);
	
	public static Tiles floorTopLeft = new Tiles(Assets.floorTopLeft, 1409);
	public static Tiles floorTopMiddle = new Tiles(Assets.floorTopMiddle, 1410);
	public static Tiles floorTopRight = new Tiles(Assets.floorTopRight, 1411);
	public static Tiles floorMiddleLeft = new Tiles(Assets.floorMiddleLeft, 1417);
	public static Tiles floorMiddleMiddle = new Tiles(Assets.floorMiddleMiddle, 1418);
	public static Tiles floorMiddleRight = new Tiles(Assets.floorMiddleRight, 1419);
	public static Tiles floorBottomLeft = new Tiles(Assets.floorBottomLeft, 1425);
	public static Tiles floorBottomMiddle = new Tiles(Assets.floorBottomMiddle, 1426);
	public static Tiles floorBottomRight = new Tiles(Assets.floorBottomRight, 1427);
	
	public static Tiles tree1TopLeft = new Tiles(Assets.tree1TopLeft, 1145);
	public static Tiles tree1TopRight = new Tiles(Assets.tree1TopRight, 1146);
	public static Tiles tree1BottomLeft = new Tiles(Assets.tree1BottomLeft, 1161);
	public static Tiles tree1BottomRight = new Tiles(Assets.tree1BottomRight, 1162);
	public static Tiles tree1BatchTopLeft = new Tiles(Assets.tree1BatchTopLeft, 1147);
	public static Tiles tree1BatchTopRight = new Tiles(Assets.tree1BatchTopRight, 1148);
	public static Tiles tree1BatchBottomLeft = new Tiles(Assets.tree1BatchBottomLeft, 1163);
	public static Tiles tree1BatchBottomRight = new Tiles(Assets.tree1BatchBottomRight, 1164);
	
	public static Tiles whiteWallTopLeft = new Tiles(Assets.whiteWallTopLeft, 1697);
	public static Tiles whiteWallTopMiddle = new Tiles(Assets.whiteWallTopMiddle, 1698);
	public static Tiles whiteWallTopRight = new Tiles(Assets.whiteWallTopRight, 1699);
	public static Tiles whiteWallMiddleLeft = new Tiles(Assets.whiteWallMiddleLeft, 1713);
	public static Tiles whiteWallMiddleMiddle = new Tiles(Assets.whiteWallMiddleMiddle, 1714);
	public static Tiles whiteWallMiddleRight = new Tiles(Assets.whiteWallMiddleRight, 1715);
	public static Tiles whiteWallBottomLeft = new Tiles(Assets.whiteWallBottomLeft, 1729);
	public static Tiles whiteWallBottomMiddle = new Tiles(Assets.whiteWallBottomMiddle, 1730);
	public static Tiles whiteWallBottomRight = new Tiles(Assets.whiteWallBottomRight, 1731);
	
	public static Tiles brownColumnTop = new Tiles(Assets.brownColumnTop, 1358);
	public static Tiles brownColumnBottom = new Tiles(Assets.brownColumnBottom, 1366);
	
	public static Tiles smallWoodenStairTop = new Tiles(Assets.smallWoodenStairTop, 1400);
	public static Tiles smallWoodenStairBottom = new Tiles(Assets.smallWoodenStairBottom, 1408);
	
	public static Tiles stairTopLeft = new Tiles(Assets.stairTopLeft, 1397);
	public static Tiles stairTopMiddle = new Tiles(Assets.stairTopMiddle, 1398);
	public static Tiles stairTopRight = new Tiles(Assets.stairTopRight, 1399);
	public static Tiles stairBottomleLeft = new Tiles(Assets.stairBottomLeft, 1405);
	public static Tiles stairBottomMiddle = new Tiles(Assets.stairBottomMiddle, 1406);
	public static Tiles stairBottomRight = new Tiles(Assets.stairBottomRight, 1407);
	
	public static Tiles whiteWallWindowTopLeft = new Tiles(Assets.whiteWallWindowTopLeft, 1701);
	public static Tiles whiteWallWindowTopRight = new Tiles(Assets.whiteWallWindowTopRight, 1702);
	public static Tiles whiteWallWindowMiddleLeft = new Tiles(Assets.whiteWallWindowMiddleLeft, 1717);
	public static Tiles whiteWallWindowMiddleRight = new Tiles(Assets.whiteWallWindowMiddleRight, 1718);
	public static Tiles whiteWallWindowBottomLeft = new Tiles(Assets.whiteWallWindowBottomLeft, 1733);
	public static Tiles whiteWallWindowBottomRight = new Tiles(Assets.whiteWallWindowBottomRight, 1734);
	
	public static Tiles palmTreeTop = new Tiles(Assets.palmTreeTop, 1274);
	public static Tiles palmTreeBotom = new Tiles(Assets.palmTreeBottom, 1290);
	
	
	

	
	
	//CLASS
	
	public static final int TILEWIDTH = 32, TILEHEIGHT = 32;
	
	protected BufferedImage texture;
	protected final int id;
	protected int x, y;
	protected Rectangle bounds;
	protected int layer;
	
	public Tiles(BufferedImage texture, int id){
		this.texture = texture;
		this.id = id;
		
		tiles[id] = this;
		bounds = new Rectangle(0, 0, TILEWIDTH, TILEHEIGHT);
		
	}
	
	public int getLayer() {
		return layer;
	}
	
	public void setLayer(int layer) {
		this.layer = layer;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setPosition(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public Rectangle tilePosition(float xOffset, float yOffset){
		//
		bounds.x = 0;
		bounds.y = 0;
		bounds.width = 32;
		bounds.height = 32;
		
		Rectangle ir = new Rectangle();
		int arSize = TILEWIDTH;
		ir.width = arSize;
		ir.height = arSize;
		return new Rectangle((int) (x + bounds.x + xOffset), (int) (y + bounds.y + yOffset), bounds.width, bounds.height);
	}
	

	public void tick(){
		
	}
	
	public void render(Graphics g, int x, int y){
		g.drawImage(texture, x, y, TILEWIDTH, TILEHEIGHT, null);
	}
	
	public void renderMiniMap(Graphics g, int x, int y){
		g.drawImage(texture, x, y, (int)TILEWIDTH * 25 / 100 , (int)TILEHEIGHT * 25 / 100 , null);
	}
	
	public BufferedImage getTexture() {
		return texture;
	}


	public void setTexture(BufferedImage texture) {
		this.texture = texture;
	}


	public boolean isSolid(){
		switch(id){

		case 28: return true;
		case 85: return true;
		case 86: return true;
		case 117: return true;
		case 118: return true;
		case 148: return true;
		case 149: return true;
		case 150: return true;
		case 180: return true;
		case 181: return true;
		case 182: return true;
		case 212: return true;
		case 213: return true;
		case 214: return true;
		case 244: return true;
		case 245: return true;
		case 246: return true;
		case 73: return true;
		case 74: return true;
		case 105: return true;
		case 106: return true;
		case 136: return true;
		case 137: return true;
		case 138: return true;
		case 168: return true;
		case 169: return true;
		case 170: return true;
		case 200: return true;
		case 201: return true;
		case 202: return true;
		case 232: return true;
		case 233: return true;
		case 234: return true;
		case 76: return true;
		case 77: return true;
		case 108: return true;
		case 109: return true;
		case 139: return true;
		case 140: return true;
		case 141: return true;
		case 171: return true;
		case 172: return true;
		case 173: return true;
		case 203: return true;
		case 204: return true;
		case 205: return true;
		case 1184: return true;
		case 1168: return true;
		case 1521: return true;
		case 1522: return true;
		case 1523: return true;
		case 1529: return true;
		case 1530: return true;
		case 1531: return true;
		case 1537: return true;
		case 1538: return true;
		case 1539: return true;
		case 1377: return true;
		case 1378: return true;
		case 1379: return true;
		case 1145: return true;
		case 1146: return true;
		case 1161: return true;
		case 1162: return true;
		case 1697: return true;
		case 1698: return true;
		case 1699: return true;
		case 1713: return true;
		case 1714: return true;
		case 1715: return true;
		case 1729: return true;
		case 1730: return true;
		case 1731: return true;
		case 1701: return true;
		case 1702: return true;
		case 1717: return true;
		case 1718: return true;
		case 1733: return true;
		case 1734: return true;
		case 1358: return true;
		case 1366: return true;
		case 1369: return true;
		case 1370: return true;
		case 1371: return true;
		case 1526: return true;
		case 1527: return true;
		case 1528: return true;
		case 1534: return true;
		case 1535: return true;
		case 1536: return true;
		case 1542: return true;
		case 1543: return true;
		case 1544: return true;
		case 1423: return true;
		case 1274: return true;
		case 1290: return true;
		case 1147: return true;
		case 1148: return true;
		case 1163: return true;
		case 1164: return true;
				
		default: return false;
		}
	}
	
	public static Tiles getTileByID(int id) {
		return tiles[id];
	}
	
	public int getId(){
		return id;
	}

}
