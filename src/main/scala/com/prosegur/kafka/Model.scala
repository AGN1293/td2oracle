package com.prosegur.kafka

object Model {

  case class Json(
                        schema: Schema,
                        payload: PayloadInput
                      )

  case class JsonOutput(
                       schema: Schema,
                       payload: PayloadOutput
                       )

  case class Schema(
                     `type`: String,
                     fields: List[Field]
                   )

  case class Field(
                    `type`: String,
                    optional: Boolean,
                    name: Option[String],
                    version: Option[Int],
                    field: String
                  )

  case class PayloadInput(
                           TENANT_ID: String,
                           ID: String,
                           IMEI: String,
                           PHONENUMBER: String,
                           MAC: String,
                           MODEL: String,
                           DESCRIPTION: String,
                           LASTACCESSON: String,
                           OBSERVATIONS: String,
                           OS: String,
                           COUNTRYID: String,
                           CENTROID: String,
                           CREATEDBY: String,
                           CREATEDON: String,
                           EDITEDBY: String,
                           EDITEDON: String,
                           DELETEDBY: String,
                           DELETEDON: String,
                           USUARIOID: String,
                           CLIENTID: String,
                           BASEID: String,
                           POS: String,
                           GG_T_TYPE: String,
                           GG_T_TIMESTAMP: String,
                           TD_T_TIMESTAMP: String
                         )

  case class PayloadOutput(
                            TENANT_ID: Int,
                            ID: Int,
                            IMEI: String,
                            PHONENUMBER: String,
                            MAC: String,
                            MODEL: String,
                            DESCRIPTION: String,
                            LASTACCESSON: String,
                            OBSERVATIONS: String,
                            OS: String,
                            COUNTRYID: Int,
                            CENTROID: Int,
                            CREATEDBY: Int,
                            CREATEDON: String,
                            EDITEDBY: Int,
                            EDITEDON: String,
                            DELETEDBY: Int,
                            DELETEDON: String,
                            USUARIOID: Int,
                            CLIENTID: Int,
                            BASEID: Int,
                            POS: String,
                            GG_T_TYPE: String,
                            GG_T_TIMESTAMP: String,
                            TD_T_TIMESTAMP: String
                          )

}
