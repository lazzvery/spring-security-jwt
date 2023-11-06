package chat.jamsi.domain.translation.service;

import chat.jamsi.domain.translation.dto.TranslationDto;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.lang.model.type.ErrorType;

@Service
public class TranslationService {
    // 환경 변수를 이렇게 불러 오는게 맞는 관습인지 잘 모르겠음
    @Value("${spring.translation.clientId}")
    private String clientId;

    @Value("${spring.translation.clientSecret}")
    private String clientSecret;

    public List<String> translate(TranslationDto translationDto) {
        // request body에서 값들 추출
        String langFrom = translationDto.getLangFrom();
        String langTo = translationDto.getLangTo();
        String[] messages = translationDto.getMessages();

        // url 연결
        String apiURL = "https://openapi.naver.com/v1/papago/n2mt";

        // api 개인정보 헤더 설정
        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", this.clientId);
        requestHeaders.put("X-Naver-Client-Secret", this.clientSecret);

        List<String> translatedMessages = new ArrayList<String>();

        for(String message : messages) {
            // 연결 생성
            HttpURLConnection con = connect(apiURL);
            try {
                // 텍스트 인코딩
                try {
                    message = URLEncoder.encode(message, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException("인코딩 실패", e);
                }

                // parameter path 추가
                String postParams = "source=" + langFrom + "&target=" + langTo + "&text=" + message;

                // 연결 방식, 정보 세팅
                con.setRequestMethod("POST");
                for (Map.Entry<String, String> header : requestHeaders.entrySet()) {
                    con.setRequestProperty(header.getKey(), header.getValue());
                }

                con.setDoOutput(true);
                try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                    wr.write(postParams.getBytes());
                    wr.flush();
                }

                // 번역 된 meesage를 list에 추가
                String responseBody = readBody(con.getInputStream());
                try {
                    String translatedMessage = getTranslatedMessage(responseBody);
                    translatedMessages.add(translatedMessage);
                }
                catch (Exception e) {
                    throw new Exception("parsing에 실패하였습니다");
                }
            }
            catch (Exception e) {
                throw new RuntimeException("API 요청과 응답 실패", e);
            } finally {
                con.disconnect();
            }
        }

        return translatedMessages;
    }

    // 연결
    private static HttpURLConnection connect(String apiUrl){
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }

    // 받은 데이터를 string으로 parsing
    private static String readBody(InputStream body){
        InputStreamReader streamReader = new InputStreamReader(body);

        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();

            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }

            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
        }
    }

    private static String getTranslatedMessage(String responseBody) throws ParseException {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(responseBody);
        JSONObject objMessage = (JSONObject) jsonObject.get("message");
        JSONObject objResult= (JSONObject) objMessage.get("result");

        return (String) objResult.get("translatedText");
    }
}
