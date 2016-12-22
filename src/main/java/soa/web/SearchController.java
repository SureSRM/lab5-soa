package soa.web;

import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;


@Controller
public class SearchController {

	@Autowired
	  private ProducerTemplate producerTemplate;

	@RequestMapping("/")
    public String index() {
        return "index";
    }


    @RequestMapping(value="/search")
    @ResponseBody
    public Object search(@RequestParam("q") String q) {
        Map<String, Object> headers = new HashMap<>();
        String[] query = q.split(" ");
        headers.put("CamelTwitterKeywords", query[0]);

        if (query.length>1 && query[1].contains("max:") && query[1].split(":").length>1 ){
                headers.put("CamelTwitterCount", Integer.parseInt(query[1].split(":")[1]));
        }

        return producerTemplate.requestBodyAndHeaders("direct:search","",headers);
    }
}