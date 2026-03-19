package com.example.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.BMI;
import com.example.demo.response.ApiResponse;
//可以省去撰寫@ResponseBody
@RestController
@RequestMapping("/api")
public class ApiController {
	/**
	 * 1.API首頁
	 * 路徑1:/home
	 * 路徑2:/welcome
	 * 路徑1:http://localhost:8080/api/home
	 * 路徑2:http://localhost:8080/api/welcome
	 */
	@GetMapping(value = {"/home","/welcome"},produces = "text/plain;charset=utf-8")
	
	public String home() {
		return "我是API首頁";
	}
	/**
	 * 2.?帶參數
	 * 路徑1:/greet?name=John&age=18
	 * 路徑2:/greet?name=Mary
	 * 網址1:http://localhost:8080/api/greet?name=John&age=18
	 * 結果1:Hi John,18(成年)
	 * 網址2:http://localhost:8080/api/greet?name=Mary
	 * 結果2:Hi Mary,0(未成年)
	 * 限制:name為必要參數,age為可選參數(有初始值0)
	 */
	@GetMapping("/greet")
	public String greet(@RequestParam(value="name",required = true)String username,
			@RequestParam(value="age",required = false,defaultValue = "0")Integer userage) {
		String result=String.format("Hi %s,%d(%s)", username,userage,userage>=18?"成年":"未成年");
		return result;
	}
	//上述2的精簡寫法
	//方法參數名稱與請求參數名相同
	@GetMapping("/greet2")
	public String greet2(@RequestParam String name,
			@RequestParam(required = false,defaultValue = "0")Integer age) {
		
		return greet(name,age);
	}
	//Lab練習
	@GetMapping("/bmi")
	public String bmi(@RequestParam(value = "high",required = true)Float height,
			@RequestParam(value="body",required = true)Float weight) {
		double BMI=weight/Math.pow(height/100, 2);
		String status=BMI>=24?"體重過重":(BMI<18.5?"體重過輕":"體重正常");
		String result=String.format("身高:%.1fcm,體重:%.1fkg,BMI:%.2f(%s)",height,weight,BMI,status);
		return result;
	}
	/**
	 * 5.回傳json結構
	 * 路徑:/json/bmi?h=170&w=60
	 * 網址:http://localhost:8080/api/json/bmi?height=170&weight=60
	 * 判斷bmi<=18.5顯示過輕,bmi>=24顯示過重
	 * 執行結果:
	 * {
	 * 	"status":200
	 * 	"message":"BMI計算成功"
	 * 	"data":{
	 * 		"height":170
	 * 		"weight":60
	 * 		"bmi":20.76
	 * 	}
	 * }
	 */
	@GetMapping(value="/json/bmi")
	public ResponseEntity<ApiResponse<BMI>> calcbmi(@RequestParam(required = false) Double h,
			@RequestParam(required = false) Double w) {
		//badRequest HTTP狀態碼:400
		if(h==null||w==null) {
			return ResponseEntity.badRequest().body(ApiResponse.fail("請輸入身高或體重參數"));
		}
		if(h<=0||w<=0) {
			return ResponseEntity.badRequest().body(ApiResponse.fail("身高體重參數錯誤"));
		}
		double bmiValue=w/Math.pow(h/100, 2);
		BMI bmi=new BMI(h,w,bmiValue);
		//ok HTTP狀態碼:200
		return ResponseEntity.ok(ApiResponse.success("計算成功", bmi));
	}
	/**
	 * 6.同名多筆資料
	 * 路徑:/json/age?age=17&age=60&age=15
	 * 網址:http://localhost:8080/api/json/age?age=17&age=60&age=15
	 * 請計算出平均年齡
	 */
	@GetMapping(value = "/json/age")
	public ResponseEntity<ApiResponse<Object>> getAverage(@RequestParam(name="age",required = false) List<Integer> ages) {
		if(ages==null||ages.size()==0) {
			return ResponseEntity.badRequest().body(ApiResponse.fail("請輸入年齡"));
		}
		if(ages.stream().anyMatch(s->s<0)) {
			return ResponseEntity.badRequest().body(ApiResponse.fail("年齡只能是正值"));
		}
		double avg=ages.stream().mapToInt(Integer::valueOf).average().orElseGet(()->0);
		Object data=Map.of("年齡",ages,"平均年齡",String.format("%.1f", avg));
		return ResponseEntity.ok(ApiResponse.success("計算成功", data));
	}
	//Lab練習
	@GetMapping(value="/json/score")
	public ResponseEntity<ApiResponse<Object>> scoreAverage(@RequestParam(name="score",required = false) List<Integer> scores){
		if(scores==null||scores.size()==0) {
			return ResponseEntity.badRequest().body(ApiResponse.fail("請輸入分數"));
		}
		if(scores.stream().anyMatch(s->s<0)) {
			return ResponseEntity.badRequest().body(ApiResponse.fail("分數只能是正值"));
		}
		if(scores.stream().anyMatch(s->s>100)) {
			return ResponseEntity.badRequest().body(ApiResponse.fail("分數不能超過100"));
		}
		double ave=scores.stream().mapToInt(Integer::valueOf).average().orElseGet(()->0);
		int sum=scores.stream().mapToInt(Integer::valueOf).sum();
		int max=scores.stream().mapToInt(Integer::valueOf).max().orElseGet(()->0);
		int min=scores.stream().mapToInt(Integer::valueOf).min().orElseGet(()->0);
		Object data=Map.of("最高分",max,"最低分",min,"平均",String.format("%.2f", ave),"總分",sum);
		return ResponseEntity.ok(ApiResponse.success("計算成功", data));
		
	}
	/**
	 * 8.多筆資料轉Map
	 * name:書名(String),price:價格(Double),amount:數量(Integer),pub:出刊/停刊(Boolean)
	 * 路徑:/json/book1?name=Math&price=12.5&amount=10&pub=true
	 * 網址:http://localhost:8080/api/json/book1?name=Math&price=12.5&amount=10&pub=true
	 * 網址:http://localhost:8080/api/json/book1?name=English&price=10.5&amount=20&pub=false
	 * 讓參數自動轉成key/value的Map集合
	 */
	@GetMapping(value="/json/book1")
	public ResponseEntity<ApiResponse<Object>> getBookInfo(@RequestParam Map<String,Object> bookMap){
		System.out.printf("bookMap=%s%n",bookMap);
		return ResponseEntity.ok(ApiResponse.success("成功", bookMap));
	}
	/**
	 * 多筆參數轉model
	 * 路徑:/json/book2?name=Math&price=12.5&amount=10&pub=true
	 */

}
