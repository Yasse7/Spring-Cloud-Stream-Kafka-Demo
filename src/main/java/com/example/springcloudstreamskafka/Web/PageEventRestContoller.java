package com.example.springcloudstreamskafka.Web;

import com.example.springcloudstreamskafka.entities.PageEevent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Random;

@RestController
public class PageEventRestContoller {

    @Autowired
    private StreamBridge streamBridge;

    @GetMapping("/publish/{topic}/{name}")
    public PageEevent publish(@PathVariable String name,@PathVariable String topic){
        PageEevent pageEevent = new PageEevent(name,Math.random()>0.5?"U1":"U2",new Date(), new Random().nextInt(9000));
    streamBridge.send(topic,pageEevent );
    return pageEevent;

    }

}
