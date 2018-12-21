package dev.ipsych0.myrinnia.quests;

import dev.ipsych0.myrinnia.skills.SkillsList;
import dev.ipsych0.myrinnia.worlds.Zone;

import java.awt.*;
import java.io.Serializable;
import java.util.*;
import java.util.List;

public class QuestManager implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 4508062817810741935L;

    private QuestUI questUI;

    // Quest Lists per zone
    private ArrayList<Quest> islandQuests = new ArrayList<Quest>();
    private ArrayList<Quest> testQuests = new ArrayList<Quest>();
    private ArrayList<Quest> mainQuests = new ArrayList<>();

    // Get Quests by Enum value
    private EnumMap<QuestList, Quest> questMap = new EnumMap<QuestList, Quest>(QuestList.class);

    // Put all Quest Lists per zone into a List of all Quest Lists
    private ArrayList<ArrayList<Quest>> allQuestLists = new ArrayList<>();

    // Map Zones to Quest Lists
    private EnumMap<Zone, ArrayList<Quest>> zoneMap = new EnumMap<Zone, ArrayList<Quest>>(Zone.class);

    public QuestManager() {
        questUI = new QuestUI();

        // Initializes all the lists of quests and zones
        initLists();

        // Fills the lists with Quest objects and sorts them alphabetically
        fillLists();

        // Maps zones to lists
        mapLists();

    }

    private void initLists() {
        // Filling allQuestLists with ALL lists of quests
        allQuestLists.add(islandQuests);
        allQuestLists.add(testQuests);
        allQuestLists.add(mainQuests);

    }

    private void fillLists() {

        // Island Quests
        islandQuests.add(new Quest("The First Quest", Zone.Island));
        islandQuests.add(new Quest("The Second Quest", Zone.Island, new QuestRequirement(QuestList.TheFirstQuest), new QuestRequirement(SkillsList.FISHING, 2)));
        islandQuests.add(new Quest("The Third Quest", Zone.Island));

        // Test Quests
        testQuests.add(new Quest("The Test Quest", Zone.IslandUnderground));

        //Main Quests
        mainQuests.add(new Quest("A Mysterious Finding", Zone.Myrinnia, new QuestRequirement("Talk to the Ability Master to learn about the use of magic in Myrinnia.")));

        Collections.sort(islandQuests, (o1, o2) -> o1.getQuestName().compareTo(o2.getQuestName()));

        // Sorts every list's quests by name, alphabetically
        for (int i = 0; i < allQuestLists.size(); i++) {
            Collections.sort(allQuestLists.get(i), (o1, o2) -> o1.getQuestName().compareTo(o2.getQuestName()));
        }

        // Sort the enum list of quests alphabetically as well
        List<QuestList> questEnums = Arrays.asList(QuestList.values());
        Collections.sort(questEnums, (o1, o2) -> o1.getZone().toString().compareTo(o2.getZone().toString()));

        int index = 0;
        // Maps the QuestList enums to the Quest objects
        for (int i = 0; i < allQuestLists.size(); i++) {
            for (int j = 0; j < allQuestLists.get(i).size(); j++) {
                try {
                    questMap.put(questEnums.get(index), allQuestLists.get(i).get(j));
                    index++;
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.err.println("Error mapping quests to enums. Please check if you have added the quest to 'QuestList.java'.");
                    System.exit(1);
                }
            }
        }
    }

    private void mapLists() {
        // Sort the Zone list by Zone Name
        List<Zone> zoneEnums = Arrays.asList(Zone.values());
        Collections.sort(zoneEnums, (o1, o2) -> o1.toString().compareTo(o2.toString()));


        // Sort the allQuestLists by zone as well
        Collections.sort(allQuestLists, (o1, o2) -> o1.get(0).getZone().toString().compareTo(o2.get(0).getZone().toString()));

        // Mapping the Zones together with the correct list of quests
        for (int i = 0; i < allQuestLists.size(); i++) {
            zoneMap.put(zoneEnums.get(i), allQuestLists.get(i));
        }
    }

    public void tick() {
        if (QuestUI.isOpen)
            questUI.tick();
    }

    public void render(Graphics g) {
        if (QuestUI.isOpen)
            questUI.render(g);
    }

    public EnumMap<QuestList, Quest> getQuestMap() {
        return questMap;
    }

    public void setQuestMap(EnumMap<QuestList, Quest> questMap) {
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

    public EnumMap<Zone, ArrayList<Quest>> getZoneMap() {
        return zoneMap;
    }

    public void setZoneMap(EnumMap<Zone, ArrayList<Quest>> zoneMap) {
        this.zoneMap = zoneMap;
    }


}
