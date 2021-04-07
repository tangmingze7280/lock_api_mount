package cn.mzmzup.awt;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ShearCaptcha;
import cn.hutool.core.util.StrUtil;
import cn.mzmzup.entity.ApiLockProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

@Component
@EnableConfigurationProperties(ApiLockProperties.class)
public class TokenAwt {
    private final ApiLockProperties properties;
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    public TokenAwt(@Autowired ApiLockProperties properties) {
        this.properties = properties;
    }

    /**
     * 请求ip
     * @author tmz
     * @description
     * @date 14:28 2021/4/7
     * @param request
     * @return java.lang.String
     */
    public String getRemortIP(HttpServletRequest request) {
        if (request.getHeader("x-forwarded-for") == null) {
            return request.getRemoteAddr();
        }
        return request.getHeader("x-forwarded-for");
    }

    public boolean isIpLock(String ip){
        if (redisTemplate.hasKey(StrUtil.format(properties.getLOCK_IP_KEY_IP_(),ip))) {
            return true;
        }
        return false;
    }
    /**
     * 锁请求
     * @author tmz
     * @description
     * @date 15:19 2021/4/7
     * @param [ip]
     * @return boolean
     */
    public boolean addReqCount(String ip){
        if (isIpLock(ip)){
            return false;
        }
        String key = StrUtil.format(properties.getLOCK_IP_REQ_KEY_COUNT_(),ip);
        ValueOperations<String, Object> ops = redisTemplate.opsForValue();
        if(redisTemplate.hasKey(key)) {
            Long increment = ops.increment(key, 1);
            if (increment>=properties.getCount_threshold_value()){
                String lockKey = StrUtil.format(properties.getLOCK_IP_KEY_IP_(),ip);
                ops.set(lockKey,1, properties.getLOCK_TIME(), TimeUnit.SECONDS);
                return false;
            }
        }else{
            ops.set(key, 1, properties.getLOCK_TIME(), TimeUnit.SECONDS);
        }
        return true;
    }
    /**
     * 返回验证码
     * @author tmz
     * @description
     * @date 15:58 2021/4/7
     * @param [req, res]
     * @return void
     */
    public void  verification(HttpServletRequest req,  HttpServletResponse res){
        try (ServletOutputStream outputStream = res.getOutputStream()) {
            String key = StrUtil.format(properties.getLOCK_IP_REQ_KEY_CODE_(),getRemortIP(req));
            ShearCaptcha captcha = CaptchaUtil.createShearCaptcha(300, 100, 4, 3);
            redisTemplate.opsForValue().set(key,captcha.getCode(),properties.getLOCK_TIME(),TimeUnit.SECONDS);
            captcha.write(outputStream);
        } catch (Exception e) {
        }
    }
    /**
     * 检查验证码并且解锁api请求锁
     * @author tmz
     * @description
     * @date 15:58 2021/4/7
     * @param [req, code]
     * @return boolean
     */
    public boolean checkAndUnlock(HttpServletRequest req,String code){
        String ip = getRemortIP(req);
        String key = StrUtil.format(properties.getLOCK_IP_REQ_KEY_CODE_(),ip);
        if(redisTemplate.hasKey(key)){
            String vcode = (String) redisTemplate.opsForValue().get(key);
            if (StrUtil.equals(code,vcode)) {
                redisTemplate.delete(StrUtil.format(properties.getLOCK_IP_KEY_IP_(),ip));
                redisTemplate.delete(StrUtil.format(properties.getLOCK_IP_REQ_KEY_COUNT_(),ip));
                return true;
            }
            return false;
        }
        return false;
    }

}
