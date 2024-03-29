package org.an4er.agent;

import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class AopTransformer implements ClassFileTransformer {
    private boolean clearFlag = false;

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] classfileBuffer) {
        if (clearFlag) {
            AgentX.setTheNextJvmti(AgentX.ownJvmtiEnvAddr, 0);
            clearFlag = false;
        }

        if(!clearFlag){
            long nextJvmti = AgentX.getTheNextOfJvmtiEnv(AgentX.ownJvmtiEnvAddr);
            if (nextJvmti != 0) {
                long jvmtiPointer = AgentX.findTheJvmtiPointer(AgentX.ownJvmtiEnvAddr);
                if (jvmtiPointer != 0) {
                    AgentX.putLong(jvmtiPointer, nextJvmti);

                    long lastJvmti = AgentX.findTheLastJvmtiEnv();
                    AgentX.setTheNextJvmti(lastJvmti, AgentX.ownJvmtiEnvAddr);

                    clearFlag = true;
                }
            }
        }

        if (classBeingRedefined != null) {
            System.out.println("Class " + className + " has been modified.");
        }

        return null;
    }
}

