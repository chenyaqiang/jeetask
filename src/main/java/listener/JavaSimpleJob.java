/*
 * Copyright 1999-2015 dangdang.com.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * </p>
 */

package listener;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;


import java.util.Date;
import java.util.List;

public class JavaSimpleJob implements SimpleJob {

    private FooRepository fooRepository = FooRepositoryFactory.getFooRepository();

    @Override
    public void execute(final ShardingContext shardingContext) {
        System.out.println(String.format("------Thread ID: %s, Date: %s, Sharding Context: %s, Action: %s", Thread
                .currentThread().getId(), new Date(), shardingContext, "simple job"));

        String shardingParameter = shardingContext.getShardingParameter();


        List<Foo> data = fooRepository.findTodoData(shardingParameter, 10);
        for (Foo each : data) {
            fooRepository.setCompleted(each.getId());
        }
        System.out.println(" shardingParameter  =  " + shardingParameter);

        while (true) {
            try {
                System.out.println("系统休眠10秒后继续休眠");
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
