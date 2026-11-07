# Networking baseline scenario for the MegaMek networking fork.
#
# Headless bot-vs-bot game used by NetStatsBaselineTest to capture packet and byte counts per command. Four
# Princess companies of ten common Inner Sphere meks on a 3x3 board set, two per side, so the server broadcasts
# to four real clients while the unit-less Observer slot (claimed by the headless runner) watches. Keep this file
# stable: the numbers in docs/networking/BASELINE-*.md are only comparable while the scenario does not change.

MMSVersion: 2
name: Networking Baseline
description: >
  Four Princess companies (40 meks) on a 3x3 grassland map set. The first faction is the unit-less watcher slot
  claimed by ScenarioGameRunner; the four bot factions form two teams.

map:
  cols: 3
  boards:
    - file: AGoAC Maps/16x17 Grassland 2.board
    - file: AGoAC Maps/16x17 Grassland 3.board
    - file: AGoAC Maps/16x17 Grassland 2.board
    - file: AGoAC Maps/16x17 Grassland 3.board
    - file: AGoAC Maps/16x17 Grassland 2.board
    - file: AGoAC Maps/16x17 Grassland 3.board
    - file: AGoAC Maps/16x17 Grassland 2.board
    - file: AGoAC Maps/16x17 Grassland 3.board
    - file: AGoAC Maps/16x17 Grassland 2.board

factions:
- name: Observer
  # Unit-less watcher slot; ScenarioGameRunner claims the first faction with a headless client. Its own team,
  # so the runner's winner/standings logic does not fold it into either side.
  team: 3

- name: West Alpha
  team: 1
  deploy: W
  bot:
    selfpreservation: 5
    fallshame: 5
    hyperaggression: 5
    herdmentality: 5
    bravery: 5
    forcedwithdraw: false
    flee: false
  units:
  - fullname: Locust LCT-1V
    crew:
      name: WA Pilot 1
      gunnery: 4
      piloting: 5
  - fullname: Wasp WSP-1A
    crew:
      name: WA Pilot 2
      gunnery: 4
      piloting: 5
  - fullname: Stinger STG-3R
    crew:
      name: WA Pilot 3
      gunnery: 4
      piloting: 5
  - fullname: Commando COM-2D
    crew:
      name: WA Pilot 4
      gunnery: 4
      piloting: 5
  - fullname: Jenner JR7-D
    crew:
      name: WA Pilot 5
      gunnery: 4
      piloting: 5
  - fullname: Panther PNT-9R
    crew:
      name: WA Pilot 6
      gunnery: 4
      piloting: 5
  - fullname: Griffin GRF-1N
    crew:
      name: WA Pilot 7
      gunnery: 4
      piloting: 5
  - fullname: Wolverine WVR-6R
    crew:
      name: WA Pilot 8
      gunnery: 4
      piloting: 5
  - fullname: Shadow Hawk SHD-2H
    crew:
      name: WA Pilot 9
      gunnery: 4
      piloting: 5
  - fullname: Phoenix Hawk PXH-1
    crew:
      name: WA Pilot 10
      gunnery: 4
      piloting: 5

- name: West Bravo
  team: 1
  deploy: W
  bot:
    selfpreservation: 5
    fallshame: 5
    hyperaggression: 5
    herdmentality: 5
    bravery: 5
    forcedwithdraw: false
    flee: false
  units:
  - fullname: Crusader CRD-3R
    crew:
      name: WB Pilot 1
      gunnery: 4
      piloting: 5
  - fullname: Catapult CPLT-C1
    crew:
      name: WB Pilot 2
      gunnery: 4
      piloting: 5
  - fullname: Warhammer WHM-6R
    crew:
      name: WB Pilot 3
      gunnery: 4
      piloting: 5
  - fullname: Marauder MAD-3R
    crew:
      name: WB Pilot 4
      gunnery: 4
      piloting: 5
  - fullname: Archer ARC-2R
    crew:
      name: WB Pilot 5
      gunnery: 4
      piloting: 5
  - fullname: Thunderbolt TDR-5S
    crew:
      name: WB Pilot 6
      gunnery: 4
      piloting: 5
  - fullname: Rifleman RFL-3N
    crew:
      name: WB Pilot 7
      gunnery: 4
      piloting: 5
  - fullname: Orion ON1-K
    crew:
      name: WB Pilot 8
      gunnery: 4
      piloting: 5
  - fullname: Atlas AS7-D
    crew:
      name: WB Pilot 9
      gunnery: 4
      piloting: 5
  - fullname: BattleMaster BLR-1G
    crew:
      name: WB Pilot 10
      gunnery: 4
      piloting: 5

- name: East Alpha
  team: 2
  deploy: E
  bot:
    selfpreservation: 5
    fallshame: 5
    hyperaggression: 5
    herdmentality: 5
    bravery: 5
    forcedwithdraw: false
    flee: false
  units:
  - fullname: Panther PNT-9R
    crew:
      name: EA Pilot 1
      gunnery: 4
      piloting: 5
  - fullname: Griffin GRF-1N
    crew:
      name: EA Pilot 2
      gunnery: 4
      piloting: 5
  - fullname: Wolverine WVR-6R
    crew:
      name: EA Pilot 3
      gunnery: 4
      piloting: 5
  - fullname: Shadow Hawk SHD-2H
    crew:
      name: EA Pilot 4
      gunnery: 4
      piloting: 5
  - fullname: Phoenix Hawk PXH-1
    crew:
      name: EA Pilot 5
      gunnery: 4
      piloting: 5
  - fullname: Crusader CRD-3R
    crew:
      name: EA Pilot 6
      gunnery: 4
      piloting: 5
  - fullname: Catapult CPLT-C1
    crew:
      name: EA Pilot 7
      gunnery: 4
      piloting: 5
  - fullname: Warhammer WHM-6R
    crew:
      name: EA Pilot 8
      gunnery: 4
      piloting: 5
  - fullname: Marauder MAD-3R
    crew:
      name: EA Pilot 9
      gunnery: 4
      piloting: 5
  - fullname: Archer ARC-2R
    crew:
      name: EA Pilot 10
      gunnery: 4
      piloting: 5

- name: East Bravo
  team: 2
  deploy: E
  bot:
    selfpreservation: 5
    fallshame: 5
    hyperaggression: 5
    herdmentality: 5
    bravery: 5
    forcedwithdraw: false
    flee: false
  units:
  - fullname: Thunderbolt TDR-5S
    crew:
      name: EB Pilot 1
      gunnery: 4
      piloting: 5
  - fullname: Rifleman RFL-3N
    crew:
      name: EB Pilot 2
      gunnery: 4
      piloting: 5
  - fullname: Orion ON1-K
    crew:
      name: EB Pilot 3
      gunnery: 4
      piloting: 5
  - fullname: Atlas AS7-D
    crew:
      name: EB Pilot 4
      gunnery: 4
      piloting: 5
  - fullname: BattleMaster BLR-1G
    crew:
      name: EB Pilot 5
      gunnery: 4
      piloting: 5
  - fullname: Locust LCT-1V
    crew:
      name: EB Pilot 6
      gunnery: 4
      piloting: 5
  - fullname: Wasp WSP-1A
    crew:
      name: EB Pilot 7
      gunnery: 4
      piloting: 5
  - fullname: Stinger STG-3R
    crew:
      name: EB Pilot 8
      gunnery: 4
      piloting: 5
  - fullname: Commando COM-2D
    crew:
      name: EB Pilot 9
      gunnery: 4
      piloting: 5
  - fullname: Jenner JR7-D
    crew:
      name: EB Pilot 10
      gunnery: 4
      piloting: 5
