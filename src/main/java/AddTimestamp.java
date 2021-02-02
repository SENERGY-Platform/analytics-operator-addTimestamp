/*
 *
 *  * Copyright 2019 InfAI (CC SES)
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *     http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

import org.infai.ses.senergy.exceptions.NoValueException;
import org.infai.ses.senergy.operators.*;
import org.joda.time.DateTimeUtils;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class AddTimestamp extends BaseOperator {

    protected String inputTypeConfig;
    protected ZoneId timezone;
    protected boolean debug;

    public AddTimestamp(){
        this(new Config());
    }

    public AddTimestamp(Config config){
        inputTypeConfig = config.getConfigValue("inputType", "float");
        timezone = ZoneId.of(config.getConfigValue("timezone", "Europe/Berlin"));
        debug = Boolean.parseBoolean(Helper.getEnv("DEBUG", "false"));
    }

    @Override
    public void run(Message message) {
        Object value;
        if(inputTypeConfig.equals("string")){
            try {
                value = message.getFlexInput("value").getString();
            } catch (NoValueException e) {
                e.printStackTrace();
                return;
            }
        } else {
            try {
                value = message.getFlexInput("value").getValue();
            } catch (NoValueException e) {
                e.printStackTrace();
                return;
            }
        }
        message.output("output_value", value);

        long currentMillis = DateTimeUtils.currentTimeMillis(); //Needs to use this method for testing
        Instant instant = Instant.ofEpochMilli(currentMillis);
        ZonedDateTime zdt = ZonedDateTime.ofInstant(instant, timezone);

        String timestamp = zdt.toOffsetDateTime().toString();
        message.output("timestamp", timestamp);
        if (debug) {
            System.out.println("Read value: " + value.toString());
            System.out.println("Output timestamp: " + timestamp);
        }
    }

    @Override
    public Message configMessage(Message message) {
        message.addFlexInput("value");
        return message;
    }
}
