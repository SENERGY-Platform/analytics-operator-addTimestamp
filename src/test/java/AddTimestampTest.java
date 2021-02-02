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
import org.infai.ses.senergy.models.DeviceMessageModel;
import org.infai.ses.senergy.models.MessageModel;
import org.infai.ses.senergy.operators.Config;
import org.infai.ses.senergy.operators.Helper;
import org.infai.ses.senergy.operators.Message;
import org.infai.ses.senergy.operators.OperatorInterface;
import org.infai.ses.senergy.testing.utils.JSONHelper;
import org.infai.ses.senergy.utils.ConfigProvider;
import org.joda.time.DateTimeUtils;
import org.json.simple.JSONArray;
import org.junit.Assert;
import org.junit.Test;

public class AddTimestampTest {

    @Test
    public void testFloatValues() throws Exception {
        DateTimeUtils.setCurrentMillisFixed(0);
        Config config = new Config(new JSONHelper().parseFile("config-numbers.json").toString());
        JSONArray messages = new JSONHelper().parseFile("numbers.json");
        String topicName = config.getInputTopicsConfigs().get(0).getName();
        ConfigProvider.setConfig(config);
        Message message = new Message();
        MessageModel model = new MessageModel();
        OperatorInterface testOperator = new AddTimestamp();
        testOperator.configMessage(message);
        for (Object msg : messages) {
            DeviceMessageModel deviceMessageModel = JSONHelper.getObjectFromJSONString(msg.toString(), DeviceMessageModel.class);
            assert deviceMessageModel != null;
            model.putMessage(topicName, Helper.deviceToInputMessageModel(deviceMessageModel, topicName));
            message.setMessage(model);
            testOperator.run(message);
            Assert.assertEquals(message.getFlexInput("value").getValue(), message.getMessage().getOutputMessage().getAnalytics().get("output_value"));
            Assert.assertEquals("1970-01-01T01:00+01:00", message.getMessage().getOutputMessage().getAnalytics().get("timestamp"));
        }
    }

    @Test
    public void testStringValues() throws NoValueException {
        DateTimeUtils.setCurrentMillisFixed(0);
        Config config = new Config(new JSONHelper().parseFile("config-strings.json").toString());
        JSONArray messages = new JSONHelper().parseFile("strings.json");
        String topicName = config.getInputTopicsConfigs().get(0).getName();
        ConfigProvider.setConfig(config);
        Message message = new Message();
        MessageModel model = new MessageModel();
        OperatorInterface testOperator = new AddTimestamp(config);
        testOperator.configMessage(message);
        for (Object msg : messages) {
            DeviceMessageModel deviceMessageModel = JSONHelper.getObjectFromJSONString(msg.toString(), DeviceMessageModel.class);
            assert deviceMessageModel != null;
            model.putMessage(topicName, Helper.deviceToInputMessageModel(deviceMessageModel, topicName));
            message.setMessage(model);
            testOperator.run(message);

            Assert.assertEquals(message.getFlexInput("value").getString(), message.getMessage().getOutputMessage().getAnalytics().get("output_value"));
            Assert.assertEquals("1970-01-01T01:00+01:00", message.getMessage().getOutputMessage().getAnalytics().get("timestamp"));
        }
    }
}
