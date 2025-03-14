package com.schedulerx.utils;

import com.cronutils.model.Cron;
import com.cronutils.model.time.ExecutionTime;
import com.schedulerx.exception.SchedulerXError;
import com.schedulerx.exception.SchedulerXException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.experimental.UtilityClass;
import lombok.val;

/** Utility class for handling cron-related operations. */
@UtilityClass
public class CronUtils {
  /**
   * Returns the next execution times based on the cron expression.
   *
   * @param count the number of next execution times to be returned
   * @return the list of next execution times for a given cron expression
   */
  public static List<LocalDateTime> getNextExecutionTimes(final Cron cron, final int count) {

    val executionTime = ExecutionTime.forCron(cron);
    val nextExecutionTimes = new ArrayList<LocalDateTime>();
    var nextTime = ZonedDateTime.now(ZoneOffset.UTC);

    for (var i = 0; i < count; i++) {
      nextTime =
          executionTime
              .nextExecution(nextTime)
              .orElseThrow(() -> new SchedulerXException(SchedulerXError.INTERNAL_SERVER_ERROR));
      nextExecutionTimes.add(nextTime.toLocalDateTime());
    }

    return nextExecutionTimes;
  }
}
