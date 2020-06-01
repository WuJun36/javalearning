package learnthread.threadlocal;

import java.util.ArrayList;
import java.util.List;

public class PerformanceTracker {
    private static class Phase {
        public Phase(String name, long duration) {
            this.name = name;
            this.duration = duration;
        }

        private String name;
        private long duration;

        @Override
        public String toString() {
            return "Phase{" +
                    "name='" + name + '\'' +
                    ", duration=" + duration +
                    '}'+'\n';
        }
    }

    private static final ThreadLocal<List<Phase>> PHASES = new ThreadLocal<>();
    private static final ThreadLocal<Long> PHASE_START_TIME = new ThreadLocal<>();

    public static void reset() {
        PHASES.set(new ArrayList<>());
        PHASE_START_TIME.set(-1L);
    }

    public static void startPhase() {
        PHASE_START_TIME.set(System.currentTimeMillis());
    }

    public static void endPhase(String phaseName){
        long start = PHASE_START_TIME.get();
        PHASES.get().add(new Phase(phaseName,System.currentTimeMillis() - start));
    }

    public static void finish(){
        List<Phase> phases = PHASES.get();
        PHASES.set(null);
        PHASE_START_TIME.set(null);

        StringBuilder out = new StringBuilder("------Thread Execution Phase Durations---------\n");
        phases.forEach(out::append);
        out.append("---------------------------------------\n");
        System.out.println(out.toString());
    }
}
