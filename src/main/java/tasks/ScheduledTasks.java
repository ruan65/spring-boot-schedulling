package tasks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * Created by a on 1/25/17.
 */
@Component
public class ScheduledTasks {

    public static final String testBashCommand = "/home/a/dev/bash/pop";
    public static final String contractorRunLoginLogoutTestBashCommand =
            "/home/a/Android/Sdk/platform-tools/adb shell am instrument -w -r -e debug false -e class com.redsys.tpsmobile.TestLoginLogoutLiveMode com.redsys.tpsmobile.test/android.support.test.runner.AndroidJUnitRunner";

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

//    @sScheduled(cron = "*/30 * * * * *")
//    public void reportCurrentTime() {
//        log.info("The time is now {}", dateFormat.format(new Date()));
//    }

    @Scheduled(cron = "0 * * * * *")
    public void getResultBack() {

        try {
            log.info(
                    execCommand(testBashCommand)
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Scheduled(cron = "0 * * * * *")
    public void contractorRunLoginLogoutTestBashCommand() {

        log.info("************************ Start test Login/Logout Time: {} *******************************",
                dateFormat.format(new Date()));
        try {
            log.info(
                    execCommand(contractorRunLoginLogoutTestBashCommand)
            );
        } catch (IOException e) {
            e.printStackTrace();
            log.info("Something went wrong: {}", e.getMessage());
        }
        log.info("************************ End test Login/Logout Time: {} ******************************",
                dateFormat.format(new Date()));

    }

    public static String execCommand(String cmd) throws IOException {

        Scanner scanner = new Scanner(Runtime.getRuntime().exec(cmd).getInputStream()).useDelimiter("\\A");

        return scanner.hasNext() ? scanner.next() : "";
    }
}
