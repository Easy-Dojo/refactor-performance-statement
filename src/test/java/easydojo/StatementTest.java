package easydojo;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import easydojo.model.Invoice;
import easydojo.model.Play;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StatementTest {


    @Test
    void test_show() {
        String expected = "Statement for BigCo Hamlet: $650.00 (55 seats)\n" +
                " As You Like It: $580.00 (35 seats)\n" +
                " Othello: $500.00 (40 seats)\n" +
                "Amount owed is $1,730.00\n" +
                "You earned 47 credits\n";
        final String plays = "{" +
                "\"hamlet\":{\"name\":\"Hamlet\",\"type\":\"tragedy\"}," +
                "\"as-like\":{\"name\":\"As You Like It\",\"type\":\"comedy\"}," +
                "\"othello\":{\"name\":\"Othello\",\"type\":\"tragedy\"}" +
                "}";

        final String invoices = "{" +
                "\"customer\":\"BigCo\",\"performances\":[" +
                "{\"playId\":\"hamlet\",\"audience\":55}" +
                "{\"playId\":\"as-like\",\"audience\":35}" +
                "{\"playId\":\"othello\",\"audience\":40}" +
                "]" +
                "}";

        TypeReference<Map<String, Play>> typeReference = new TypeReference<Map<String, Play>>(){};
        Map<String, Play> playMap = JSONObject.parseObject(plays, typeReference);
        Invoice invoice = JSONObject.parseObject(invoices, Invoice.class);
        Statement statement = new Statement(invoice, playMap);
        String result = statement.show();
        assertEquals(expected, result);
    }
}