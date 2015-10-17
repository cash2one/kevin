package com.fuda.dc.lhtz.web.monitor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 当被监控的节点被删除时，说明当前服务可能有有问题，需要发邮件告知开发人员
 * 
 * @author liukai
 *
 */
public class Monitor {

    private static final Logger logger = LoggerFactory.getLogger(Monitor.class);


    /**
     * 当被监控的节点被删除时，说明当前服务可能有有问题，需要发邮件告知开发人员
     * 
     * @param path
     * @param data
     */
    private void sendMail(String path, String data) {
        logger.info("send mail path={}, data={}", path, data);
    }


	public void start() {
		sendMail("", "");
	}
}
