package org.an4er.agent;

import java.io.IOException;
import java.lang.instrument.Instrumentation;

public class Monitor {
    public static void premain(String agentOps, Instrumentation inst) throws IOException {
        AopTransformer transformer = new AopTransformer();
        inst.addTransformer(transformer, true);
    }
}
