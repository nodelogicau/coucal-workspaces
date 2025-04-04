/*
 *  Copyright 2025 Node Logic
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package au.nodelogic.coucal.workspaces.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface FeedItemRepository extends JpaRepository<FeedItem, String> {

    List<FeedItem> findAllByOrderByPublishedDate();

    List<FeedItem> findByPublishedDateBetweenOrderByPublishedDateDesc(@Param("start") Date start,
                                                                      @Param("end") Date end);

    List<FeedItem> findByPublishedDateBefore(@Param("date") Date date);
    
    List<FeedItem> findByReadOrderByPublishedDateDesc(@Param("read") Boolean read);
}
