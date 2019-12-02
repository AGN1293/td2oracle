package com.prosegur.kafka

import com.prosegur.kafka.Model.Json
import org.apache.kafka.streams.{KeyValue, StreamsBuilder}
import org.slf4j.LoggerFactory
import com.prosegur.kafka.Transformation.{jsonToClass, formatEvents}

class MainTopology {

  val logger = LoggerFactory.getLogger(getClass)

  val createTopology = (sourceTopic: String, sinkTopic: String) => {
    val builder = new StreamsBuilder()
    builder.stream(sourceTopic)
      .map[String, Option[Json]]((key, value) => toJsonEvent(key, value))
      .map[String, String]((key, value) => toFormattedEvents(key, value))
      .to(sinkTopic)
    builder.build()
  }

  private val toJsonEvent = (key: String, value: String) => {
    val jsonEventsAsCaseClasses = jsonToClass(value)
    new KeyValue(key, jsonEventsAsCaseClasses)
  }

  private val toFormattedEvents = (key: String, value: Option[Json]) => {
    val jsonOutputEvent: String = formatEvents(value)
    new KeyValue(key,jsonOutputEvent)
  }

}
