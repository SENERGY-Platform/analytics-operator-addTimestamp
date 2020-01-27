# analytics-operator-addTimestamp

Takes an input and puts it into an output field. Adds another output field 'timestamp' with the current timestamp in ISO 8610 format of a configurable timezone

## Inputs

* value (float|string): Reading from device. Configure type with 'inputType'

## Outputs

* output_value (float|string): Unchanged input. Configure type with 'inputType'
* timestamp (string): Current datetime in ISO 8601. Configure timezone with 'timezone'

## Configs
 * inputType (string): Can be either 'string' or 'float'. Default value is 'float'.
 * timezone (string): Can be anything able to be parsed by [ZoneId.of(String)](https://docs.oracle.com/javase/8/docs/api/java/time/ZoneId.html#of-java.lang.String-).
   Default value is 'Europe/Berlin'. Note that this doesn't change the actual time, it changes the time offset and the timestamp accordingly.