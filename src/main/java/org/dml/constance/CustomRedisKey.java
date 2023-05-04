package org.dml.constance;

public class CustomRedisKey {
    public static final String NODE = "NODE";
    public static final String RELATIONSHIP = "RELATIONSHIP";
    public static final String RELATIONSHIP_LABEL = "RELATIONSHIP_LABEL_";

    // 使用pipeline模式下的buffer大小，即每多少条command执行一次pipeline
    // 尝试了每一百万条执行一次，但无法执行成功
    public static final int PIPELINE_FLUSH_POLICY_BUFFERSIZE = 200000;
}
