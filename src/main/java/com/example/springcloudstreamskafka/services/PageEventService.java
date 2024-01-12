package com.example.springcloudstreamskafka.services;

import com.example.springcloudstreamskafka.entities.PageEevent;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Service
public class PageEventService {
    @Bean
    public Consumer<PageEevent> pageEeventConsumer(){
        return (input)->{
            System.out.println("*****************************");
            System.out.println(input.toString());
            System.out.println("*******************************");
        };
    }

    @Bean
    public Supplier<PageEevent> pageEeventSupplier(){
        return ()-> new  PageEevent(Math.random()>0.5?"P1":"P2",Math.random()>0.5?"U1":"U2",new Date(), new Random().nextInt(9000));
    }

    @Bean
    public Function<PageEevent,PageEevent> pageEeventFunction(){
        return (input)->{
            input.setName("Page Event");
            input.setUser("UUUUUU");
            return input;
        };
    }

    @Bean
    public Function<KStream<String,PageEevent>,KStream<String,Long>> KStreamFunction(){
        return (input)->{
            return input
                    .filter((k,v)->v.getDuration()>100)
                    .map((k,v)->new KeyValue<>(v.getName(),0L))
                    .groupBy((k,v)->k,Grouped.with(Serdes.String(),Serdes.Long()))
                    .count()
                    .toStream();
        };
    }
}
