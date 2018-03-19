package dev.ipsych0.mygame.quests;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.entities.Entity;
import dev.ipsych0.mygame.worlds.Zone;

public class QuestManager{
	
	private Handler handler;
	private QuestUI questUI;
	
	// Quest Lists per zone
	private ArrayList<Quest> islandQuests = new ArrayList<Quest>();
	private ArrayList<Quest> testQuests = new ArrayList<Quest>();
	
	// Get Quests by Enum value
	private HashMap<QuestList, Quest> questMap = new HashMap<>();
	
	// Put all Quest Lists per zone into a List of all Quest Lists
	private ArrayList<ArrayList<Quest>> allQuestLists = new ArrayList<>();
	
	// Map Zones to Quest Lists
	private HashMap<Zone, ArrayList<Quest>> zoneMap = new HashMap<>();
	
	public QuestManager(Handler handler) {
		this.handler = handler;
		questUI = new QuestUI(handler);
		
		// Initializes all the lists of quests and zones
		initLists();
		
		// Fills the lists with Quest objects and sorts them alphabetically
		fillLists();
		
		// Maps zones to lists
		mapLists();
		
	}
	
	private void initLists() {
		// Initiate a list that contains all list of quests
		allQuestLists = new ArrayList<ArrayList<Quest>>();
		
		// Initiating the list of quests PER ZONE
		islandQuests = new ArrayList<Quest>();
		testQuests = new ArrayList<Quest>();
		
		// Filling allQuestLists with ALL lists of quests
		allQuestLists.add(islandQuests);
		allQuestLists.add(testQuests);
	}
	
	private void fillLists() {
		// Island Quests
		islandQuests.add(new Quest(handler, "The First Quest", Zone.Island));
		islandQuests.add(new Quest(handler, "The Second Quest", Zone.Island));
		islandQuests.add(new Quest(handler, "The Third Quest", Zone.Island));
		
		// Test Quests
		testQuests.add(new Quest(handler, "The Test Quest", Zone.Mainland));
		
		
		// Sorts every lists quests by name, alphabetically
		for(int i = 0; i < allQuestLists.size(); i++) {
			Collections.sort(allQuestLists.get(i), new Comparator<Quest>() {
				@Override
				public int compare(Quest o1, Quest o2) {
					// TODO Auto-generated method stub
					return o1.getQuestName().compareTo(o2.getQuestName());
				}
			});
		}
		
		// Sort the enum list of quests alphabetically as well
		List<QuestList> questEnums = Arrays.asList(QuestList.values());
		Collections.sort(questEnums, new Comparator<QuestList>() {
			@Override
			public int compare(QuestList o1, QuestList o2) {
				// TODO Auto-generated method stub
				return o1.getZone().toString().compareTo(o2.getZone().toString());
			}
		});
		
		int index = 0;
		// Maps the QuestList enums to the Quest objects
		for(int i = 0; i < allQuestLists.size(); i++) {
			for(int j = 0; j < allQuestLists.get(i).size(); j++) {
				questMap.put(questEnums.get(index), allQuestLists.get(i).get(j));
				index++;
			}
		}
	}
	
	private void mapLists() {
		// Sort the Zone list by Zone Name
		List<Zone> zoneEnums = Arrays.asList(Zone.values());
		Collections.sort(zoneEnums, new Comparator<Zone>() {
			@Override
			public int compare(Zone o1, Zone o2) {
				// TODO Auto-generated method stub
				return o1.toString().compareTo(o2.toString());
			}
		});
		
		
		// Sort the allQuestLists by zone as well
		Collections.sort(allQuestLists, new Comparator<ArrayList<Quest>>() {
			@Override
			public int compare(ArrayList<Quest> o1, ArrayList<Quest> o2) {
				// TODO Auto-generated method stub
				return o1.get(0).getZone().toString().compareTo(o2.get(0).getZone().toString());
			}
		});
		
		// Mapping the Zones together with the correct list of quests
		for(int i = 0; i < allQuestLists.size(); i++) {
			zoneMap.put(zoneEnums.get(i), allQuestLists.get(i));
		}
	}
	
	public void tick() {
		if(QuestUI.isOpen)
			questUI.tick();
	}
	
	public void render(Graphics g) {
		if(QuestUI.isOpen)
			questUI.render(g);
	}

	public ArrayList<Quest> getIslandQuests() {
		return islandQuests;
	}

	public void setIslandQuests(ArrayList<Quest> questList) {
		this.islandQuests = questList;
	}

	public HashMap<QuestList, Quest> getQuestMap() {
		return questMap;
	}

	public void setQuestMap(HashMap<QuestList, Quest> questMap) {
		this.questMap = questMap;
	}

	public QuestUI getQuestUI() {
		return questUI;
	}

	public void setQuestUI(QuestUI questUI) {
		this.questUI = questUI;
	}

	public ArrayList<ArrayList<Quest>> getAllQuestLists() {
		return allQuestLists;
	}

	public void setAllQuestLists(ArrayList<ArrayList<Quest>> allQuestLists) {
		this.allQuestLists = allQuestLists;
	}

	public HashMap<Zone, ArrayList<Quest>> getZoneMap() {
		return zoneMap;
	}

	public void setZoneMap(HashMap<Zone, ArrayList<Quest>> zoneMap) {
		this.zoneMap = zoneMap;
	}
	
	

}
