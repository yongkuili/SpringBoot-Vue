package com.liyk.server.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.liyk.server.pojo.Employee;
import com.liyk.server.pojo.MailLog;
import com.liyk.server.service.IEmployeeService;
import com.liyk.server.service.IMailLogService;
import com.liyk.server.utils.MailConstants;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author LiYongkui
 * @title: MailTask
 * @description: 邮件发送定时任务
 * @date 2021-06-18 8:40
 */
//@Component
public class MailTask {
    @Autowired
    private IMailLogService mailLogService;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private IEmployeeService employeeService;

    /**
     * 邮件发送定时任务
     * 10秒一次
     */
    @Scheduled(cron = "0/10 * * * * ?")
    public void mailTask(){
        //状态为0且重试时间小于当前时间的才需要重新发送
        List<MailLog> list = mailLogService.list(new QueryWrapper<MailLog>().eq("status", 0).lt("tryTime", LocalDateTime.now()));
        list.forEach(mailLog -> {
            //重试次数超过3次，更新为投递失败，不再重试
            if (3 <= mailLog.getCount()){
                mailLogService.update(new UpdateWrapper<MailLog>().set("status",2).eq("msgId",mailLog.getMsgId()));
            }
            //更新重试次数，更新时间，重试时间
            mailLogService.update(new UpdateWrapper<MailLog>().set("count",mailLog.getCount()+1).set("updateTime",LocalDateTime.now())
                    .set("tryTime",LocalDateTime.now().plusMinutes(MailConstants.MSG_TIMEOUT)).eq("msgId",mailLog.getMsgId()));
            Employee employee = employeeService.getEmployee(mailLog.getEid()).get(0);
            System.out.println("MailTask: employee = " + employee );
            System.out.println("MailTask: msgId: " + mailLog.getMsgId());
            //发送消息
            rabbitTemplate.convertAndSend(MailConstants.MAIL_EXCHANGE_NAME,MailConstants.MAIL_ROUTING_KEY_NAME,employee,new CorrelationData(mailLog.getMsgId()));
        });
    }

}

