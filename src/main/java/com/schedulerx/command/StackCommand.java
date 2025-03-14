package com.schedulerx.command;

import static com.schedulerx.utils.Constants.RANDOM;
import static com.schedulerx.utils.Constants.RANDOM_BOUND;

import com.schedulerx.utils.Stack;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.collections4.CollectionUtils;

@RequiredArgsConstructor
@Slf4j
public class StackCommand implements Command {
  private static final String PUSH = "PUSH";
  private static final String POP = "POP";
  private static final String SHOW = "SHOW";
  private final String id;
  private final Stack<Integer> stack;

  @Override
  public String id() {
    return id;
  }

  @Override
  public void execute(final List<String> params) {
    if (CollectionUtils.isEmpty(params)) {
      log.error("The parameters are empty for a stack command");
    }
    params.forEach(this::executeStackCommand);
  }

  private void executeStackCommand(final String command) {
    if (POP.equals(command)) {
      val poppedElement = stack.pop();
      if (poppedElement == null) {
        log.error("Can't pop from an empty stack!");
      } else {
        log.info("Popped element: {}", poppedElement);
      }
    } else if (PUSH.equals(command)) {
      stack.push(RANDOM.nextInt(RANDOM_BOUND));
    } else if (SHOW.equals(command)) {
      stack.show();
    } else {
      log.error("Unsupported stack command encountered: {}", command);
    }
  }
}
