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

import org.infai.ses.senergy.operators.Config;
import org.infai.ses.senergy.operators.Message;
import org.infai.ses.senergy.operators.OperatorInterface;
import org.joda.time.DateTimeUtils;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class AddTimestamp implements OperatorInterface {

    protected String inputTypeConfig;
    protected ZoneId timezone;

    public AddTimestamp(){
        this(new Config());
    }

    public AddTimestamp(Config config){
        inputTypeConfig = config.getConfigValue("inputType", "float");
        timezone = ZoneId.of(config.getConfigValue("timezone", "Europe/Berlin"));
    }

    @Override
    public void run(Message message) {
        Object value;
        if(inputTypeConfig.equals("string")){
            value = message.getInput("value").getString();
        } else {
            value = message.getInput("value").getValue();
        }
        message.output("output_value", value);

        long currentMillis = DateTimeUtils.currentTimeMillis(); //Needs to use this method for testing
        Instant instant = Instant.ofEpochMilli(currentMillis);
        ZonedDateTime zdt = ZonedDateTime.ofInstant(instant, timezone);

        message.output("timestamp", zdt.toOffsetDateTime().toString());
    }

    @Override
    public void configMessage(Message message) {
        message.addInput("value");
    }
}
