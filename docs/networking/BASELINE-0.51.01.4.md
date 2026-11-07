# Networking baseline — MegaMek 0.51.01.4

Captured 2026-11-07T00:30:56.3078738 by `NetStatsBaselineTest` on Windows 11 amd64, 16 cores, JVM Eclipse Adoptium 21.0.12.

Scenario `testresources/data/scenarios/networking/NetBaseline.mms`: 4 Princess bots x 10 meks on a 3x3 board set plus a unit-less watcher, run with a 3-round limit (the runner calls `/victory` one round early, so two complete rounds were played). Finished: true; final round 2; wall time 59 s. Timing on.

## Server totals, every command seen

```
command                               sent    recv  bytes-out   bytes-in   raw-out  max-out marsh-ms unmar-ms handl-ms
SENDING_ENTITIES                       140       0   42867550          0 221001435   320084  7902.16     0.00     0.00
ENTITY_UPDATE                         1035       0   18992125          0  66207095    19934  2088.24     0.00     0.00
CHAT                                 13920    2778    1433940     252589   1172805      194   153.41    51.02     0.00
ENTITY_MOVE                              0      80          0    1541573         0        0     0.00   249.21  2684.83
PLAYER_UPDATE                          764       4    1060476       4906   2046575     1407    68.29     0.95    23.95
PLAYER_READY                           897      40     134910       4200    152490      151    11.43     1.07  6454.02
SENDING_BOARD                            5       0     129865          0   1414800    25973    66.31     0.00     0.00
ENTITY_ATTACK                          420      84      99720      25468    116940      242     7.70     5.05  3459.53
TURN                                   820       0     109670          0    112140      134    11.34     0.00     0.00
BLDG_UPDATE                             70       0      92300          0    346540     1607    15.00     0.00     0.00
SENDING_REPORTS                         80       0      69300          0    603970     2255    15.70     0.00     0.00
PLAYER_ADD                              40       0      51010          0     97330     1281    11.22     0.00     0.00
SENDING_TURNS                          105       0      32290          0     62130      412     4.69     0.00     0.00
SENDING_GAME_SETTINGS                    5       0      22935          0     74840     4587     7.68     0.00     0.00
PRINCESS_DISHONORED                     60      12      19515       3276     23280      326     1.34     1.27     2.74
END_OF_GAME                              5       0      17855          0    282455     3571     1.85     0.00     0.00
PHASE_CHANGE                           130       0      17390          0     18300      150     1.73     0.00     0.00
SENDING_REPORTS_ALL                     10       0      17380          0    350830     3362     7.55     0.00     0.00
SENDING_SPECIAL_HEX_DISPLAY             55       0      10560          0     11275      192     0.96     0.00     0.00
TEAM_INITIATIVE                         15       0       9320          0     26035      626     0.97     0.00     0.00
SENDING_PLANETARY_CONDITIONS            15       0       8855          0     23810      591     1.72     0.00     0.00
CLEAR_ILLUMINATED_HEXES                120       0       8280          0      6000       69     1.05     0.00     0.00
SENDING_FLARES                          45       0       7200          0      8235      160     0.62     0.00     0.00
UPDATE_GROUND_OBJECTS                   40       0       5520          0      5120      138     0.62     0.00     0.00
PRINCESS_SETTINGS                        5       4       2556       2776      4650      775     0.39     0.75     0.04
SENDING_ARTILLERY_ATTACKS               25       0       5000          0      5925      200     0.54     0.00     0.00
ROUND_UPDATE                            20       0       2505          0      2540      127     0.31     0.00     0.00
SENDING_MINEFIELDS                      10       0       1600          0      1830      160     0.22     0.00     0.00
SENDING_TAG_INFO                        10       0       1600          0      1830      160     0.11     0.00     0.00
CHANGE_HEXES                            10       0       1380          0      1280      138     0.13     0.00     0.00
UPDATE_CUT_HEXES                        10       0       1380          0      1280      138     0.14     0.00     0.00
SYNC_TEMPORARY_ECM_FIELDS               10       0       1140          0      1040      114     0.10     0.00     0.00
RESET_TAG_INFO                          15       0       1035          0       750       69     0.34     0.00     0.00
CLIENT_VERSIONS                          0       5          0        805         0        0     0.00     0.58     0.00
LOCAL_PN                                 5       0        624          0       635      125     0.18     0.00     0.00
CLIENT_NAME                              0       5          0        587         0        0     0.00     0.46     0.00
SERVER_VERSION_CHECK                     5       0        345          0       250       69     0.66     0.00     0.00
SERVER_GREETING                          5       0        345          0       250       69     0.15     0.00     0.00
GAME_VICTORY_EVENT                       5       0        345          0       250       69     0.04     0.00     0.00
CLOSE_CONNECTION                         0       3          0        201         0        0     0.00     0.03     0.00
total: 21946 packets, 67074202 bytes on the wire, 18935 flushes, slowest flush 8.88 ms
pump: 219 handled, max depth 5, max wait 946.75 ms
```

Columns: packets sent/received by the server; bytes-out/bytes-in are compressed frame payloads; raw-out is before gzip; max-out is the largest single frame; times are totals in ms.

## Per phase

| round | phase | ms | packets | bytes | busiest |
| --- | --- | --- | --- | --- | --- |
| -1 | STARTING_SCENARIO | 751 | 341 | 1919245 | SENDING_ENTITIES 1557885/5, SENDING_BOARD 129865/5, PLAYER_UPDATE 124080/95 |
| 0 | INITIATIVE | 61 | 5 | 1840 | SENDING_TURNS 1840/5 |
| 0 | INITIATIVE_REPORT | 1156 | 165 | 1664768 | SENDING_ENTITIES 1571415/5, PLAYER_UPDATE 69978/50, PLAYER_READY 11805/80 |
| 0 | DEPLOYMENT | 282 | 101 | 1649560 | SENDING_ENTITIES 1573605/5, PLAYER_UPDATE 69952/50, PLAYER_READY 3760/25 |
| 1 | INITIATIVE | 345 | 13 | 3344 | SENDING_TURNS 1835/5, PRINCESS_DISHONORED 819/3, CHAT 690/5 |
| 1 | INITIATIVE_REPORT | 685 | 190 | 1674078 | SENDING_ENTITIES 1573210/5, PLAYER_UPDATE 69983/50, PLAYER_READY 11805/80 |
| 1 | TARGETING | 281 | 75 | 1616196 | SENDING_ENTITIES 1573000/5, PLAYER_UPDATE 34976/25, PLAYER_READY 3760/25 |
| 1 | PREMOVEMENT | 281 | 70 | 1614806 | SENDING_ENTITIES 1573000/5, PLAYER_UPDATE 34976/25, PLAYER_READY 3760/25 |
| 1 | MOVEMENT | 24608 | 9734 | 10592950 | ENTITY_UPDATE 7278520/400, SENDING_ENTITIES 1573210/5, CHAT 890970/8994 |
| 1 | OFFBOARD | 310 | 95 | 1652781 | SENDING_ENTITIES 1598795/5, PLAYER_UPDATE 34976/25, BLDG_UPDATE 8035/5 |
| 1 | PRE_FIRING | 280 | 70 | 1640371 | SENDING_ENTITIES 1598795/5, PLAYER_UPDATE 34976/25, PLAYER_READY 3760/25 |
| 1 | FIRING | 1748 | 914 | 5419746 | ENTITY_UPDATE 3683020/200, SENDING_ENTITIES 1598695/5, ENTITY_ATTACK 60694/240 |
| 1 | PHYSICAL | 373 | 167 | 1782574 | SENDING_ENTITIES 1598995/5, ENTITY_UPDATE 92710/5, PLAYER_UPDATE 64330/46 |
| 1 | PREEND_DECLARATIONS | 281 | 20 | 1600850 | SENDING_ENTITIES 1599000/5, BLDG_UPDATE 800/5, PHASE_CHANGE 705/5 |
| 1 | END | 311 | 65 | 1645621 | SENDING_ENTITIES 1596565/5, PLAYER_UPDATE 34976/25, BLDG_UPDATE 8035/5 |
| 1 | END_REPORT | 374 | 165 | 1670126 | SENDING_ENTITIES 1596565/5, PLAYER_UPDATE 43429/31, PLAYER_READY 11805/80 |
| 1 | INITIATIVE | 31 | 24 | 27144 | PLAYER_UPDATE 26519/19, ROUND_UPDATE 625/5 |
| 2 | INITIATIVE | 342 | 10 | 2525 | SENDING_TURNS 1835/5, CHAT 690/5 |
| 2 | INITIATIVE_REPORT | 691 | 165 | 1665590 | SENDING_ENTITIES 1572200/5, PLAYER_UPDATE 69910/50, PLAYER_READY 11805/80 |
| 2 | TARGETING | 281 | 26 | 1583832 | SENDING_ENTITIES 1572235/5, PLAYER_UPDATE 8422/6, SENDING_REPORTS 2175/5 |
| 2 | PREMOVEMENT | 312 | 119 | 1645570 | SENDING_ENTITIES 1572235/5, PLAYER_UPDATE 61460/44, PLAYER_READY 7520/50 |
| 2 | MOVEMENT | 21269 | 7694 | 7097836 | ENTITY_UPDATE 3972715/215, SENDING_ENTITIES 1572200/5, ENTITY_MOVE 773024/40 |
| 2 | MOVEMENT_REPORT | 344 | 100 | 1645549 | SENDING_ENTITIES 1600220/5, PLAYER_UPDATE 34969/25, PLAYER_READY 8045/55 |
| 2 | OFFBOARD | 314 | 95 | 1654101 | SENDING_ENTITIES 1600150/5, PLAYER_UPDATE 34941/25, BLDG_UPDATE 8035/5 |
| 2 | PRE_FIRING | 311 | 70 | 1641691 | SENDING_ENTITIES 1600150/5, PLAYER_UPDATE 34941/25, PLAYER_READY 3760/25 |
| 2 | FIRING | 1447 | 942 | 5450127 | ENTITY_UPDATE 3681825/200, SENDING_ENTITIES 1600065/5, ENTITY_ATTACK 60698/240 |
| 2 | PHYSICAL | 442 | 171 | 1947446 | SENDING_ENTITIES 1600420/5, ENTITY_UPDATE 283335/15, PLAYER_UPDATE 34941/25 |
| 2 | PREEND_DECLARATIONS | 282 | 65 | 1649686 | SENDING_ENTITIES 1600420/5, PLAYER_UPDATE 34941/25, BLDG_UPDATE 8835/10 |
| 2 | END | 285 | 50 | 1611960 | SENDING_ENTITIES 1597825/5, SENDING_REPORTS 8770/5, PLAYER_READY 3760/25 |
| 2 | END_REPORT | 377 | 129 | 1635153 | SENDING_ENTITIES 1597825/5, SENDING_REPORTS_ALL 16810/5, PLAYER_READY 8045/55 |

## Per client

| client | packets out | bytes out | packets in | bytes in | slowest flush ms |
| --- | --- | --- | --- | --- | --- |
| Observer | 12 | 1301 | 3791 | 13051588 | 0.07 |
| West Alpha | 737 | 451126 | 3782 | 13045354 | 0.26 |
| West Bravo | 715 | 461418 | 3788 | 13049435 | 0.14 |
| East Alpha | 766 | 461819 | 3786 | 13046942 | 0.22 |
| East Bravo | 785 | 460717 | 3763 | 12708963 | 0.13 |

## Pump

219 packets dispatched, queue high-water 5, longest wait 946.75 ms, slowest flush 8.88 ms.

## Observations (the reference for Phases 2-6)

Read these against the totals above; every later PR re-runs this scenario and reports its own column
beside them.

1. **`SENDING_ENTITIES` is 64% of all bytes on the wire** (42.9 MB of 67.1 MB). The server sends the
   complete entity list to every client on every phase change: 28 phase changes x 5 clients = 140
   packets of ~306 KB compressed (320 KB max, 221 MB raw in total, 5.2:1 gzip). In a phase where
   nothing happens (`PRE_FIRING`, `PREEND_DECLARATIONS`) it is 97-99% of the phase's traffic. It also
   costs 7.9 s of marshalling on the pump thread, five times more than necessary because each
   client's `SendPacket` serialises the same objects independently. This is the Phase 4 target
   (deltas / skip unchanged entities) and the Phase 5/6 target (marshal a broadcast once).
2. **`ENTITY_UPDATE` is 28%** (19.0 MB): 1,035 packets averaging 18 KB compressed, 64 KB raw - a
   full `Entity` per unit move or damage event, sent to every client. Phase 4.
3. **`CHAT` is 63% of all packets** (13,920 of 21,946). The bots sent 2,778 chat lines, almost all
   during the movement phases, and the server echoed each to all five clients synchronously: ~9,000
   socket writes in one `MOVEMENT` phase for 0.9 MB. Cheap in bytes, expensive in pump time and
   write calls; a per-connection writer (Phase 6) absorbs it, and the source of the chat volume is
   worth a look before that.
4. **Pump:** queue high-water only 5, but the longest wait was 947 ms and `PLAYER_READY` handling
   totalled 6.4 s, `ENTITY_ATTACK` 3.5 s, `ENTITY_MOVE` 2.7 s. The time goes to the phase-change
   broadcasts executed inside the handler under the game lock, not to the game logic. Phase 6.
5. **Per client**, two rounds cost ~13 MB inbound; a player on a 10 Mbit link would spend ~10 s per
   round just receiving. The slowest flush was 8.9 ms on loopback - the socket never blocked here, so
   nothing in this run measures what a slow WAN client does to the others. The fault-injection
   harness is where that gets measured (Phase 2).
6. **Handshake and lobby state** are negligible at game scale (`SENDING_BOARD` 130 KB total,
   `PLAYER_ADD` 51 KB), but `PLAYER_UPDATE` is sent 764 times (1.06 MB) - 25 per phase, i.e. every
   player's record to every client on every phase, unchanged or not. Low-hanging Phase 4 item.
