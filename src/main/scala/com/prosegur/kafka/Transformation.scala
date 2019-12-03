package com.prosegur.kafka

import com.prosegur.kafka.Model.{Field, Json, JsonOutput, PayloadInput, PayloadOutput, Schema}
import io.circe.Printer
import org.slf4j.{Logger, LoggerFactory}
import io.circe.parser.decode
import io.circe.generic.auto._
import io.circe.syntax._

object Transformation {

  val intFields: List[String] = List("TENANT_ID", "ID", "COUNTRYID", "CENTROID", "CREATEDBY", "EDITEDBY", "DELETEDBY", "USUARIOID", "CLIENTID", "BASEID")

  val printer: Printer = Printer.noSpaces.copy(dropNullValues = true)

  val logger: Logger = LoggerFactory.getLogger(getClass)

  def jsonToClass(input: String): Option[Json] = {
    val decoded = decode[Json](input)
    decoded match {
      case Right(msg) =>
        logger.info(s"[TD2ORACLE_OSUSR_7WK_DEVICE] Processing message with ID: {${msg.payload.ID}}")
        Some(msg)
      case Left(left) =>
        logger.debug(s"[TD2ORACLE_OSUSR_7WK_DEVICE] Error: $left")
        logger.debug(s"[TD2ORACLE_OSUSR_7WK_DEVICE] Couldn't parse the message: $input")
        None
    }
  }

  def formatEvents(json: Option[Json]): String = json match {
    case Some(value) =>
      val newFields = changeSchemaTypes(value.schema.fields)
      val newSchema = value.schema.copy(fields = newFields)
      val newPayload = changePayload(value.payload)
      val jsonOutput = JsonOutput(newSchema, newPayload).asJson.printWith(printer)
      jsonOutput.toString
  }

  def changeSchemaTypes(value: List[Field]): List[Field] = value.map(x => if (intFields.contains(x.field)) x.copy(`type` = "int64") else x)

  def changePayload(value: PayloadInput): PayloadOutput = {
    PayloadOutput(
      value.TENANT_ID.toInt,
      value.ID.toInt,
      value.IMEI,
      value.PHONENUMBER,
      value.MAC,
      value.MODEL,
      value.DESCRIPTION,
      value.LASTACCESSON,
      value.OBSERVATIONS,
      value.OS,
      value.COUNTRYID.toInt,
      value.CENTROID.toInt,
      value.CREATEDBY.toInt,
      value.EDITEDBY.toInt,
      value.EDITEDON,
      value.DELETEDBY.toInt,
      value.DELETEDON,
      value.USUARIOID.toInt,
      value.CLIENTID.toInt,
      value.BASEID.toInt,
      value.POS,
      value.GG_T_TYPE_ORIGIN,
      value.GG_T_TIMESTAMP_ORIGIN,
      value.TD_T_TIMESTAMP_ORIGIN
    )
  }

}
