package cn.mzmzup.entity;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "cn.mzmzup")
public class ApiLockProperties {
    /**
     * redis ip lock key
     */
    private  String LOCK_IP_KEY_IP_="LOCK_IP_KEY_IP_{}";
    /**
     * redis req count key 60秒请求数量
     */
    private String LOCK_IP_REQ_KEY_COUNT_= "LOCK_IP_REQ_KEY_COUNT_{}";
    /**
     * redis  存接触请求锁的验证码的key
     */
    private String LOCK_IP_REQ_KEY_CODE_= "LOCK_IP_REQ_KEY_CODE_{}";
    /**
     * key 存储时间
     */
    private int LOCK_TIME= 60;
    /**
     * 在 {@see LOCK_TIME} 时间内请求的阈值
     */
    private int count_threshold_value=120;
    /**
     *  RedisTemplate<String,Object> 对象名字
     */
    private String redisTemplateName = "redisTemplate";

    public String getLOCK_IP_KEY_IP_() {
        return LOCK_IP_KEY_IP_;
    }

    public void setLOCK_IP_KEY_IP_(String LOCK_IP_KEY_IP_) {
        this.LOCK_IP_KEY_IP_ = LOCK_IP_KEY_IP_;
    }

    public String getLOCK_IP_REQ_KEY_COUNT_() {
        return LOCK_IP_REQ_KEY_COUNT_;
    }

    public void setLOCK_IP_REQ_KEY_COUNT_(String LOCK_IP_REQ_KEY_COUNT_) {
        this.LOCK_IP_REQ_KEY_COUNT_ = LOCK_IP_REQ_KEY_COUNT_;
    }

    public String getLOCK_IP_REQ_KEY_CODE_() {
        return LOCK_IP_REQ_KEY_CODE_;
    }

    public void setLOCK_IP_REQ_KEY_CODE_(String LOCK_IP_REQ_KEY_CODE_) {
        this.LOCK_IP_REQ_KEY_CODE_ = LOCK_IP_REQ_KEY_CODE_;
    }

    public int getLOCK_TIME() {
        return LOCK_TIME;
    }

    public void setLOCK_TIME(int LOCK_TIME) {
        this.LOCK_TIME = LOCK_TIME;
    }

    public int getCount_threshold_value() {
        return count_threshold_value;
    }

    public void setCount_threshold_value(int count_threshold_value) {
        this.count_threshold_value = count_threshold_value;
    }

    public String getRedisTemplateName() {
        return redisTemplateName;
    }

    public void setRedisTemplateName(String redisTemplateName) {
        this.redisTemplateName = redisTemplateName;
    }
}
