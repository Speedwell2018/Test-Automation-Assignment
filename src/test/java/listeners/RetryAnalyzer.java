package listeners;

import io.github.cdimascio.dotenv.Dotenv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {
    private final Logger log = LoggerFactory.getLogger(RetryAnalyzer.class);

    private int retryCount = 0;
    private final int maxRetries;
    private static final Dotenv dotenv = Dotenv.configure()
            .directory(System.getProperty("user.dir"))
            .ignoreIfMissing()
            .load();

    public RetryAnalyzer() {
        String retries = dotenv.get("TEST_RETRIES", "1");
        maxRetries = Integer.parseInt(retries);
        log.info("RetryAnalyzer initialized with maxRetries = {}", maxRetries);
    }

    @Override
    public boolean retry(ITestResult result) {
        if (retryCount < maxRetries) {
            log.warn("Retrying test '{}' (attempt {}/{})",
                    result.getName(),
                    retryCount + 1,
                    maxRetries
            );
            retryCount++;
            return true;
        }

        return false;
    }
}
