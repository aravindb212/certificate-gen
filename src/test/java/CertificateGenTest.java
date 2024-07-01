import java.io.IOException;

import org.junit.Test;

import com.abc.Main;

public class CertificateGenTest {
    @Test
    public void test() throws IOException {
        Main.generateCertificate("username", "companyName");
    }

}
