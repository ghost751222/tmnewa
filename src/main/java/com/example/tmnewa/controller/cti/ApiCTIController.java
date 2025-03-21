package com.example.tmnewa.controller.cti;

import com.example.tmnewa.service.DNRoutineService;
import com.example.tmnewa.utils.IDValidatorUtils;
import com.example.tmnewa.utils.ToolUtils;
import com.example.tmnewa.vo.cti.CTIResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/cti")
@Slf4j
public class ApiCTIController {


//    https://localhost:8888/tmnewa/api/cti/IdResult?ID=123456789
//    https://localhost:8888/tmnewa/api/cti/IdCheckResult?ID=123456789
//
//    https://localhost:8888/tmnewa/api/cti/SMS?Ani=0987654321&Service=366001&Phone=0988809097
//    https://localhost:8888/tmnewa/api/cti/Holiday?Service=6001
    @Autowired
    DNRoutineService dnRoutineService;

    @Value("${tmnewa.smsIp:smsb2c.mitake.com.tw}")
    String smsIp;
    @Value("${tmnewa.smsId:crm4527}")
    String smsId;
    @Value("${tmnewa.smsPassword:ec120823}")
    String smsPassword;

    @GetMapping("/IdResult")
    public String IdResult(@RequestParam(value = "ID") String id) {
        var ctiResponseVo = new CTIResponseVo();
        ctiResponseVo.setRet(-1);
        try {
            String[] outStream = ToolUtils.IdentifyPrefix(id);
            if (outStream.length > 0) {
                ctiResponseVo.setRet(0);
                ctiResponseVo.addVar("v_IdEn", String.join(Strings.EMPTY, outStream));
            }
        } catch (Exception e) {
            log.error(String.valueOf(e));
        }
        return ctiResponseVo.toString();
    }

    @GetMapping("/IdCheckResult")
    public String IdCheckResult(@RequestParam(value = "ID") String id) {
        var ctiResponseVo = new CTIResponseVo();
        ctiResponseVo.setRet(-1);
        try {
            if (IDValidatorUtils.isValidTWID(id) || IDValidatorUtils.isValidARC(id) || IDValidatorUtils.isValidUniformNumber(id)) {
                ctiResponseVo.setRet(0);
                ctiResponseVo.addVar("v_IdCheck", "ok");
            }
        } catch (Exception e) {
            log.error(String.valueOf(e));
        }
        return ctiResponseVo.toString();
    }

    @GetMapping("/Holiday")
    public String holiday(@RequestParam(value = "Service") String service) {
        var ctiResponseVo = new CTIResponseVo();
        ctiResponseVo.setRet(-1);
        try {
            LocalDateTime localDateTime = LocalDateTime.now();
            LocalTime localTime = localDateTime.toLocalTime();
            String dayOfWeek = localDateTime.getDayOfWeek().name();
            log.info("{}  ,{},{}", localDateTime, localTime,dayOfWeek);
        } catch (Exception e) {
            log.error(String.valueOf(e));
        }
        return ctiResponseVo.toString();
    }

    @GetMapping("/SMS")
    public String sms(@RequestParam Map<String, String> map) {
        var ctiResponseVo = new CTIResponseVo();
        ctiResponseVo.setRet(-1);
        try {
            String ani = map.get("Ani");
            String Service = map.get("Service");
            String Phone = map.get("Phone");

            String params = "username=" + smsId +
                    "&password=" + smsPassword +
                    "&dstaddr=" + Phone +
                    "&smbody=簡訊SmSend測試";
            URL url = new URL("https://" + smsIp + "/b2c/mtk/SmSend?CharsetURL=UTF-8");
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            urlConnection.setDoOutput(true);
            urlConnection.connect();
            DataOutputStream dos = new DataOutputStream(urlConnection.getOutputStream());
            dos.write(params.getBytes(StandardCharsets.UTF_8));
            dos.flush();
            dos.close();

            var sb = getStringBuilder(urlConnection);
            log.info(sb);
            if (sb.contains("Error")) throw new Exception(sb.substring(sb.indexOf("Error")));
            ctiResponseVo.setRet(0);
            ctiResponseVo.addVar("v_SmsResult", "ok");
        } catch (Exception e) {
            log.error(String.valueOf(e));
        }
        return ctiResponseVo.toString();
    }

    private static String getStringBuilder(HttpsURLConnection urlConnection) throws IOException {
        BufferedReader br = null;
        if (100 <= urlConnection.getResponseCode() && urlConnection.getResponseCode() <= 399) {
            br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        } else {
            br = new BufferedReader(new InputStreamReader(urlConnection.getErrorStream()));
        }
        var sb = new StringBuilder();
        String output;
        while ((output = br.readLine()) != null) {
            sb.append(output);
        }
        return sb.toString();
    }

}
