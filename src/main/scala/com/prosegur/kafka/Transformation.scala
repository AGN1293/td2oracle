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
    val TENANT_ID: Integer = if (value.TENANT_ID.trim.contains("-1")) null else value.TENANT_ID.toInt
    val ID: Integer = if (value.ID.trim.contains("-1")) null else value.ID.toInt
    val COUNTRYID: Integer = if (value.COUNTRYID.trim.contains("-1")) null else value.COUNTRYID.toInt
    val CENTROID: Integer = if (value.CENTROID.trim.contains("-1")) null else value.CENTROID.toInt
    val CREATEDBY: Integer = if (value.CREATEDBY.trim.contains("-1")) null else value.CREATEDBY.toInt
    val EDITEDBY: Integer = if (value.EDITEDBY.trim.contains("-1")) null else value.EDITEDBY.toInt
    val DELETEDBY: Integer = if (value.DELETEDBY.trim.contains("-1")) null else value.DELETEDBY.toInt
    val USUARIOID: Integer = if (value.USUARIOID.trim.contains("-1")) null else value.USUARIOID.toInt
    val CLIENTID: Integer = if (value.CLIENTID.trim.contains("-1")) null else value.CLIENTID.toInt
    val BASEID: Integer = if (value.BASEID.trim.contains("null") || value.BASEID.trim.isEmpty) null else value.BASEID.toInt
    PayloadOutput(
      TENANT_ID,
      ID,
      value.IMEI,
      value.PHONENUMBER,
      value.MAC,
      value.MODEL,
      value.DESCRIPTION,
      value.LASTACCESSON,
      value.OBSERVATIONS,
      value.OS,
      COUNTRYID,
      CENTROID,
      CREATEDBY,
      value.CREATEDON,
      EDITEDBY,
      value.EDITEDON,
      DELETEDBY,
      value.DELETEDON,
      USUARIOID,
      CLIENTID,
      BASEID,
      value.POS,
      value.GG_T_TYPE,
      value.GG_T_TIMESTAMP,
      value.TD_T_TIMESTAMP
    )
  }

}
