import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * @author mcw 2019\12\2 0002-15:23
 */
public class slf4Test {
    public static void main(String[] args) {
        Logger log=LoggerFactory.getLogger(slf4Test.class);
        log.debug("debug消息id={},name={}",1,"zhangsan");
        log.info("info...");//用于请求处理提示消息
        log.warn("warn...");//用于警告处理提示消息
        log.error("error...");//用于异常处理提示消息
        log.error("==>>"+log.getClass());
    }
}
