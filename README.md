- **[1: Game Information](#1-game-information)**

  - **[1.1: Introduction](#11-introduction)**

  - **[1.2: Theme](#12-theme)**

  - **[1.3: Setting](#13-setting)**

  - **[1.4: Genre](#14-genre)**

  - **[1.5: Gameplay (controls)](#15-gameplay-controls)**

- **[2: Game overview](#2-game-overview)**

  - **[2.1: Story summary](#21-story-summary)**

  - **[2.2: Character](#22-character)**

  - **[2.3: Story detailed](#23-story-detailed)**

  - **[2.4: Combat](#24-combat)**

  - **[2.5: Skills](#25-skills)**

  - **[2.6: Zone overview](#26-zone-overview)**

- **[3: Technical overview](#3-technical-overview)**

  - **[3.1: Engine](#31-engine)**

  - **[3.2: Graphics](#32-graphics)**

  - **[3.3: Sound](#33-sound)**

  

# Elements of Myrinnia
  
## Introduction  
Elements of Myrinnia is a 2D adventure action RPG that takes place in the magic-filled fantasy world of Myrinnia. As an adventurer, you can tailor your own experience by choosing the skills and elements you want to train. Combat is based on the classical four elements: Air, Earth, Fire, Water. There are a plethora of skills for you to train, which may assist you in combat or complement the rich number of lore-supporting quests that Myrinnia has to offer. With puzzles and secrets throughout the world, there is plenty for you to explore in Myrinnia!

### Gameplay


### How to play
  #### Building from source code
````  
1. git clone https://github.com/iPsych0/Myrinnia.git  
2. Myrinnia uses version >=14.0.2 of the Java SDK to compile and run.
3. Download IntelliJ if you don't have it already.
4. Open the project in IntelliJ.
5. Navigate to ./src/dev/ipsych0/myrinnia/Launcher.java
6. Run the main method, which will boot the application and make it playable.
7. ???
8. Profit. 
````
#### Download the latest version
> TODO: Create an installer that packages the Java SDK with the JAR to create a runnable .exe distribution.

# Game Design Document

# **1: General Information**

## **1.1: Introduction**

**This is a game design document for the game Elements of Myrinnia. This document will provide an insight into the context of the game, as well as the gameplay, story, game design and technical choices.**

**Chapter 1 will discuss the theme, setting, genre and general gameplay of Elements of Myrinnia. Next, chapter 2 will discuss the main plot, the player character, the combat and skills that can be trained and the different zones in Myrinnia.**

**Finally, chapter 3 will review the technical design choices for Elements of Myrinnia. In specific, the engine that is used to create the world of Myrinnia, the graphical design and the sound design.**

## **1.2: Themes**

**In Elements of Myrinnia, the most common theme you will encounter is conflict. Conflict between fanatics and purists, conservatism vs. progressivism, revenge and good vs. evil.**

## **1.3: Setting**

**Elements of Myrinnia is set in a medieval fantasy world, where magic is commonplace. The aptitude of magic comes from the four primordial spirits Luna (air), Fyra (fire), Terra (earth) and Seyla (water), whom together shaped the world of Myrinnia with their divine magical powers. The remnants of magic that linger in Myrinnia lets its inhabitants channel some of this magic and use it. From the early-day factions and cults aligned to these elements, conflicts often arise for the adventurer to resolve.**

## **1.4: Genre**

**Elements of Myrinnia is a 2D, Top-Down Action RPG where the player character explores a fantasy world filled with magic through combat, quests and skills. Elements of Myrinnia has drawn inspiration from games such as RuneScape, Guild Wars and the TV series Avatar: The Last Airbender and blends it into a unique 2D adventure-like open world experience.**

## **1.5: Gameplay (controls)**

**The player character uses WASD movement keys to walk around. For combat and interaction with user interfaces, the mouse is used.**

**By hovering the mouse over objects or NPCs, a tooltip will display at the top of the screen. This indicates that an object or NPC may be interacted with. If no tooltip shows up when hovering over an object, this means you cannot interact with it. SHIFT may be held to highlight interactable entities.**

**The left-mouse button is primarily used for aiming and attacking but is also used to drag items and to click in user interfaces.**

**The right-mouse button is used to interact primarily with items or objects. By right-clicking, the player may pick up an item when standing on it, equip/unequip an item and quickly swap items between interfaces. In addition, objects or enemies may be right-clicked to lock in their hovering tooltip.**

**Spacebar is used to interact with objects or NPCs in Myrinnia. While interacting, the player may be presented with some dialogue and/or choices to advance a dialogue.**

**By default, the interfaces that are open are the ‘Chat Window’, ‘Inventory & Equipment’, ‘HP Overlay’ and the Ability Bar. All interfaces but the Ability Bar can be toggled off to customize the player’s experience. For a full list of all interface toggle keys, please see the in-game Settings Menu and go to the Controls tab to view/change the key bindings.**

**Combat in Myrinnia works with ‘auto attacks’ and ‘abilities’. These abilities can be unlocked by discovering new areas, quests, puzzles or Ability Masters. Abilities can be slotted in the Ability Bar. The key binds can be changed to your preference.**

**Finally, the game is rendered through an orthographic camera, which centers around the player.**

# **2: Gameplay**

## **2.1: Story summary**

**The story of Myrinnia is complex, as there is no traditional ‘main-thread story’. The past, present and future are defined by the many quests that Myrinnia has to offer, which serve to enrich the lore of each area in the game. In general, most impactful quests revolve around conflicts of the past between fanatics and purists that require the adventurer’s assistance in solving.**

## **2.2: Character**

**The main character is an adventurer born on Azureal Island, a tranquil island remotely located far west of the mainland of Myrinnia. Much of the character’s traits are open to the player’s interpretation of the game. Throughout the game, the player gets to experience the main character in a way they want to play the game by making their own choices.**

## **2.3: Combat**

### **2.3.1: General Combat**

**Combat is at the game’s core and much of the time exploring, you will find that you will engage in combat situations. During these encounters, the player can freely choose to fight with magic, ranged or melee combat. Elements of Myrinnia does not lock you into a class. Instead, rather than punishing choices by locking out content permanently, it leaves open all options for the player to tailor their build and experiment with it.**

**To customize your experience, you get to spend points in certain character traits whenever you level up a combat level. You can dedicate 1 point into melee/magic/ranged combat and dedicate 1 point into air/earth/fire/water skills after each level-up. As of now, there is a combat cap of 50. Dedicating points to melee/ranged/magic allows you to wear higher level equipment. The more points you dedicate to an element, the stronger your abilities are. If at any point, the player feels dissatisfied with their current build, they may choose to redistribute their points for a fee.**

### **2.3.2: Combat Attributes**

**In Elements of Myrinnia, there are 7 combat attributes: Strength (STR), Dexterity (DEX), Intelligence (INT), Defence (DEF), Vitality (VIT), Attack Speed (ATK Spd.) and Movement Speed (MOV Spd.)**

**Strength determines the player’s melee power. Dexterity determines the player’s ranged power. Intelligence determines the player’s magic damage. These stats increase the player’s standard attack’s damage and can be requirements to wield certain gear.**

**Defence decreases the damage received from all raw damage sources (auto-attacks or abilities). Defence also serves as a requirement to wear better armor. Vitality increases the player’s maximum health at a 4:1 ratio, meaning a player gains 4 maximum health for each point of Vitality. This makes the player more resistant to conditions or other damage-over-time effects. Vitality can in some cases be a requirement to wear better armor.**

**Attack speed increases the player’s speed of auto-attacks, but not that of abilities. Movement speed increases the player’s walking speed. Movement speed can be reduced up to 50% by conditions, explained later in this chapter.**

### **2.3.3: Abilities**

**Abilities are what makes the combat of Elements of Myrinnia unique and fresh. Abilities can be sub-classed into Magic / Ranged / Melee Abilities. Each of these can be sub-classed into Air/Earth/Fire/Water Skills. Each of these elements strongly favor a certain playstyle.**

- **Fire Abilities mostly concentrate on offensive, high risk/reward gameplay and as such are generally best capable of dealing high bursts of damage at the cost of lower sustainability. Two common elements associated are fire and smoke.**

- **Air Abilities mostly concentrate on battlefield control, such as movement speed, stuns, and moderate damage, at the cost of some sustainability and some damage. Two common elements associated are wind and lightning.**

- **Water Abilities mostly concentrate on healing and survivability. Examples are direct healing, regeneration, condition removal and moderate defensive utility at the cost of damage. Two common elements associated are water and ice.**

- **Finally, Earth Abilities mostly concentrate on defensive control and survivability. Examples are damage reduction, shielding mechanisms and moderate healing at the cost of damage. Two common elements associated are nature and rock.**

### **2.3.4: Conditions**

**In combat, you may encounter enemies or use abilities that can apply certain conditions. These conditions as of Alpha version are:**

- **Burning: Inflicts damage over time.**

- **Chill: Decreases the receiver’s movement speed by 50%.**

- **Crippled: Decreases the receiver’s movement speed by 33%.**

- **Bleeding: Inflicts damage over time.**

- **Poison: Inflicts damage over time.**

- **Rooted: Renders the receiver immobilized.**

- **Blinded: Makes the next attack miss.**

- **Stun: Immobilized and prevents the receiver from fighting back.**

**The power or intensity of these conditions increase the more points are spent in the Elemental Skill that caused the condition. For example, if you have a Fire Ability that inflicts burning, each point you attribute to Fire Skills will slightly increase damage and/or duration depending on the Ability.**

### **2.3.5: Buffs**

**Buffs can vary tremendously from flat stat modifiers to more complex buffs, like resurrecting with 50% upon taking a fatal hit. In general buffs can be sub-classed into three categories:**

- **Stat modifiers**

- **Complex buffs**

- **Resistance**

**The first category, stat modifiers, can apply an additive or multiplicative boost to your stat attributes (power, dexterity, intelligence, defense, vitality, movement speed, attack speed).**

**Complex buffs vary but are generally comprised of utility buffs or buffs that trigger on certain combat events.**

**Resistance is a partial counter to conditions, as resistance reduces the effectiveness of conditions for a certain duration. Resistance can be obtained by drinking potions, eating certain food dishes or some abilities.**

## **2.4: Non-Combat Skills**

**There are 6 skills (7 if you count combat):**

**Bounty Hunter involves helping out locals with combat-related troubles. As a reward for helping these locals, you may obtain bounties that can include equipment, gold, experience or abilities. They can be considered as mini-quests, which generally involve very light story and a focus on combat. You will often face demi-bosses that provide a refreshing change from killing & grinding enemies. Look out for Bounty Boards in each region!**

- **Crafting is the process of refining or generating items. In general, the process involves combining 1 to 4 items that form a ‘recipe’ to create a new item. This can be done at any workbench in-game. An icon overlay will indicate where the player may train Crafting. Players may obtain items such as equipment and potions elsewhere in the game, but the general philosophy of Elements of Myrinnia is that Crafting should be a skill worth training on its own. Thus, crafted items will always be better than those obtained from enemies in the open world. This gives incentive to train crafting, although not a necessity.**

- **Farming involves planting seeds varying from fruit trees to vegetables, bush plants and other produce. The purpose of Farming is to grow ingredients for food that can be used in the Crafting skill to make long-lasting buffs for the player to assist in combat. Players may plant seeds only in certain Farming Patches, spread out over Myrinnia.**

- **Fishing is used to catch different kinds of fish all over Myrinnia. These fish may be used in food ingredients, providing in addition to Farming, a way to produce food in the Crafting skill for long-lasting buffs.**

- **The Mining skill is used to gather raw ore and other crafting ingredients to make equipment and some weapons via the Crafting skill, mostly for melee equipment. While training Mining, sometimes rare minerals such as gems may be found, which can be used to craft trinkets.**

- **The Woodcutting skill is used to gather logs which can be used in the Crafting skill to craft staves and ranged weapons mostly. While training Woodcutting, sometimes seeds may fall out of the tree, which can be used in the Farming skill.**

# **3: Technical overview**

## **3.1: Engine**

## **3.2: Graphics**

## **3.3: Sound**