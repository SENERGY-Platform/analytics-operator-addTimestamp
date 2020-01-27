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

import org.infai.seits.sepl.operators.Config;
import org.infai.seits.sepl.operators.Message;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class AddTimestampTest {

    @Test
    public void testFloatValues() throws Exception {
        AddTimestamp addTimestamp = new AddTimestamp();
        List<Message> messages = TestMessageProvider.getTestMesssagesSet( "src/test/resources/sample-data-small.json");
        for (int i = 0; i < messages.size(); i++) {
            Message m = messages.get(i);
            addTimestamp.config(m);
            addTimestamp.run(m);
            double valueActual = m.getInput("value").getValue();
            double valueExpected = Double.parseDouble(m.getMessageString().split("value\":")[1].split("}")[0]);
            Assert.assertEquals(valueExpected, valueActual, 0.01);
        }
    }

    @Test
    public void testStringValues() throws Exception {
        JSONObject config =TestMessageProvider.getConfig();
        AddTimestamp addTimestamp = new AddTimestamp(new Config(config.toString()));
        List<Message> messages = TestMessageProvider.getTestMesssagesSet( "src/test/resources/sample-data-small-2.json");
        for (int i = 0; i < messages.size(); i++) {
            Message m = messages.get(i);
            addTimestamp.config(m);
            addTimestamp.run(m);

            String valueActual = m.getInput("value").getString();
            String valueExpected = m.getMessageString().split("value\":\"")[1].split("\"}")[0];
            Assert.assertEquals(valueExpected, valueActual);
        }
    }
}
