/*
 * Copyright (C) 2026 The MegaMek Team. All Rights Reserved.
 *
 * This file is part of MegaMek.
 *
 * MegaMek is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License (GPL),
 * version 3 or (at your option) any later version,
 * as published by the Free Software Foundation.
 *
 * MegaMek is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * A copy of the GPL should have been included with this project;
 * if not, see <https://www.gnu.org/licenses/>.
 *
 * NOTICE: The MegaMek organization is a non-profit group of volunteers
 * creating free software for the BattleTech community.
 *
 * MechWarrior, BattleMech, `Mech and AeroTech are registered trademarks
 * of The Topps Company, Inc. All Rights Reserved.
 *
 * Catalyst Game Labs and the Catalyst Game Labs logo are trademarks of
 * InMediaRes Productions, LLC.
 *
 * MechWarrior Copyright Microsoft Corporation. MegaMek was created under
 * Microsoft's "Game Content Usage Rules"
 * <https://www.xbox.com/en-US/developers/rules> and it is not endorsed by or
 * affiliated with Microsoft.
 */
package megamek.server.net;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import megamek.SuiteConstants;
import megamek.client.AbstractClient;
import megamek.common.enums.GamePhase;
import megamek.common.net.stats.NetStats;
import megamek.common.net.stats.NetStats.CommandStats;
import megamek.common.net.stats.NetStats.Snapshot;
import megamek.server.Server;
import megamek.utilities.ScenarioGameRunner;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

/**
 * Captures the networking baseline: a headless bot-vs-bot game on a fixed scenario with timing on, written as a
 * Markdown report to {@code build/reports/netstats/baseline.md}. Not a pass/fail test of anything but "the game ran";
 * its product is the report, which is copied into {@code docs/networking/} as the reference that later
 * optimisation PRs are measured against.
 *
 * <p>Off by default because it takes several minutes. Run it with the environment variable {@code NET_BASELINE=true}
 * (and optionally {@code NET_BASELINE_ROUNDS}, default 3):</p>
 * <pre>
 *   $env:NET_BASELINE="true"; .\gradlew.bat :megamek:test -x checkstyleMain --tests megamek.server.net.NetStatsBaselineTest
 * </pre>
 */
@EnabledIfEnvironmentVariable(named = "NET_BASELINE", matches = "true")
class NetStatsBaselineTest {

    private static final File SCENARIO = new File("testresources/data/scenarios/networking/NetBaseline.mms");
    private static final File REPORT = new File("build/reports/netstats/baseline.md");
    private static final int TIMEOUT_MINUTES = 30;

    /** One phase of the game as the server's counters saw it. */
    private record PhaseWindow(int round, GamePhase phase, long millis, Snapshot traffic) {}

    @Test
    void captureBaseline() throws Exception {
        int rounds = Integer.parseInt(System.getenv().getOrDefault("NET_BASELINE_ROUNDS", "3"));
        assertTrue(SCENARIO.isFile(), "scenario missing: " + SCENARIO.getAbsolutePath());
        NetStats.setTimingEnabled(true);

        ScenarioGameRunner runner = new ScenarioGameRunner(SCENARIO);
        Server server = runner.getServer();
        NetStats stats = server.getNetStats();
        List<PhaseWindow> windows = new ArrayList<>();
        PhaseWatcher watcher = new PhaseWatcher(server, stats, windows);
        watcher.start();
        long started = System.nanoTime();
        ScenarioGameRunner.GameResult result;
        try {
            result = runner.runGame(rounds, TIMEOUT_MINUTES);
        } finally {
            watcher.stop();
        }
        long wallMillis = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - started);
        Snapshot total = stats.snapshot();
        List<String> clientLines = describeClients(runner.getConnectedClients());
        runner.shutdown();

        writeReport(result, rounds, wallMillis, total, windows, clientLines);
        assertTrue(result.finished(), "the baseline game did not finish within " + TIMEOUT_MINUTES + " minutes");
    }

    /** Polls the server's phase and round and closes a traffic window every time either changes. */
    private static final class PhaseWatcher {
        private final Server server;
        private final NetStats stats;
        private final List<PhaseWindow> windows;
        private final Thread thread;
        private volatile boolean running = true;

        PhaseWatcher(Server server, NetStats stats, List<PhaseWindow> windows) {
            this.server = server;
            this.stats = stats;
            this.windows = windows;
            thread = new Thread(this::run, "netstats-phase-watcher");
            thread.setDaemon(true);
        }

        void start() {
            thread.start();
        }

        void stop() throws InterruptedException {
            running = false;
            thread.join(5_000);
        }

        private void run() {
            GamePhase phase = server.getGame().getPhase();
            int round = server.getGame().getCurrentRound();
            Snapshot last = stats.snapshot();
            long since = System.nanoTime();
            while (running) {
                GamePhase nowPhase = server.getGame().getPhase();
                int nowRound = server.getGame().getCurrentRound();
                if (nowPhase != phase || nowRound != round) {
                    Snapshot now = stats.snapshot();
                    long nanos = System.nanoTime();
                    synchronized (windows) {
                        windows.add(new PhaseWindow(round, phase, TimeUnit.NANOSECONDS.toMillis(nanos - since),
                              now.since(last)));
                    }
                    last = now;
                    since = nanos;
                    phase = nowPhase;
                    round = nowRound;
                }
                try {
                    Thread.sleep(20);
                } catch (InterruptedException interrupted) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        }
    }

    private static List<String> describeClients(List<AbstractClient> clients) {
        List<String> lines = new ArrayList<>();
        for (AbstractClient client : clients) {
            Snapshot snapshot = client.getNetStats().snapshot();
            long sentPackets = snapshot.commands().stream().mapToLong(CommandStats::sent).sum();
            long sentBytes = snapshot.commands().stream().mapToLong(CommandStats::wireBytesSent).sum();
            long receivedPackets = snapshot.commands().stream().mapToLong(CommandStats::received).sum();
            long receivedBytes = snapshot.commands().stream().mapToLong(CommandStats::wireBytesReceived).sum();
            lines.add(String.format(Locale.ROOT, "| %s | %d | %d | %d | %d | %.2f |", client.getName(), sentPackets,
                  sentBytes, receivedPackets, receivedBytes, snapshot.maxFlushNanos() / 1_000_000.0));
        }
        return lines;
    }

    private static void writeReport(ScenarioGameRunner.GameResult result, int rounds, long wallMillis,
          Snapshot total, List<PhaseWindow> windows, List<String> clientLines) throws IOException {
        File directory = REPORT.getParentFile();
        if (!directory.isDirectory() && !directory.mkdirs()) {
            throw new IOException("Could not create " + directory);
        }
        try (PrintWriter out = new PrintWriter(REPORT, StandardCharsets.UTF_8)) {
            out.println("# Networking baseline — MegaMek " + SuiteConstants.VERSION);
            out.println();
            out.println("Captured " + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                  + " by `NetStatsBaselineTest` on " + System.getProperty("os.name") + " "
                  + System.getProperty("os.arch") + ", " + Runtime.getRuntime().availableProcessors() + " cores, JVM "
                  + System.getProperty("java.vendor") + " " + System.getProperty("java.version") + ".");
            out.println();
            out.println("Scenario `" + SCENARIO.getPath().replace('\\', '/') + "`: 4 Princess bots x 10 meks on a 3x3 "
                  + "board set plus a unit-less watcher, run for " + rounds + " rounds. Finished: " + result.finished()
                  + "; final round " + result.rounds() + "; wall time " + wallMillis / 1000 + " s. Timing on.");
            out.println();
            out.println("## Server totals, every command seen");
            out.println();
            out.println("```");
            for (String line : NetStats.format(total, Integer.MAX_VALUE)) {
                out.println(line);
            }
            out.println("```");
            out.println();
            out.println("Columns: packets sent/received by the server; bytes-out/bytes-in are compressed frame payloads; "
                  + "raw-out is before gzip; max-out is the largest single frame; times are totals in ms.");
            out.println();
            out.println("## Per phase");
            out.println();
            out.println("| round | phase | ms | packets | bytes | busiest |");
            out.println("| --- | --- | --- | --- | --- | --- |");
            synchronized (windows) {
                for (PhaseWindow window : windows) {
                    List<CommandStats> busiest = window.traffic().busiest();
                    StringBuilder top = new StringBuilder();
                    for (int i = 0; i < Math.min(3, busiest.size()); i++) {
                        CommandStats stats = busiest.get(i);
                        top.append(i > 0 ? ", " : "").append(stats.command()).append(' ').append(stats.wireBytes())
                              .append('/').append(stats.packets());
                    }
                    out.println(String.format(Locale.ROOT, "| %d | %s | %d | %d | %d | %s |", window.round(),
                          window.phase(), window.millis(), window.traffic().totalPackets(),
                          window.traffic().totalWireBytes(), top));
                }
            }
            out.println();
            out.println("## Per client");
            out.println();
            out.println("| client | packets out | bytes out | packets in | bytes in | slowest flush ms |");
            out.println("| --- | --- | --- | --- | --- | --- |");
            for (String line : clientLines) {
                out.println(line);
            }
            out.println();
            out.println("## Pump");
            out.println();
            out.println(String.format(Locale.ROOT, "%d packets dispatched, queue high-water %d, longest wait %.2f ms, "
                        + "slowest flush %.2f ms.", total.totalHandled(), total.pumpDepthMax(),
                  total.pumpWaitMaxNanos() / 1_000_000.0, total.maxFlushNanos() / 1_000_000.0));
        }
    }
}
