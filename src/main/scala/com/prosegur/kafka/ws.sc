val value = " -1"

val TENANT_ID: Integer = if (value.trim.contains("-1")) null else value.toInt