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
import org.infai.seits.sepl.operators.OperatorInterface;

public class AddTimestamp implements OperatorInterface {

    Config config = new Config();

    public AddTimestamp(){}

    public AddTimestamp(Config config){
        this.config = config;
    }

    @Override
    public void run(Message message) {
        String configValue = config.getConfigValue("inputType", "float");
        Object value;
        if(configValue.equals("string")){
            value = message.getInput("value").getString();
        } else {
            value = message.getInput("value").getValue();
        }
        message.output("value", value);
    }

    @Override
    public void config(Message message) {
        message.addInput("value");
    }
}
