package com.schedulerx.utils;

import com.cronutils.model.CronType;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.parser.CronParser;
import java.util.Random;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {
  public static final CronParser CRON_PARSER =
      new CronParser(CronDefinitionBuilder.instanceDefinitionFor(CronType.UNIX));

  /** Number of next jobs to schedule for a command. */
  public static final int JOB_COUNT = 5;

  public static final int THREAD_POOL_SIZE = 10;
  public static final int SECONDS_AGO = 30;
  public static final int SECONDS_LATER = 1;
  public static final Random RANDOM = new Random();
  public static final int RANDOM_BOUND = 1000;
  public static final int STACK_SIZE = 100;
}
