package com.prosegur.kafka

import java.util.Properties
import java.util.concurrent.TimeUnit

import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.{KafkaStreams, StreamsConfig}
import org.slf4j.LoggerFactory

object Main extends App {
  private val logger = LoggerFactory.getLogger(getClass)

  val sourceTopic = "PENDING"
  val sinkTopic = "PENDING"

  val appName = "TD2ORACLE_OSUSR_7WK_DEVICE"
  val bootstrapServers = "ESDC1CSPLA290:9092"

  val config: Properties = {
    val p = new Properties()
    p.put(StreamsConfig.APPLICATION_ID_CONFIG, appName)
    p.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers)
    p.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String.getClass)
    p.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass)
    p
  }

  val mainTopology = new MainTopology()

  val streams: KafkaStreams = new KafkaStreams(mainTopology.createTopology(sourceTopic, sinkTopic), config)
  logger.info(s"[$appName] [KAFKA STREAMS] Starting streaming")
  streams.start()

  sys.ShutdownHookThread {
    streams.close(10, TimeUnit.SECONDS)
  }

}
