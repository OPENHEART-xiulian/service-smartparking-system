package com.sp.cjgc;

import com.sp.cjgc.backend.vehiclebilling.CaptureDataUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SmartparkingApplicationTests {


    @Test
    void contextLoads() {
        
        String cameraIp = "192.88.233.19";
        short wLinkPort = 8000;
        String sLicense = "粤A11111";

        CaptureDataUtil.createCaptureData(sLicense, cameraIp, wLinkPort);
    }
}
